import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ManagerMenu {

   private static void Clear_Console()
   {    
      System.out.flush(); 
   }

   private static int validateIntInput(Scanner inputB)
    {
        boolean stop = false;
        int ans = 100;
        while(!stop)
        {
            if(inputB.hasNextInt())
            {
               ans = inputB.nextInt();
               stop =true;
            }
            else
            {             
               System.out.println("Enter a valid NUMBER.");
               inputB.next();
            }
        }
        inputB.reset();
        return ans;
    }
   
   private static LocalDate enterDate()
   {
      Scanner sc = new Scanner(System.in);
      LocalDate date = null;

      boolean stop =false;
      String str;
      
      
      while(!stop)
      {
         System.out.println("Enter date:");
         str = sc.next(); 
         if(!str.equals(""))
         {
            while (true) 
            { 
               try
               {
                  date = LocalDate.parse(str);
                  
                  return date;
               }
               catch(DateTimeParseException e)
               {
                  System.out.println("Error: Enter a valid date.");
                  str = sc.nextLine();   
               }
            }
            //stop=true;
         }
         else
         {             
            System.out.println("Enter a date.");
            sc.next();
         }
      } 
        sc.reset();
        return date;
   }

   public static ResultSet select(Statement statement)
   {
      String SELECT_QUERY = " SELECT * FROM workers"; 
      ResultSet resultSet;

      while(true)
      {
         try{
            resultSet = statement.executeQuery(SELECT_QUERY);
            return resultSet;
         }
         catch(SQLException e)
         {
            System.out.println(e.getMessage() + " Retrying....");
         }
      }
   }

   public static ResultSet select(Statement statement,int a)
   {
      String SELECT_QUERY = " SELECT * FROM workers WHERE role=" +a; 
      ResultSet resultSet;

      while(true)
      {
         try{
            resultSet = statement.executeQuery(SELECT_QUERY);
            return resultSet;
         }
         catch(SQLException e)
         {
            System.out.println(e.getMessage() + " Retrying....");
         }
      }
   }

   public static ResultSet select(Statement statement,String a)
   {
      String SELECT_QUERY = " SELECT * FROM workers WHERE username=" +a; 
      ResultSet resultSet;


      try{
         resultSet = statement.executeQuery(SELECT_QUERY);
         return resultSet;
      }
      catch(SQLException e)
      {
         System.out.println(e.getMessage() + " Retrying....");
         return null;
      }
      
   }

   public static void insertEmployee(EmployeeClass emp,Connection connection)
   {  
      try
      {
         String Select_Query = "SELECT * FROM workers";
         Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
         ResultSet rs = statement.executeQuery(Select_Query);

         rs.moveToInsertRow();
         rs.updateInt("role",emp.roleIndex);
         rs.updateString("Username",emp.username);
         rs.updateString("Name",emp.name);
         rs.updateString("Surname",emp.surname);
         rs.updateString("dateOfBirth",emp.dateOfBirth.toString());
         rs.updateString("dateOfStart",emp.dateOfStart.toString());
         rs.updateString("password",emp.password.toString());
         rs.insertRow();
         System.out.println("Added "+ emp.username + " successfully!");
      }
      catch(SQLException e)
      {
         System.out.println("Error: Couldn't insert employee to db");
         e.printStackTrace();
      }

   }

   public static void hireEmployee(Connection connection)
   {
      Scanner sc = new Scanner(System.in);
      EmployeeClass employee = new EmployeeClass();
      boolean stop = false;
      int counter=0;
      String[] fields = {"Username","Name","Surname","Date of Birth (YYYY-MM-DD)","Date of Start (YYYY-MM-DD)","Role (0-3)"};

      System.out.println("Hire Empolyee");
      String line= "";

      while (!stop) 
      {
         System.out.println("Enter "+ fields[counter]+ ": ");
         

            switch(counter)
            {
               case 0->
               {
                  line = sc.nextLine();
                  if(line.length()>20)
                  {
                     System.out.println("Username should be less than 20 characters");
                     counter--;
                  }

                  else if(line.length()==0)
                  {
                     line = "-";
                     employee.username=line;
                  }
                  else
                  {
                     employee.username=line;
                  }
                  counter++;
               }
               case 5->
               {
                  int temp = validateIntInput(sc);
                  while(temp<0 || temp >3)
                  {
                     System.out.println("Enter a value between 0 and 3");
                     temp = validateIntInput(sc);
                  }
                  employee.roleIndex = temp;
                  employee.setRole();
                  counter++;
               }
               case 1->
               {
                  line = sc.nextLine();
                  if(line.length()>20)
                  {
                     System.out.println("Name should be less than 20 characters");
                     counter--;
                  }

                  else if(line.length()==0)
                  {
                     line = "-";
                     employee.name=line;
                  }
                  else
                  {
                     employee.name=line;
                  }
                  counter++;
               }
               case 2->
               {
                  line = sc.nextLine();
                  if(line.length()>20)
                  {
                     System.out.println("Surname should be less than 20 characters");
                     counter--;
                  }
                  else if(line.length()==0)
                  {
                     line = "-";
                     employee.surname=line;
                  }
                  else
                  {
                     employee.surname=line;
                  }
                  counter++;
               }
               case 3->
               {
                  LocalDate date = enterDate();
                  employee.dateOfBirth=date;
                  counter++;
               }
               case 4->
               {
                  LocalDate date = enterDate();
                  employee.dateOfStart=date;
                  counter++;
               }
            
         }
         
         sc.reset();
         line="";
         if(counter==6)
         {
            stop=true;
         }
      }

      employee.password = "defaultPass";
      
      insertEmployee(employee, connection);

      sc.close();
   }

   public static void printRS(ResultSet resultSet)
   {
      
      try
      {
         ResultSetMetaData metaData = resultSet.getMetaData();
         int numberOfColumns = metaData.getColumnCount(); 
         Clear_Console();
         System.out.printf("db Table of workers Database:%n%n");

         // display the names of the columns in the ResultSet
         for (int i = 1; i <= numberOfColumns; i++) 
         {
            if(i==3)
               continue;
            System.out.printf("%s\t", metaData.getColumnName(i));
         }
         System.out.println();

         // display query results
         while (resultSet.next()) 
         {
            for (int i = 1; i <= numberOfColumns; i++) {
               if(i==3)
                  continue;
               System.out.printf("%-8s\t", resultSet.getObject(i));}
            System.out.println();
         }
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage()+ " Retrying....");
      }
   }

   // public static void main(String args[]) 
   // {
   //    hireEmployee();
   // }
   
   
   public static void main(String args[]) 
   {
      final String DATABASE_URL = "jdbc:mysql://localhost:3306/db?useTimezone=true&serverTimezone=UTC";        
      //final String SELECT_QUERY = "SELECT * FROM workers";
      // use try-with-resources to connect to and query the database
      try 
      {                        
         Connection connection = DriverManager.getConnection(DATABASE_URL, "Manager", "ManagerPass123");                     
         Statement statement = connection.createStatement();   
         hireEmployee(connection);

         ResultSet resultSet = select(statement);

         // get ResultSet's meta data
             
         
         
         printRS(resultSet);
         

	      connection.close();
      }
      catch (SQLException sqlException) 
      {
         sqlException.printStackTrace();
      }
   }
}