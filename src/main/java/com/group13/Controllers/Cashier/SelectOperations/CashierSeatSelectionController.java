package com.group13.Controllers.Cashier.SelectOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.group13.Models.ConnectionModel;
import com.group13.Models.SeatModel;
import com.group13.Models.TicketModel;

public class CashierSeatSelectionController
{
    //This method searches the seats table and finds all the seats with a specific session_id (retrieved from the ticketModel object), then returns the available seats in a seatModel array (if the session seats have not been assigned yet, the method assigns them)
    static void sessionSeatsFinder(List<SeatModel> seatsFinal,TicketModel ticket)
    {

        try
        {
            Connection conn = ConnectionModel.getConnection();
            Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stat.executeQuery("Select * FROM seats WHERE session_id='"+ticket.sessionID+"'");

            if(rs==null)
            {
                System.out.println("No Seats found");
                return;
            }

            int counter=0;
            while(rs.next())
            {
                SeatModel seat = new SeatModel();
                seat.hall=rs.getInt("hall");
                seat.seat_id= rs.getInt("seat_id");
                seat.col= rs.getInt("col");
                seat.row=rs.getString("row");
                seat.session_id=rs.getInt("session_id");
                seat.sold = rs.getBoolean("sold");

                seatsFinal.add(seat);

                if(seat.sold)
                {
                    counter++;
                }

            }
            System.out.println("\nNumber of full seats: "+counter + "\n");

            return;
        }
        catch(SQLException e)//The code never goes here :D
        {
            e.printStackTrace();
            return;
        }


    }

}