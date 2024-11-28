import java.time.LocalDate;

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
    int phoneNo;

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

    public void printEmployee()
    {
        System.out.println("username: "+ username);
        System.out.println("name: "+ name);
        System.out.println("surname: "+ surname);
        System.out.println("dateOfBirth: "+ dateOfBirth.toString());
        System.out.println("dateOfStart: "+ dateOfStart.toString());
        System.out.println("role: "+ role);
        System.out.println("email: "+ email);
        System.out.println("password: "+ password);
        System.out.println("phoneNo: "+ phoneNo);

    }
public void getSelfFromDB(String EMail, Connection connection)
    {
        try
        {
            String Select_Query = "SELECT * FROM workers WHERE name='" +EMail+"'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(Select_Query);

            rs.next();
            username = rs.getString("username");
            name = rs.getString("name");
            surname = rs.getString("surname");
            phoneNo = rs.getString("phone_no");
            roleIndex = rs.getInt("role");
            setRole();
            email = rs.getString("email");
            password = rs.getString("password");

            try 
            {
                dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                dateOfStart = LocalDate.parse(rs.getString("dateOfStart"));
    
            } 
            catch(DateTimeException e)
            {
                System.out.println("Error: Parsing date");
                e.printStackTrace();
            }
            

        }
        catch(SQLException e)
        {
            e.printStackTrace();
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
        System.out.println("username: "+ username);
        System.out.println("name: "+ name);
        System.out.println("surname: "+ surname);
        System.out.println("dateOfBirth: "+ dateOfBirth.toString());
        System.out.println("dateOfStart: "+ dateOfStart.toString());
        System.out.println("role: "+ role);
        System.out.println("email: "+ email);
        System.out.println("password: "+ password);
        System.out.println("phoneNo: "+ phoneNo);

    }
    
    public void updateProfile(Connection connection)
    {
        Scanner sc = new Scanner(System.in);

        for(int i=0;i<3;i++)
        {
            switch(i)
            {
                case 0->
                {
                    System.out.println("Enter new email (should contain '@' and be less than 30 characters): (leave empty if you dont want to update)");
                    String line = sc.nextLine();
                    if(line.length()>30)
                    {
                        System.out.println("email should be less than 30 characters");
                        i--;
                    }

                    else if(line.length()==0)
                    {
                        continue;
                    }
                    else
                    {
                        if(!line.contains("@"))
                        {
                            System.out.println("email should be contain an '@' symbol.");
                            i--;
                        }
                        else
                        {
                            email=line;
                        }
                    }
                }
                case 1->
                {
                    System.out.println("Enter new password (should be less than 20 characters): (leave empty if you dont want to update)");
                    String line = sc.nextLine();
                    if(line.length()>30)
                    {
                        System.out.println("password should be less than 20 characters");
                        i--;
                    }

                    else if(line.length()==0)
                    {
                        continue;
                    }
                    else
                    {
                        password=line;
                    }
                }
                case 2->
                {
                    System.out.println("Enter new phoneNo. (should begin with 5 and follow the format (5XXXXXXXXX) ): (leave empty if you dont want to update)");
                    String line = sc.nextLine();
                    if(line.length()==0)
                    {
                        continue;
                    }
                    else if(line.length()!=10)
                    {
                        System.out.println("phoneNo. should be exactly 10 characters");
                        i--;
                    }
                    else
                    {
                        if(line.charAt(0)!='5')
                        {
                            System.out.println("phoneNo. should begin with 5 and follow the format (5XXXXXXXXX).");
                            i--;
                        }
                        else
                        {
                            phoneNo=line;
                        }
                    }
                }
            }
        }
        updateEmployee(connection, this);
        sc.close();
    }

    public static void updateEmployee(Connection connection, EmployeeClass emp)
   {  
        try
        {
            String Select_Query = "SELECT * FROM workers WHERE name='"+ emp.name+ "' AND surname='" + emp.surname+"'";
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(Select_Query);
            
            rs.next();
            rs.updateString("password",emp.password);
            rs.updateString("email",emp.email);
            rs.updateString("phone_no",emp.phoneNo);
            rs.updateRow();

            System.out.println("Added "+ emp.username + " successfully!");
        }
        catch(SQLException e)
        {
            System.out.println("Error: Couldn't update employee in db: " + emp.name + " " + emp.surname);
            e.printStackTrace();
        }
   }



}
