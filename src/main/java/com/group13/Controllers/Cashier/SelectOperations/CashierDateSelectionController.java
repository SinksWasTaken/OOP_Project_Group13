package com.group13.Controllers.Cashier.SelectOperations;
import java.sql.*;
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

public class CashierDateSelectionController {

    @FXML
    TableView<SessionModel> sessionsTable;
    @FXML
    private TableColumn<SessionModel, Date> dateColumn;
    @FXML
    private TableColumn<SessionModel, Timestamp> timeColumn;
    @FXML
    private TableColumn<SessionModel, Integer> hallColumn;

    @FXML
    private Button cancelButton;
    @FXML
    private Button nextStageButton;

    private void cancelSecondStage() {
        Model.getInstance().setTicketModel(null);
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Search By Genre");
    }

    private void nextStage() {
        SessionModel selectedSession = sessionsTable.getSelectionModel().getSelectedItem();
        TicketModel ticketModel = Model.getInstance().getTicketModel();
        if (ticketModel != null) {
            ticketModel.setSessionID(selectedSession.getSessionID());
        }
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Hall");
    }

    private final ObservableList<SessionModel> sessionsList = FXCollections.observableArrayList();

    public void initialize() {
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        this.timeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        this.hallColumn.setCellValueFactory(new PropertyValueFactory<>("hallNumber"));

        sessionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cancelButton.setOnAction(e -> cancelSecondStage());
        nextStageButton.setOnAction(e -> nextStage());
        System.out.println("\n\nMovieID: " + Model.getInstance().getTicketModel().getMovieID() + "\n");

        sessionDatesFinder();

        for (int i = 0; i < sessionsList.size(); i++) {
            System.out.println("MovieIDs from array:" + sessionsList.get(i).getMovieID());
        }
        sessionsTable.setItems(sessionsList);
        sessionsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = newValue != null;
            nextStageButton.setDisable(!isItemSelected);
            Model.getInstance().setSessionModel(newValue);
            assert newValue != null;
            Model.getInstance().getTicketModel().setSessionID(newValue.getSessionID());
        });
    }

    // This method searches the sessions table and finds all the sessions with a specific movieName (retrieved from the ticketModel object), then returns the available sessions in a sessionModel array (if the session seats have not been assigned yet, the method assigns them)
    public void sessionDatesFinder() {
        sessionsList.clear();
        List<SessionModel> IDs = new ArrayList<>();

        try (Connection conn = ConnectionModel.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM sessions WHERE movie_id = ?")) {

            pstmt.setInt(1, Model.getInstance().getTicketModel().getMovieID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SessionModel session = new SessionModel();
                session.setHallNumber(rs.getInt("hall_number"));
                session.setSessionID(rs.getInt("session_id"));
                session.setMovieID(rs.getInt("movie_id"));
                session.setSessionDate(rs.getDate("sessionDate"));
                session.setSessionTime(rs.getTimestamp("sessionTime"));
                IDs.add(session);
            }

            for (SessionModel session : IDs) {
                PreparedStatement seatStmt = conn.prepareStatement("SELECT * FROM seats WHERE session_id = ?");
                seatStmt.setInt(1, session.getSessionID());
                ResultSet seatRs = seatStmt.executeQuery();

                if (!seatRs.next()) {
                    Statement fillStmt = conn.createStatement();
                    fillStmt.executeUpdate("CALL fillSeats(" + session.getSessionID() + ")");
                }

                seatRs = seatStmt.executeQuery();
                while (seatRs.next()) {
                    if (seatRs.getInt("sold") == 0) {
                        sessionsList.add(session);
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}