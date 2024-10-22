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
        int rows = 5, columns=1;
        String [][] W = {{"W   W   W"},{"W   W   W"},{"W   W   W"}, {" W  W  W "}, {"  W W W  "}};
    
        String [][] E = {{"EEEEEEEEE"},{"E        "},{"EEEEEEEEE"}, {"E        "}, {"EEEEEEEEE"}};

        String [][] L = {{"L        "},{"L        "},{"L        "}, {"L        "}, {"LLLLLLLLL"}};
        
        String [][] C = {{"CCCCCCCCC"},{"C        "},{"C        "}, {"C        "}, {"CCCCCCCCC"}};

        String [][] O = {{"OOOOOOOOO"},{"O       O"},{"O       O"}, {"O       O"}, {"OOOOOOOOO"}};
        
        String [][] M = {{"M       M"},{"M M   M M"}, {"M   M   M"},{"M       M"},{"M       M"}};
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(W[i][j]);
                System.out.print("   ");
                System.out.print(E[i][j]);
                System.out.print("   ");
                System.out.print(L[i][j]);
                System.out.print("   ");
                System.out.print(C[i][j]);
                System.out.print("   ");
                System.out.print(O[i][j]);
                System.out.print("   ");
                System.out.print(M[i][j]);
                System.out.print("   ");
                System.out.print(E[i][j]);
                System.out.print("   ");
            }
            
            System.out.println();
        }



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
        System.out.println("Group 13: LUAY HAMED, OUSSAMA TANFOURI, ANDREI COJOCARU, JIHAD KHOULY, PATRICK MARCHITAN\n");
        Main_Menu();
        System.out.println("Please an available option (A, B, C, D, E): ");
    }

    private static void Clear_Console()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }


}
