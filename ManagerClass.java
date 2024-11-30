import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ManagerClass extends EmployeeClass
{

   public ManagerClass() 
   {
      isManager=true;
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
        return date;
   }

   private static ResultSet select(Statement statement)
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

   private static ResultSet select(Statement statement,String a, String b)
   {
      String SELECT_QUERY = " SELECT * FROM workers WHERE name='" +a+"' AND surname='" + b+"'"; 
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

   private static ResultSet select(Statement statement,int a)
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

   private static ResultSet select(Statement statement,String a)
   {
      String SELECT_QUERY = " SELECT * FROM workers WHERE username='" +a+"'"; 
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

   private void insertEmployee(EmployeeClass emp)
   {  
      try
      {
         String Select_Query = "SELECT * FROM workers";
         Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
         ResultSet rs = statement.executeQuery(Select_Query);

         rs.moveToInsertRow();
         rs.updateInt("role",emp.roleIndex);
         rs.updateString("Username",emp.username);
         rs.updateString("Name",emp.name);
         rs.updateString("Surname",emp.surname);
         rs.updateString("dateOfBirth",emp.dateOfBirth.toString());
         rs.updateString("dateOfStart",emp.dateOfStart.toString());
         rs.updateString("password",emp.password);
         rs.insertRow();
         MainFunc.Clear_Console();
         System.out.println("Added "+ emp.username + " successfully!");
      }
      catch(SQLException e)
      {
         System.out.println("Error: Couldn't insert employee to db");
         e.printStackTrace();
      }
   }

   private void hireEmployee()
   {
      Scanner sc = new Scanner(System.in);
      EmployeeClass employee = new EmployeeClass();
      boolean stop = false;
      int counter=0;
      String[] fields = {"Username","Name","Surname","Date of Birth (YYYY-MM-DD)","Date of Start (YYYY-MM-DD)","Role (0-3)"};

      System.out.println("Hire Empolyee");
      String line;

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
                  int temp = MainFunc.validateIntInput(sc);
                  while(temp<0 || temp >3)
                  {
                     System.out.println("Enter a value between 0 and 3");
                     temp = MainFunc.validateIntInput(sc);
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
                  if(!date.isAfter(LocalDate.now()))
                  {
                     employee.dateOfBirth=date;
                     counter++;
                  }
                  else
                  {
                     MainFunc.Clear_Console();
                     System.out.println("They can't be born in the future. Such technology doesn't exist yet.");
                  }
                  
               }
               case 4->
               {
                  LocalDate date = enterDate();
                  if(!date.isBefore(employee.dateOfBirth))
                  {
                     employee.dateOfStart=date;
                     counter++;
                  }
                  else
                  {
                     MainFunc.Clear_Console();
                     System.out.println("They are working here before they are born? They must have amazing dedication to this job!");
                     
                  }
               }
            
         }
         
         sc.reset();
         if(counter==6)
         {
            stop=true;
         }
      }

      employee.password = "defaultPass";
      
      this.insertEmployee(employee);

      
   }

   private void fireEmployee(String Name, String Surname)
   {
      if(Name.equals(this.name) && Surname.equals(this.surname))
      {
         System.out.println("Error: You can't fire yourself :(");
         return;
      }
      try 
      {   
         String Delete_Query = "DELETE FROM workers WHERE name='" +Name + "' AND surname='" + Surname+"'";
         Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

         statement.executeUpdate(Delete_Query);
         MainFunc.Clear_Console();
         System.out.println("Fired: " + Name + " " + Surname + '\n');
      }
      catch (SQLException e) 
      {
         System.err.println("Error: Couldn't find the employee in database: " + Name + " " +Surname);
         e.printStackTrace();
      }
   }

   private void updateEmployeeProfile(String name, String surname)
   {
      Scanner sc = new Scanner(System.in);

      EmployeeClass employee = new EmployeeClass();
      boolean stop = false;
      int counter=0;
      String[] fields = {"Username","Name","Surname","Date of Birth (YYYY-MM-DD)","Date of Start (YYYY-MM-DD)","Role (0-3)"};

      System.out.println("Update Empolyee");
      String line;

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
                  if(!date.isAfter(LocalDate.now()))
                  {
                     employee.dateOfBirth=date;
                     counter++;
                  }
                  else
                  {
                     MainFunc.Clear_Console();
                     System.out.println("You can't be born in the future. Such technology doesn't exist yet.");
                  }
               }
            case 4->
               {
                  LocalDate date = enterDate();
                  if(!date.isAfter(employee.dateOfStart))
                  {
                     employee.dateOfStart=date;
                     counter++;
                  }
                  else
                  {
                     MainFunc.Clear_Console();
                     System.out.println("You are working here before you are born? You have amazing dedication to this job!");
                     
                  }
               }
            case 5->
               {
                  
                  int temp = MainFunc.validateIntInput(sc);
                  while(temp<0 || temp >3)
                  {
                     System.out.println("Enter a value between 0 and 3");
                     temp = MainFunc.validateIntInput(sc);
                  }
                  employee.roleIndex = temp;
                  employee.setRole();
                  counter++;
               }
            
         }
         
         sc.reset();
         if(counter==6)
         {
            stop=true;
         }
      }
   
      updateEmployee(employee,name,surname);
   }
   
   private void updateEmployee(EmployeeClass emp, String Name, String Surname)
   {  
        try
        {
            String Select_Query = "SELECT * FROM workers WHERE name='"+ Name+ "' AND surname='" + Surname +"'";
            Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(Select_Query);
            
           
            rs.next();
            rs.updateString("name",emp.name);
            rs.updateString("surname",emp.surname);
            rs.updateString("username",emp.username);
            if(!emp.isManager)
            {
               rs.updateInt("role",emp.roleIndex);
               System.out.println("\nA manager can't change thier own roles.");
            }
            rs.updateString("dateOfBirth",emp.dateOfBirth.toString());
            rs.updateString("dateOfStart",emp.dateOfStart.toString());
            
            rs.updateRow();

            System.out.println("Updated "+ Name+ " " + Surname +" to "+ emp.name+ " " +emp.surname + " successfully!");
        }
        catch(SQLException e)
        {
            System.out.println("Error: Couldn't update employee in db: " + emp.name + " " + emp.surname);
            e.printStackTrace();
        }
   }

   public static void printRS(ResultSet resultSet)
   {
      try
      {
         ResultSetMetaData metaData = resultSet.getMetaData();
         int numberOfColumns = metaData.getColumnCount(); 
         //MainFunc.Clear_Console();
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
               if(i==3)//Password
                  continue;
               if(i==4)
               {
                  switch(resultSet.getInt(i))
                  {
                     case 0 ->
                     {
                        System.out.print("Manager\t");
                     }
                     case 1 ->
                     {
                        System.out.print("Technician\t");
                     }
                     case 2 ->
                     {
                        System.out.print("Engineer\t");
                     }
                     case 3 ->
                     {
                        System.out.print("Intern\t");
                     }
                  }
                  continue;
               }
               System.out.printf("%-8s\t", resultSet.getObject(i));}
            System.out.println();
         }
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage()+ " Retrying....");
      }
   }

   public void ManagerMenu(Scanner sc) 
   {
      //sc.reset();
      boolean menuStop=false;
      while(!menuStop)
      {
         //MainFunc.Clear_Console();
         System.out.println("Welcome "+ this.name + " " + this.surname + "!\n");

         System.out.println("Select option:");
         System.out.println("1.Display");
         System.out.println("2.Hire Employee");
         System.out.println("3.Fire Employee");
         System.out.println("4.Update Employee Non-Profile");
         System.out.println("5.Own Profile Operations");
         System.out.println("6.Log Out");
         sc.reset();
         int option = MainFunc.validateIntInput(sc);
         switch(option)
         {
           
            case 1->//Display
            {
               MainFunc.Clear_Console();   
               boolean stopDisplay =false;
               while(!stopDisplay)
               {
                  try
                  {
                     
                     System.out.println("Select option:");
                     System.out.println("1.All");
                     System.out.println("2.With Role");
                     System.out.println("3.With Username");
                     System.out.println("4.Previous Menu");
                     Statement stat = this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                     
                  
                     int dispOption = MainFunc.validateIntInput(sc);

                     switch(dispOption)
                     {
                        case 1->//ALL
                        {
                           MainFunc.Clear_Console();
                           ResultSet rs= select(stat);
                           if(rs.next())
                           {
                              rs.previous();
                              printRS(rs);
                           }
                           else
                           {
                              System.out.println("No employees found");
                           }
                           break;
                        }
                        case 2->//with Role
                        {
                           sc.reset();
                           MainFunc.Clear_Console();
                           System.out.println("Enter an option");
                           System.out.println("0-Manager");
                           System.out.println("1-Technician");
                           System.out.println("2-Engineer");
                           System.out.println("3-Intern");

                           int roleSelection = MainFunc.validateIntInput(sc);
                           while(roleSelection>3 ||roleSelection<0)
                           {
                              System.out.println("Enter a valid option (from 0 to 3)");
                              roleSelection = MainFunc.validateIntInput(sc);
                           }

                           ResultSet rs= select(stat,roleSelection);
                           
                           if(rs.next())
                           {
                              rs.previous();
                              printRS(rs);
                           }
                           else
                           {
                              System.out.println("No employees of that criterea found");
                           }
                           
                           break;
                        }
                        case 3->//With username
                        {
                           try
                           {
                              MainFunc.Clear_Console();
                              sc.reset();
                              sc.nextLine();
                              System.out.println("Enter username:");
                              String usrname = sc.nextLine();
                              ResultSet rs= select(stat,usrname);
                              if(rs.next())
                              {
                                 rs.previous();
                                 printRS(rs);
                              }
                              else
                              {
                                 System.out.println("No employees of that criterea found");
                              }
                              
                              
                           }
                           catch(RuntimeException e)
                           {
                              System.out.println("Error: Enter a proper string.");
                           }
                           break;
                        }
                        case 4->
                        {
                           stopDisplay=true;
                        }
                        default->
                        {
                           System.out.println("Choose a valid option\n");
                        }

                     }
                  }
                  catch(SQLException e)
                  {
                     System.out.println("Error: Couldn't initiate Statement");
                     e.printStackTrace();
                  }
               }
            }
            case 2->//Hire
            {
               MainFunc.Clear_Console();
               this.hireEmployee();
            }
            case 3->//Fire
            {
               sc.reset();
               MainFunc.Clear_Console();
               String Name = "";
               String Surname= "";
              
               sc.nextLine();
               try
               {
                  System.out.println("Enter name:");
                  Name = sc.nextLine();
                  System.out.println("Enter surname:");
                  Surname = sc.nextLine();
               }
               catch(RuntimeException e)
               {
                  System.out.println("Error: Enter a proper string.");
               }

               try
               {
                  Statement stat = this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                  ResultSet rs = select(stat,Name,Surname);
                  if(!rs.next())
                  {
                     System.out.println("Employee not in database");
                     break;
                  }
                  else
                  {
                     rs.previous();
                  }
               }
               catch(SQLException e)
               {
                  System.out.println("Error: Couldn't access database");
               }
               MainFunc.Clear_Console();
               this.fireEmployee(Name, Surname);
               
            }
            case 4->//UpdateOther
            {
               MainFunc.Clear_Console();
               sc.reset();
               String Name = "";
               String Surname= "";
               
               sc.nextLine();
               try
               {
                  System.out.println("Enter name:");
                  Name = sc.nextLine();
                  System.out.println("Enter surname:");
                  Surname = sc.nextLine();
               }
               catch(RuntimeException e)
               {
                  System.out.println("Error: Enter a proper string.");
               }
               try
               {
                  Statement stat = this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                  ResultSet rs = select(stat,Name,Surname);
                  if(!rs.next())
                  {
                     System.out.println("Employee not in database");
                     break;
                  }
                  else
                  {
                     rs.previous();
                  }
               }
               catch(SQLException e)
               {
                  System.out.println("Error: Couldn't access database");
               }
               MainFunc.Clear_Console();

               this.updateEmployeeProfile(Name, Surname);

            }
            case 5->//SelfOperations
            {
               MainFunc.Clear_Console();
               this.Menu(sc);
            }
            case 6->//Log Out
            {
               try
               {
                  this.conn.close();
                  menuStop=true;
                  MainFunc.Clear_Console();
                  System.out.println("Logged out Successfully! \n");
               }
               catch(SQLException e)
               {
                  System.out.println("Error: Couldn't close connection.");
               }

            }
            default->
            {
               System.out.println("Choose a valid option\n");
            }
         }
      }
   }
   
}