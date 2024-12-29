package com.group13.Controllers.Cashier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.group13.Models.ConnectionModel;




public class CashierSearchByNameController 
{
   
   static ResultSet searchByName(String Name)
   {
      ResultSet rs =null;
      
      try
      {
         Connection conn = ConnectionModel.getConnection();
         Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

         rs = stat.executeQuery("Select * FROM movies WHERE movie_name='"+Name+"'");
         if(rs==null)
         {
            System.err.println("No movies with this name.");
         }   
      }
      catch(SQLException e)
      {   
         System.out.println("Error occured while getting movie by name");
         e.printStackTrace();
      }
      
      return rs;

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
        
      ResultSet rs = searchByName("Cars2");
      printRS(rs);


    }
}
