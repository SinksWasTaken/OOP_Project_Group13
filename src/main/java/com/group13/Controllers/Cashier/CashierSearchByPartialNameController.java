package com.group13.Controllers.Cashier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.group13.Models.ConnectionModel;
import com.group13.Models.MovieModel;




public class CashierSearchByPartialNameController 
{
   
   static List<MovieModel> searchByPartialName(String PartialName)
   {
      
      List<MovieModel> movies = new ArrayList<>();
      
      try
      {
         Connection conn = ConnectionModel.getConnection();
         Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

         ResultSet rs = stat.executeQuery("Select * FROM movies");
         if(rs==null)
         {
            System.err.println("No movies with this partial name.");
         }
         else
         {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();            

            while (rs.next()) 
            {
               MovieModel movie = new MovieModel();
               if(!rs.getString(2).contains(PartialName))
               {
                  continue;
               }
               for (int i = 1; i <= numberOfColumns; i++) 
               {
                  switch(i)
                  {
                     case 2 ->
                     {
                        movie.movieName =rs.getString(i);
                        
                     }
                     case 3 ->
                     {
                        movie.imgPath =rs.getString(i);
                     }
                     case 4 ->
                     {
                        movie.genre =rs.getString(i);
                     }
                  }
                  
               }
               movies.add(movie);
               System.out.println();
            }

         }  

      }
      catch(SQLException e)
      {   
         System.out.println("Error occured while getting movie by name");
         e.printStackTrace();
      }
      
      return movies;

    }

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

    public static void main(String[] args)
    {
        
      List<MovieModel> movies = searchByPartialName("Pirates");

      for(int i =0;i<movies.size();i++)
      {
         movies.get(i).printMovie();
         System.out.println('\n');
      }

    }
}
