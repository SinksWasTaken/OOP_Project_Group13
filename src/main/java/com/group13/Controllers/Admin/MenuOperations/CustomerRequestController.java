package com.group13.Controllers.Admin.MenuOperations;

import com.group13.Controllers.Admin.AdminRefundTicketController;
import com.group13.Models.ConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRequestController {

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, Integer> ticketIdColumn;

    @FXML
    private TableColumn<Ticket, String> customerNameColumn;

    @FXML
    private TableColumn<Ticket, String> movieNameColumn;

    @FXML
    private TableColumn<Ticket, String> sessionDateColumn;

    @FXML
    private TableColumn<Ticket, String> sessionTimeColumn;

    @FXML
    private TableColumn<Ticket, String> hallColumn;

    @FXML
    private TableColumn<Ticket, String> seatColumn;

    @FXML
    private Button refundButton;

    private final ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadTickets();

        refundButton.setOnAction(event -> refundSelectedTicket());
    }

    private void setupTable() {
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        sessionDateColumn.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        sessionTimeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        hallColumn.setCellValueFactory(new PropertyValueFactory<>("hall"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));

        ticketTable.setItems(ticketList);
    }

    private void loadTickets() {
        ticketList.clear();
        String query = "SELECT t.ticket_id, t.customer_name, m.movie_name, s.session_date, s.session_time, " +
                "s.hall_number AS hall, CONCAT(se.seat_row, se.seat_col) AS seat " +
                "FROM tickets t " +
                "JOIN sessions s ON t.session_id = s.session_id " +
                "JOIN movies m ON s.movie_id = m.movie_id " +
                "JOIN seats se ON se.session_id = s.session_id AND se.seat_row = t.seat_row AND se.seat_col = t.seat_col";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                String customerName = resultSet.getString("customer_name");
                String movieName = resultSet.getString("movie_name");
                String sessionDate = resultSet.getString("session_date");
                String sessionTime = resultSet.getString("session_time");
                String hall = resultSet.getString("hall");
                String seat = resultSet.getString("seat");

                ticketList.add(new Ticket(ticketId, customerName, movieName, sessionDate, sessionTime, hall, seat));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refundSelectedTicket() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showAlert("Error", "Please select a ticket to refund.", Alert.AlertType.ERROR);
            return;
        }

        // Refund the ticket (logic in AdminRefundTicketController)
        boolean success = AdminRefundTicketController.refundTicket(selectedTicket.getTicketId());

        if (success) {
            ticketList.remove(selectedTicket);
            showAlert("Success", "Ticket refunded successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to refund the ticket.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class Ticket {
        private final int ticketId;
        private final String customerName;
        private final String movieName;
        private final String sessionDate;
        private final String sessionTime;
        private final String hall;
        private final String seat;

        public Ticket(int ticketId, String customerName, String movieName, String sessionDate, String sessionTime, String hall, String seat) {
            this.ticketId = ticketId;
            this.customerName = customerName;
            this.movieName = movieName;
            this.sessionDate = sessionDate;
            this.sessionTime = sessionTime;
            this.hall = hall;
            this.seat = seat;
        }

        public int getTicketId() {
            return ticketId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getMovieName() {
            return movieName;
        }

        public String getSessionDate() {
            return sessionDate;
        }

        public String getSessionTime() {
            return sessionTime;
        }

        public String getHall() {
            return hall;
        }

        public String getSeat() {
            return seat;
        }
    }
}
