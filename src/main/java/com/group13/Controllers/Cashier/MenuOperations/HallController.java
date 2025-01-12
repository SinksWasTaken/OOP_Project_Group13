package com.group13.Controllers.Cashier.MenuOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.SeatModel;
import com.group13.Models.TicketModel;
import com.mysql.cj.x.protobuf.MysqlxNotice;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HallController {
    @FXML
    private GridPane seatGrid;

    @FXML
    private Label hallLabel;

    @FXML
    private Button nextStageButton;

    @FXML
    private Button cancelButton;

    private int selectedCount;

    private void cancelSecondStage() {
        
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Session Date");
    }

    private void nextStage() 
    {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Third Stage"); //Change these
        storeAllSelectedSeatsToTicket();
        
    }

    @FXML
    public void initialize() 
    {
        
        createSeatLayout(Model.getInstance().getSessionModel().getHallNumber());
        disableSoldSeats(sessionSeatsFinder());
        
        cancelButton.setOnAction(e -> cancelSecondStage());
        nextStageButton.setOnAction(e -> nextStage());

    }

    private void createSeatLayout(int hallNo) {

        if(hallNo == 1) {
            hallLabel.setText("Hall A Seat Selection");
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 8; col++) {
                    Button seatButton = new Button();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BED);
                    icon.getStyleClass().add("menu-icon");
                    seatButton.setGraphic(icon);
                    seatButton.getStyleClass().add("seat-button");
                    seatButton.setOnAction(event -> toggleSeatSelection(seatButton));
                    seatGrid.add(seatButton, col, row);
                    
                }
            }
        } else {
            hallLabel.setText("Hall B Seat Selection");
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 8; col++) {
                    Button seatButton = new Button();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BED);
                    icon.getStyleClass().add("menu-icon");
                    seatButton.setGraphic(icon);
                    seatButton.getStyleClass().add("seat-button");
                    seatButton.setOnAction(event -> toggleSeatSelection(seatButton));
                    seatGrid.add(seatButton, col, row);
                    
                }
            }
        }
    }

    private void toggleSeatSelection(Button seatButton) {
        if (seatButton.getStyleClass().contains("selected")) {
            seatButton.getStyleClass().remove("selected");
            selectedCount--;
            if(selectedCount==0)
            {
                nextStageButton.setDisable(true);
            }
        } 
        else {
            seatButton.getStyleClass().add("selected");
            selectedCount++;
            if(selectedCount!=0)
            {
                nextStageButton.setDisable(false);
            }
        }
    }

    //This method searches the seats table and finds all the seats with a specific session_id (retrieved from the ticketModel object), then returns the available seats in a seatModel array (if the session seats have not been assigned yet, the method assigns them)
    static List<SeatModel> sessionSeatsFinder()
    {
        List<SeatModel> seats =  new ArrayList<>();
        try
        {
            Connection conn = ConnectionModel.getConnection();
            Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            ResultSet rs = stat.executeQuery("Select * FROM seats WHERE session_id="+Model.getInstance().getTicketModel().getSessionID());

            if(rs==null)
            {
                System.out.println("No Seats found");
                return seats;
            }

            while(rs.next())
            {
                SeatModel seat = new SeatModel();
                seat.hall=rs.getInt("hall");
                seat.seat_id= rs.getInt("seat_id");
                seat.col= rs.getInt("col");
                seat.row=rs.getString("`row`");
                seat.session_id=rs.getInt("session_id");
                seat.sold = rs.getBoolean("sold");

                seats.add(seat);
            }
            return seats;
        }
        catch(SQLException e)//The code never goes here :D
        {
            e.printStackTrace();
            return null;
        }

        
    }
    
    void disableSoldSeats(List<SeatModel> allSeats)
    {
        for(int i = 0;i<seatGrid.getRowCount();i++)
        {
            for(int j = 0;j<seatGrid.getColumnCount();j++)
            {
                if(allSeats.get(i*seatGrid.getRowCount() + j).getSold())
                {
                    seatGrid.getChildren().get(i*seatGrid.getRowCount() + j).setDisable(true);
                }
            }
        }
    }

    void storeAllSelectedSeatsToTicket()
    {
        for(int i =0;i<seatGrid.getChildren().size();i++)
        {
            if(seatGrid.getChildren().get(i).getStyleClass().contains("selected"))
            {
                try
                {
                    Connection conn = ConnectionModel.getConnection();
                    Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    
                    ResultSet rs = stat.executeQuery("Select * FROM seats WHERE session_id="+Model.getInstance().getTicketModel().getSessionID());


                    Model.getInstance().getTicketModel().setNumberOfTickets(Model.getInstance().getTicketModel().getNumberOfTickets()+1);

                
                    while(rs.next())
                    {
                        Model.getInstance().getTicketModel().allSeats.add(rs.getInt("seat_id"));
                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }

                
            }
        }
    }

}
