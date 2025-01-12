package com.group13.Controllers.Cashier.SelectOperations;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.group13.Models.ConnectionModel;
import com.group13.Models.SessionModel;
import com.group13.Models.Model;
import com.group13.Models.TicketModel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class CashierDateSelectionController 
{

   @FXML
   TableView<SessionModel> sessionsTable;
   @FXML
   private TableColumn<SessionModel, Date> dateColumn;
   @FXML
   private TableColumn<SessionModel, Timestamp> timeColumn;
   
    @FXML
    private Button cancelButton;

    @FXML
    private Button nextStageButton;



    private void cancelSecondStage() {
        Model.getInstance().setMovieModel(null);
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Search By Genre");
    }

    private void nextStage() {
        
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Hall A");
        
    }

   private final ObservableList<SessionModel> sessionsList = FXCollections.observableArrayList();

    public void initialize() 
    {
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        this.timeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        sessionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cancelButton.setOnAction(e -> cancelSecondStage());
        nextStageButton.setOnAction(e -> nextStage());
        System.out.println("\n\nMovieID: "+Model.getInstance().getTicketModel().getMovieID()+"\n");

        

        sessionDatesFinder(); // Stores all sessions using ticketModel info 
        
        for(int i=0;i<sessionsList.size();i++)
        {
            System.out.println("MovieIDs from array:"+sessionsList.get(i).getMovieID());
        }
        sessionsTable.setItems(sessionsList);
        sessionsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = newValue != null;
            nextStageButton.setDisable(!isItemSelected);
            Model.getInstance().setSessionModel(newValue);
            Model.getInstance().getTicketModel().setSessionID(newValue.getSessionID());
            

        });
    }   

    @FXML
    private void onDateSelection()
    {
        sessionDatesFinder();
    }

    //This method searches the sessions table and finds all the sessions with a specific movieName (retrieved from the ticketModel object), then returns the available sessions in a sessionModel array (if the session seats have not been assigned yet, the method assigns them)
    public void sessionDatesFinder()
    {
        List<SessionModel> IDs = new ArrayList<>();
    
        try
        {
            Connection conn = ConnectionModel.getConnection();
            Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery("Select * FROM sessions WHERE movie_id='"+Model.getInstance().getTicketModel().getMovieID()+"'");

            if(rs==null)
            {
                System.out.println("No Sessions found");
                return;
            }


            while(rs.next())
            {
                SessionModel session = new SessionModel();
                session.setHallNumber(rs.getInt("hall_number"));
                session.setSessionID(rs.getInt("session_id"));
                session.setMovieID(rs.getInt("movie_id"));
                session.setSessionDate(rs.getDate("session_date"));
                session.setSessionTime(rs.getTimestamp("session_time"));

                IDs.add(session);
            }

            for(int i=0;i<IDs.size();i++)
            {
                ResultSet newRs = stat.executeQuery("Select * FROM seats WHERE session_id="+IDs.get(i).getSessionID());
                
                
                if(!newRs.next())
                {
                    stat.executeQuery("CALL fillSeats(" +IDs.get(i).getSessionID()+")");

                    System.out.println("\n\nFilled Seats for session:\t" + IDs.get(i).getSessionID());
                }

                newRs = stat.executeQuery("Select * FROM seats WHERE session_id="+IDs.get(i).getSessionID());
                
                while(newRs.next())
                {
                    if(newRs.getInt("sold")==0)
                    {
                        sessionsList.add(IDs.get(i));
                        break;
                    }
                }
            }
            


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }   
    }
}