package com.group13.Controllers.Cashier.SelectOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.group13.Models.*;

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
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Session Selection");
    }

    private void nextStage() {
        storeAllSelectedSeatsToTicket();
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Product Selection");
    }

    @FXML
    public void initialize() {
        SessionModel selectedSession = Model.getInstance().getSessionModel();
        if (selectedSession != null) {
            System.out.println("Session ID: " + selectedSession.getSessionID());
            createSeatLayout(selectedSession.getHallNumber());
        }
        disableSoldSeats(sessionSeatsFinder());

        cancelButton.setOnAction(e -> cancelSecondStage());
        nextStageButton.setOnAction(e -> nextStage());
    }

    private void createSeatLayout(int hallNo) {
        List<SeatModel> seats = sessionSeatsFinder();

        int index = 0;
        if (hallNo == 1) {
            hallLabel.setText("Hall A Seat Selection");
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 8; col++) {
                    Button seatButton = new Button();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BED);
                    icon.getStyleClass().add("menu-icon");
                    seatButton.setGraphic(icon);
                    seatButton.getStyleClass().add("seat-button");

                    seatButton.setUserData(seats.get(index).getSeatID());
                    index++;

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

                    seatButton.setUserData(seats.get(index).getSeatID());
                    index++;

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
            if (selectedCount == 0) {
                nextStageButton.setDisable(true);
            }
        } else {
            seatButton.getStyleClass().add("selected");
            selectedCount++;
            if (selectedCount != 0) {
                nextStageButton.setDisable(false);
            }
        }
    }

    // This method searches the seats table and finds all the seats with a specific session_id (retrieved from the ticketModel object), then returns the available seats in a seatModel array (if the session seats have not been assigned yet, the method assigns them)
    static List<SeatModel> sessionSeatsFinder() {
        List<SeatModel> seats = new ArrayList<>();
        try {
            Connection conn = ConnectionModel.getConnection();
            Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stat.executeQuery("Select * FROM seats WHERE session_id=" + Model.getInstance().getTicketModel().getSessionID());

            if (rs == null) {
                System.out.println("No Seats found");
                return seats;
            }

            while (rs.next()) {
                SeatModel seat = new SeatModel();
                seat.hall = rs.getInt("hall");
                seat.seat_id = rs.getInt("seat_id");
                seat.col = rs.getInt("col");
                seat.row = rs.getString("row");
                seat.session_id = rs.getInt("session_id");
                seat.sold = rs.getBoolean("sold");

                seats.add(seat);
            }
            return seats;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    void disableSoldSeats(List<SeatModel> allSeats) {
        for (int i = 0; i < seatGrid.getRowCount(); i++) {
            for (int j = 0; j < seatGrid.getColumnCount(); j++) {
                if (allSeats.get(i * seatGrid.getRowCount() + j).getSold()) {
                    seatGrid.getChildren().get(i * seatGrid.getRowCount() + j).setDisable(true);
                    seatGrid.getChildren().get(i * seatGrid.getRowCount() + j).setStyle("-fx-background-color: #f44336;");
                }
            }
        }
    }

    void storeAllSelectedSeatsToTicket() {
        TicketModel ticketModel = Model.getInstance().getTicketModel();
        List<Integer> selectedSeats = new ArrayList<>();

        for (int i = 0; i < seatGrid.getChildren().size(); i++) {
            Button seatButton = (Button) seatGrid.getChildren().get(i);
            if (seatButton.getStyleClass().contains("selected")) {
                int seatId = (int) seatButton.getUserData();
                selectedSeats.add(seatId);
            }
        }

        ticketModel.setAllSeats(selectedSeats);
        ticketModel.setNumberOfTickets(selectedSeats.size());
    }
}