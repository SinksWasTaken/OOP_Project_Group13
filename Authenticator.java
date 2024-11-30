import java.sql.*;
public class Authenticator 
{
    public static Connection validateCredentials(String USRNAME, String PSWRD,boolean isManager[])
    {
        Connection finalConnection=null;
        String DatabaseUrl ="jdbc:mysql://localhost:3306/db?useTimezone=true&serverTimezone=UTC";   
        try
        {
            String SELECT_QUERY = "SELECT * FROM workers WHERE username='" + USRNAME +"' AND password='" + PSWRD+ "'"; 
            
            Connection connection = DriverManager.getConnection(DatabaseUrl,"Authenticator","Authenticator");
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = statement.executeQuery(SELECT_QUERY);
            if(!rs.next())
            {
                System.out.println("Wrong Username & Password Try again");
                return finalConnection;
            }
            
            if(USRNAME.equals(rs.getString("username")))
            {
                if(PSWRD.equals(rs.getString("password")))
                {
                    if(rs.getInt("role")==0)
                    {
                        finalConnection = DriverManager.getConnection(DatabaseUrl,"Manager","ManagerPass123");
                        System.out.println("Manager Connection Successful");
                        isManager[0]=true;
                    }
                    else
                    {
                        finalConnection = DriverManager.getConnection(DatabaseUrl,"Regular","RegularPass123");
                        System.out.println("Regular Connection Successful");
                        isManager[0]=false;
                    }
                }
                else
                {
                    System.out.println("Wrong Password Try again");
                }

            }
            else
            {
                System.out.println("Wrong Username & Password Try again");

            }
            
        }
        catch(SQLException e)
        {
            System.out.println("Error: Couldn't Connect to Database");
            e.printStackTrace();
        }

        return finalConnection;
    } 
}
