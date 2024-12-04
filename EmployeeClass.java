import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class EmployeeClass 
{
    //Profile Info
    String username;
    String name;
    String surname;
    LocalDate dateOfBirth;
    LocalDate dateOfStart;
    String role;
    int roleIndex;
    //Non-Profile Info
    String email;
    String password;
    String phoneNo;
    //Extra Info
    boolean isManager;
    protected Connection conn;

    public void getSelfFromDB(String Username)
    {
        try
        {
            String Select_Query = "SELECT * FROM workers WHERE username='" +Username+"'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(Select_Query);

            rs.next();
            this.username = rs.getString("username");
            this.name = rs.getString("name");
            this.surname = rs.getString("surname");
            this.phoneNo = rs.getString("phone_no");
            this.roleIndex = rs.getInt("role");
            this.setRole();
            this.email = rs.getString("email");
            this.password = rs.getString("password");

            try 
            {
                this.dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                this.dateOfStart = LocalDate.parse(rs.getString("dateOfStart"));
    
            } 
            catch(DateTimeException e)
            {
                MainFunc.Clear_Console();
                System.out.println("Error: Parsing date");
               //e.printStackTrace();
            }
            catch(NullPointerException e)
            {
                MainFunc.Clear_Console();
                System.out.println("\nWarning: Missing Data (found NULL values)\n");
            }
            

        }
        catch(SQLException e)
        {
           //e.printStackTrace();
        }
        
    }

    public void setRole()
    {
        switch(roleIndex)
        {
            case 0 ->
            {
                role = "Manager";
            }
            case 1 ->
            {
                role = "Technician";
            }
            case 2 ->
            {
                role = "Engineer";
            }
            case 3 ->
            {
                role = "Intern";
            }
        }
    }

    public void printSelf()
    {
        
        MainFunc.Clear_Console();
        String dob = this.dateOfBirth == null ? "" : dateOfBirth.toString();
        String dos = this.dateOfStart == null ? "" : dateOfStart.toString();

        System.out.println("Your Information: \n");
        
        System.out.println("username: "+ username);
        System.out.println("name: "+ name);
        System.out.println("surname: "+ surname);
        System.out.println("dateOfBirth: "+ dob);
        System.out.println("dateOfStart: "+ dos);
        System.out.println("role: "+ role);
        System.out.println("email: "+ email);
        System.out.println("password: "+ password);
        System.out.println("phoneNo: "+ phoneNo +"\n\n");

    }
    
    public void updateOwnProfile()
    {
        Scanner sc = new Scanner(System.in);

        for(int i=0;i<3;i++)
        {
            switch(i)
            {
                case 0->
                {
                    System.out.println("Enter new email (should contain '@' and be less than 30 characters): (leave empty if you dont want to update unless its your first log-in)");
                    String line = sc.nextLine();
                    if(line.length()>30)
                    {
                        MainFunc.Clear_Console();
                        System.out.println("email should be less than 30 characters");
                        i--;
                    }

                    else if(line.length()==0)
                    {
                        if(this.email==null)
                        {
                            MainFunc.Clear_Console();
                            i--;
                            System.out.println("On first log-in you should add an email");
                        }
                        continue;
                    }
                    else
                    {
                        if(!line.contains("@"))
                        {
                            MainFunc.Clear_Console();
                            System.out.println("email should be contain an '@' symbol.");
                            i--;
                        }
                        else
                        {
                            email=line.toLowerCase();
                        }
                    }
                }
                case 1->
                {
                    System.out.println("Enter new password (8-20 characters): (leave empty if you dont want to update unless its your first log-in)");
                    String line = sc.nextLine();
                    if(line.length()>20)
                    {
                        MainFunc.Clear_Console();
                        System.out.println("password should be less than 20 characters");
                        i--;
                    }

                    else if(line.length()<8)
                    {
                        if(this.password.equals("defaultPass"))
                        {
                            i--;
                            MainFunc.Clear_Console();
                            System.out.println("On first log-in you should create a new password");
                        }
                        else if(line.length()==0)
                        {
                            password=line;
                        }
                        else
                        {
                            MainFunc.Clear_Console();
                            System.out.println("password should be atleast 8 characters");
                            i--;
                        }
                    }
                    else
                    {
                        password=line;
                    }
                }
                case 2->
                {
                    System.out.println("Enter new phoneNo. (should begin with 5 and follow the format [5XXXXXXXXX] ): (leave empty if you dont want to update unless its your first log-in)");
                    String line = sc.nextLine();
                    boolean hasChars=false;
                    for(int j=0;j<line.length();j++)
                    {
                        if(line.charAt(j)>'9' || line.charAt(j)<'0')
                        {
                            hasChars=true;
                        }
                    }
                    if(line.length()==0)
                    {
                        if(this.phoneNo==null)
                        {
                            i--;
                            MainFunc.Clear_Console();
                            System.out.println("On first log-in you should add a phoneNo.");
                        }
                        continue;
                    }
                    else if(line.length()!=10)
                    {
                        MainFunc.Clear_Console();
                        System.out.println("phoneNo. should be exactly 10 characters");
                        i--;
                    }
                    else if(line.charAt(0)!='5')
                    {
                        MainFunc.Clear_Console();
                        System.out.println("phoneNo. should begin with 5 and follow the format (5XXXXXXXXX).");
                        i--;
                    }
                    else if(hasChars)
                    {
                        MainFunc.Clear_Console();
                        System.out.println("phoneNo. should not have any characters.");
                        i--;
                    }
                    else
                    {
                        phoneNo=line;
                    }
                }
            }
        }
        updateEmployee(this);
    }

    public void updateEmployee(EmployeeClass emp)
   {  
        try
        {
            String Select_Query = "SELECT * FROM workers WHERE username='"+ emp.username+"'";
            Statement statement = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(Select_Query);
            
            rs.next();
            rs.updateString("password",emp.password);
            rs.updateString("email",emp.email);
            rs.updateString("phone_no",emp.phoneNo);
            rs.updateRow();

            MainFunc.Clear_Console();
            System.out.println("Added "+ emp.username + " successfully!");
        }
        catch(SQLException e)
        {
            MainFunc.Clear_Console();
            System.out.println("Error: Couldn't update employee in db: " + emp.name + " " + emp.surname);
            //e.printStackTrace();
        }
   }

    public void Menu(Scanner sc)
    {
        //sc.reset();
        boolean menuStop=false;
        
        while(!menuStop)
        {
            //MainFunc.Clear_Console();
            System.out.println("Welcome "+ this.name + " " + this.surname + "!\n");
            

            System.out.println("Select option:");
            System.out.println("1.View Profile");
            System.out.println("2.Update Profile");
            if(!this.isManager)
            {
                System.out.println("3.Log out");
            }
            else
            {
                System.out.println("3.Previous Menu");
            }
            sc.reset();
            int option = MainFunc.validateIntInput(sc);
            switch(option)
            {
            
                case 1->//Display
                {
                    MainFunc.Clear_Console();
                    getSelfFromDB(username);
                    printSelf();

                }
                case 2->//Update Profile Info
                {
                    MainFunc.Clear_Console();
                    getSelfFromDB(username);
                    updateOwnProfile();

                }
                case 3->//Log Out
                {
                    if(this.isManager)
                    {
                        menuStop=true;
                        MainFunc.Clear_Console();
                        break;
                        
                    }
                    
                    try
                    {
                       this.conn.close();
                       menuStop = true;
                       MainFunc.Clear_Console();
                       
                    }
                    catch(SQLException e)
                    {
                        MainFunc.Clear_Console();
                        System.out.println("Error: Couldn't close connection.");
                    }
                }
                default->
                {
                    MainFunc.Clear_Console();
                    System.out.println("Choose a valid option\n");
                }
            }
        }
    }

}
