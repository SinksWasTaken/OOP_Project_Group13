package com.group13.Controllers.Cashier.MenuOperations;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.group13.Models.ConnectionModel;
import com.group13.Models.TicketModel;

public class CashierPurchaseTicketController {
    public static boolean purchaseTicket(TicketModel ticket) {
        try {
            Connection connection = ConnectionModel.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String selectAllTicketsQuery = "SELECT * FROM tickets";

            ResultSet rs = statement.executeQuery(selectAllTicketsQuery);

            rs = statement.executeQuery(selectAllTicketsQuery);
            rs.moveToInsertRow();

            rs.updateString(2, ticket.getCustomerName());
            rs.updateInt(3, ticket.getCustomerAge());
            rs.updateInt(4, ticket.getSessionID());
            rs.updateInt(5, ticket.getSeatID());
            rs.updateInt(6, ticket.getMovieID());
            rs.updateString(7, ticket.getProductNames());
            rs.updateInt(8, ticket.getNumberOfTickets());
            rs.updateDouble(9, ticket.getDiscountRate());
            rs.updateDouble(10, ticket.getTotalTicketPrice());
            rs.updateDouble(11, ticket.getTotalProductPrice());
            rs.updateDate(12, ticket.getPurchaseDate());
            rs.insertRow();

            System.out.println("Refunded Ticket Successfully");

            String selectSessionQuery = "SELECT * FROM sessions Where session_date=" + ticket.getSessionID();
            rs = statement.executeQuery(selectSessionQuery);

            if (rs == null) {
                System.out.println("No Session Found");
                return false;
            }
            rs.next();
            int sessionID = rs.getInt("sessionID");

            System.out.println("Session Selected Successfully");

            String selectSeatQuery = "SELECT * FROM seats Where sessionID=" + sessionID + " and seat_name=" + ticket.getSeatID();
            rs = statement.executeQuery(selectSeatQuery);

            rs.next();
            rs.updateBoolean(2, true);

            System.out.println("Seat Sold Successfully");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't Refund Ticket");
            return false;
        }
    }
}