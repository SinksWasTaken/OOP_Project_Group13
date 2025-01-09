package com.group13.Controllers.Admin;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
 
import com.group13.DatabaseConnection;

public class AdminRefundTicketController
{
    public static void printRS(ResultSet resultSet)
    {
      try
      {
        ResultSetMetaData metaData = resultSet.getMetaData();
         int numberOfColumns = metaData.getColumnCount(); 
         //MainFunc.Clear_Console();
         System.out.printf("group13 Table of movies Database:%n%n");

         // display the names of the columns in the ResultSet
         for (int i = 1; i <= numberOfColumns; i++) 
         {
            System.out.printf("%-8s\t", metaData.getColumnName(i));
         }
         System.out.println();

         while (resultSet.next()) 
         {
            for (int i = 1; i <= numberOfColumns; i++) 
            {
               System.out.printf("%-8s\t", resultSet.getObject(i));
            }
            System.out.println();
         }
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage()+ " Retrying....");
      }
   }
    
    public static boolean refundTicket(int ticketNumber)
    {
        try 
        {
            Connection connection = DatabaseConnection.connect();
            String query = "Select * From tickets where ticketNumber=" + ticketNumber;


            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = statement.executeQuery(query);
            if(rs==null)
            {
                System.out.println("TicketNumber: "+ ticketNumber + " Couldn't be found.");
                return false;
            }
            
            rs.next();
            String customerName         = "REFUND: "+ rs.getString("customerName");
            int age                     = rs.getInt("age");
            String extraProducts        = rs.getString("extrasProducts");
            double price                = rs.getDouble("price");
            String customerSuggestions  = rs.getString("customerSuggestions");
            String movieName            = rs.getString("movieName");
            Date sessionDate            = rs.getDate("sessionDate");
            Timestamp sessionTime       = rs.getTimestamp("sessionTime");
            int sessionHall             = rs.getInt("sessionHall");
            int seatCol                 = rs.getInt("seatCol");
            String seatRow              = rs.getString("seatRow");



            
            String selectAllTicketsQuery = "SELECT * FROM tickets";

            rs=statement.executeQuery(selectAllTicketsQuery);
            rs.moveToInsertRow();
            rs.updateString(2, customerName);
            rs.updateInt(3, age);
            rs.updateString(4, extraProducts);
            rs.updateDouble(5, price*-1);
            rs.updateString(6, customerSuggestions);
            rs.updateString(7, movieName);
            rs.updateTimestamp(8, sessionTime);
            rs.updateDate(9, sessionDate);
            rs.updateInt(10, sessionHall);
            rs.updateInt(11, seatCol);
            rs.updateString(12, seatRow);
            rs.insertRow();

            System.out.println("Refunded Ticket Successfully");

            String selectSeatQuery = "SELECT * FROM seats Where c="+seatCol+ " & r='"+seatRow+"' & hall="+sessionHall;
            rs=statement.executeQuery(selectSeatQuery);
            rs.next();
            rs.updateBoolean(2, false);
            
            System.out.println("Seat Cleared Successfully");

            return true;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println("Couldn't Refund Ticket");
            return false;
        }
    }
    public static void main(String[] args)
    {
        refundTicket(1);
    }
}