package com.group13.Controllers.Cashier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.chrono.ThaiBuddhistChronology;

import com.group13.Models.TicketModel;
 
import com.group13.DatabaseConnection;

public class CashierPurchaseTicketController 
{
    public static boolean purchaseTicket(TicketModel ticket)
    {
        try 
        {
            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String selectAllTicketsQuery = "SELECT * FROM tickets";

            ResultSet rs = statement.executeQuery(selectAllTicketsQuery);

            rs=statement.executeQuery(selectAllTicketsQuery);
            rs.moveToInsertRow();

            rs.updateString(2, ticket.customerName);
            rs.updateInt(3, ticket.age);
            rs.updateString(4, ticket.extraProducts);
            rs.updateDouble(5, ticket.price*-1);
            rs.updateString(6, ticket.customerSuggestions);
            rs.updateString(7, ticket.movieName);
            rs.updateTimestamp(8, ticket.sessionTime);
            rs.updateDate(9, ticket.sessionDate);
            rs.updateInt(10, ticket.sessionHall);
            rs.updateInt(11, ticket.seatCol);
            rs.updateString(12, ticket.seatRow);
            rs.insertRow();

            System.out.println("Refunded Ticket Successfully");

            String selectSessionQuery = "SELECT * FROM sessions Where session_date='"+ticket.sessionDate+ "' and session_time='"+ticket.sessionTime+"' and movie_name='"+ticket.movieName+"'";
            rs=statement.executeQuery(selectSessionQuery);

            if(rs==null)
            {
                System.out.println("No Session Found");
                return false;
            }
            rs.next();
            int sessionID = rs.getInt("session_id");

            System.out.println("Session Selected Successfully");

            String selectSeatQuery = "SELECT * FROM seats Where session_id="+sessionID+" and c="+ticket.seatCol+ " and r='"+ticket.seatRow+"' and hall="+ticket.sessionHall;
            rs=statement.executeQuery(selectSeatQuery);

            rs.next();
            rs.updateBoolean(2, true);
            
            System.out.println("Seat Sold Successfully");

            return true;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println("Couldn't Refund Ticket");
            return false;
        }
    }
}
