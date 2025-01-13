package com.group13.Controllers.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group13.Models.ConnectionModel;

public class AdminRefundTicketController {

    public static boolean refundTicket(int ticketId) {
        try (Connection connection = ConnectionModel.getConnection()) {
            // Check if the ticket exists
            String ticketQuery = "SELECT * FROM tickets WHERE ticket_id = ?";
            PreparedStatement ticketStatement = connection.prepareStatement(ticketQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ticketStatement.setInt(1, ticketId);
            ResultSet ticketResultSet = ticketStatement.executeQuery();

            if (!ticketResultSet.next()) {
                System.out.println("Ticket ID: " + ticketId + " not found.");
                return false;
            }

            // Retrieve ticket details
            int sessionId = ticketResultSet.getInt("session_id");
            String seatRow = ticketResultSet.getString("seat_row");
            int seatCol = ticketResultSet.getInt("seat_col");

            // Delete the ticket
            String deleteTicketQuery = "DELETE FROM tickets WHERE ticket_id = ?";
            PreparedStatement deleteTicketStatement = connection.prepareStatement(deleteTicketQuery);
            deleteTicketStatement.setInt(1, ticketId);
            deleteTicketStatement.executeUpdate();

            System.out.println("Ticket ID: " + ticketId + " refunded successfully.");

            // Free the corresponding seat
            String updateSeatQuery = "UPDATE seats SET sold = FALSE WHERE session_id = ? AND seat_row = ? AND seat_col = ?";
            PreparedStatement updateSeatStatement = connection.prepareStatement(updateSeatQuery);
            updateSeatStatement.setInt(1, sessionId);
            updateSeatStatement.setString(2, seatRow);
            updateSeatStatement.setInt(3, seatCol);
            updateSeatStatement.executeUpdate();

            System.out.println("Seat " + seatRow + seatCol + " is now available.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error while refunding ticket: " + e.getMessage());
            return false;
        }
    }
}
