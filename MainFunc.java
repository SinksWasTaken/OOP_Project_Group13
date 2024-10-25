import java.util.Scanner;


public class MainFunc{

    public static void main(String[] args) {
       
        boolean terminate = false;       
        Scanner input = new Scanner(System.in);
        
        while(terminate == false){
            
            WelcomeScreen();
            
            String option = input.nextLine().toUpperCase();
       
            switch (option) {         
                case "A" -> {
                    Clear_Console();  
                    System.out.println("You have selected option A");
                    
                }
                case "B" -> {
                    Clear_Console();
                    
                    MatrixOperations.main(args);

                    break;
                }
                case "C" -> {
                    Clear_Console();
                    System.out.println("You have selected option C");
                }
                case "D" -> {
                    Clear_Console();
                    System.out.println("You have selected option D");
                    TicTacToe.main(args);
                    break;
                }
                case "E" -> {
                    Clear_Console();
                    System.out.println("You have selected option E\nProgram was Terminated");
                    terminate = true;
                }
                default -> System.out.println("INVALID INPUT!!!, no such option exists, Please enter select a valid option (A, B, C, D, E)");

            }     
        }

        input.close();
    }
    
    private static void Welcome(){

        Clear_Console();

        String[] welcome = 
                    {
                        """
                         _    _      _                          \r
                        | |  | |    | |                         \r
                        | |  | | ___| | ___ ___  _ __ ___   ___ \r
                        | |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\\r
                        \\  /\\  /  __/ | (_| (_) | | | | | |  __/\r
                         \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|\r
                        
                        """
        };
        String[] Luay = 
                    {
                        """
                         _                         _   _                          _ \r
                        | |                       | | | |                        | |\r
                        | |    _   _  __ _ _   _  | |_| | __ _ _ __ ___   ___  __| |\r
                        | |   | | | |/ _` | | | | |  _  |/ _` | '_ ` _ \\ / _ \\/ _` |\r
                        | |___| |_| | (_| | |_| | | | | | (_| | | | | | |  __/ (_| |\r
                        \\_____/\\__,_|\\__,_|\\__, | \\_| |_/\\__,_|_| |_| |_|\\___|\\__,_|\r
                                            __/ |                                   \r
                                           |___/                                    \r
                        """
                    };
        String[] Oussema = 
                    {
                        """
                         _____                                      _____            __                 _ \r
                        |  _  |                                    |_   _|          / _|               (_)\r
                        | | | |_   _ ___ ___  ___ _ __ ___   __ _    | | __ _ _ __ | |_ ___  _   _ _ __ _ \r
                        | | | | | | / __/ __|/ _ \\ '_ ` _ \\ / _` |   | |/ _` | '_ \\|  _/ _ \\| | | | '__| |\r
                        \\ \\_/ / |_| \\__ \\__ \\  __/ | | | | | (_| |   | | (_| | | | | || (_) | |_| | |  | |\r
                         \\___/ \\__,_|___/___/\\___|_| |_| |_|\\__,_|   \\_/\\__,_|_| |_|_| \\___/ \\__,_|_|  |_|\r
                        
                        """
                    };
        String[] Andrei = 
                    {
                        """
                          ___            _          _   _____       _                            \r
                         / _ \\          | |        (_) /  __ \\     (_)                           \r
                        / /_\\ \\_ __   __| |_ __ ___ _  | /  \\/ ___  _ _   _  ___ __ _ _ __ _   _ \r
                        |  _  | '_ \\ / _` | '__/ _ \\ | | |    / _ \\| | | | |/ __/ _` | '__| | | |\r
                        | | | | | | | (_| | | |  __/ | | \\__/\\ (_) | | |_| | (_| (_| | |  | |_| |\r
                        \\_| |_/_| |_|\\__,_|_|  \\___|_|  \\____/\\___/| |\\__,_|\\___\\__,_|_|   \\__,_|\r
                                                                  _/ |                           \r
                                                                 |__/                            \r
                        """
                    };
        String[] Patrick = 
                    {
                        """
                        ______          _   _      _     ___  ___               _     _ _              \r
                        | ___ \\        | | (_)    | |    |  \\/  |              | |   (_) |             \r
                        | |_/ /_ _ _ __| |_ _  ___| | __ | .  . | __ _ _ __ ___| |__  _| |_ __ _ _ __  \r
                        |  __/ _` | '__| __| |/ __| |/ / | |\\/| |/ _` | '__/ __| '_ \\| | __/ _` | '_ \\ \r
                        | | | (_| | |  | |_| | (__|   <  | |  | | (_| | | | (__| | | | | || (_| | | | |\r
                        \\_|  \\__,_|_|   \\__|_|\\___|_|\\_\\ \\_|  |_/\\__,_|_|  \\___|_| |_|_|\\__\\__,_|_| |_|\r
                        
                        """
                    };
        String[] Jihad = 
                    {
                        """
                           ___ _ _               _   _   ___                 _       \r
                          |_  (_) |             | | | | / / |               | |      \r
                            | |_| |__   __ _  __| | | |/ /| |__   ___  _   _| |_   _ \r
                            | | | '_ \\ / _` |/ _` | |    \\| '_ \\ / _ \\| | | | | | | |\r
                        /\\__/ / | | | | (_| | (_| | | |\\  \\ | | | (_) | |_| | | |_| |\r
                        \\____/|_|_| |_|\\__,_|\\__,_| \\_| \\_/_| |_|\\___/ \\__,_|_|\\__, |\r
                                                                                __/ |\r
                                                                               |___/ \r
                        
                        """
                    };
                    
        System.out.print(welcome[0]);
        System.out.print(Luay[0]);
        System.out.print(Oussema[0]);
        System.out.print(Andrei[0]);
        System.out.print(Patrick[0]);
        System.out.print(Jihad[0]);

    }
    
    private static void Main_Menu(){
       
        System.out.println("Please select one of the following options: ");
        System.out.println("[A] Statistical Information About an Array");
        System.out.println("[B] Matrix Operations,");
        System.out.println("[C] Text Encryption/Decryption");
        System.out.println("[D] Tic-tac-toe HotSeat,");
        System.out.println("[E] Terminate.");
    }

    private static void WelcomeScreen()
    {
        Clear_Console();
        Welcome();
        System.out.println();
        
        Main_Menu();
        System.out.println("Please an available option (A, B, C, D, E): ");
    }

    private static void Clear_Console()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }


}
