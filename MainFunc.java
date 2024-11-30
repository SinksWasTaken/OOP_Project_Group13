import java.util.Scanner;
import java.sql.Connection;
import java.util.NoSuchElementException;



public class MainFunc 
{
    public static boolean isManager[] ={false}; 
    
    public static void loginScreen(String username,String password,Scanner sc)
    {
       
        sc.nextLine();

            System.out.println("Enter Username:");
            username = sc.nextLine();


            System.out.println("Enter Password:");
            password = sc.nextLine();

            
        
        
        System.out.println(username);
        System.out.println(password);

    }

    public static void Clear_Console()
    {    
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
    public static int validateIntInput(Scanner inputB)
    {
        boolean stop = false;
        int ans = 100;
        while(!stop)
        {
            try
            {
                if(inputB.hasNextInt())
                {
                    ans = inputB.nextInt();
                    stop =true;
                }
                else
                {             
                    System.out.println("Enter a valid NUMBER.");
                    inputB.nextInt();
                }
                
            
            }
            catch(NoSuchElementException e)
            {
                System.out.println("Error: Enter a number.");
                inputB.nextLine();
            }
        }   
        return ans;
    }
    
    public static void main(String[] args)
    {
        Clear_Console();
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Select option:");
            System.out.println("1.Login");
            System.out.println("2.Terminate");
            int option;
            option = validateIntInput(sc);
            switch(option)
            {
                case 1->
                {
                    String User="";
                    String Pass="";
                    Connection connection = null;
                    int trials=0;
                    while(true)
                    {   
                        //loginScreen(User, Pass,sc);
                        sc.nextLine();

                        System.out.println("Enter Username:");
                        User = sc.nextLine();
                    
                    
                        System.out.println("Enter Password:");
                        Pass = sc.nextLine();

                        Clear_Console();

                        connection=Authenticator.validateCredentials(User, Pass,isManager);
                    
                        
                        if(connection==null)
                        {
                            
                            System.out.println("Error: Connection Not Established\n");
                            trials++;
                            if(trials == 5)
                            {
                                break;
                            }
                            break;
                        }
                        else if(isManager[0])
                        {
                            
                            ManagerClass manager= new ManagerClass();
                            manager.conn = connection;
                            manager.getSelfFromDB(User);
                            manager.ManagerMenu(sc);
                        }
                        else
                        {
                            EmployeeClass employee = new EmployeeClass();
                            employee.conn = connection;
                            employee.getSelfFromDB(User);
                            employee.Menu(sc);
                        }

                    }
                    
                }
                case 2->
                {
                    Clear_Console();

                    System.out.println("See you next time\n");
                    sc.close();
                    break;
                }
                default->
                {
                    System.out.println("Choose a valid option\n");
                }
            }
            
        }
    }
}
