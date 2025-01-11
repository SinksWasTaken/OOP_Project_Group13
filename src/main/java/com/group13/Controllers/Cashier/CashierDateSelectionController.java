package com.group13.Controllers.Cashier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.group13.Models.ConnectionModel;
import com.group13.Models.TicketModel;
import com.group13.Models.SessionModel;



public class CashierDateSelectionController 
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
            System.out.printf("%-30s\t", metaData.getColumnName(i));
         }
         System.out.println();
      
         while (resultSet.next()) 
         {
            for (int i = 1; i <= numberOfColumns; i++) 
            {
               System.out.printf("%-30s\t", resultSet.getObject(i));
            }
            System.out.println();
         }
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage()+ " Retrying....");
      }
   }
    //This method searches the sessions table and finds all the sessions with a specific movieName (retrieved from the ticketModel object), then returns the available sessions in a sessionModel array (if the session seats have not been assigned yet, the method assigns them)
    static void sessionDatesFinder(List<SessionModel> IDsFinal,TicketModel ticket)
    {
        List<SessionModel> IDs = new ArrayList<>();
    
        try
        {
            Connection conn = ConnectionModel.getConnection();
            Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery("Select * FROM sessions WHERE movie_name='"+ticket.movieName+"'");

            if(rs==null)
            {
                System.out.println("No Sessions found");
                return;
            }


            while(rs.next())
            {
                SessionModel session = new SessionModel();
                session.hall_number=rs.getInt("hall_number");
                session.session_id= rs.getInt("session_id");
                session.movie_name= rs.getString("movie_name");
                session.session_date=rs.getDate("session_date");
                session.session_time=rs.getTimestamp("session_time");

                IDs.add(session);
            }

            for(int i=0;i<IDs.size();i++)
            {
                
                
                ResultSet newRs = stat.executeQuery("Select * FROM seats WHERE session_id="+IDs.get(i).session_id);
                
                
                if(!newRs.next())
                {
                    stat.executeQuery("CALL fillSeats(" +IDs.get(i).session_id+")");

                    System.out.println("\n\nFilled Seats for session:\t" + IDs.get(i).session_id);
                }

                newRs = stat.executeQuery("Select * FROM seats WHERE session_id="+IDs.get(i).session_id);
                
                while(newRs.next())
                {
                    if(newRs.getBoolean("sold")==false)
                    {
                        IDsFinal.add(IDs.get(i));
                        break;
                    }
                }
            }

            return;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return;
        }

        
    }

    public static void main(String[] args)
    {
        TicketModel ticket =  new TicketModel();
        ticket.movieName= "Cars";
        List<SessionModel> sessions = new ArrayList<>();
        sessionDatesFinder(sessions,ticket);

        for(int i=0;i<sessions.size();i++)
        {
            sessions.get(i).printSession();
            System.err.println("");
        }
    }
}