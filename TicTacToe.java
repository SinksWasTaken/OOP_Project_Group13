import java.util.Scanner;

public class TicTacToe {

    private static char[] board = new char[9];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean gameOngoing = true;

        while (true) //Loop breaks when the user enters "terminate"
        {
            Clear_Console();
            initializeBoard();
            System.out.println("New game started!");

            while (gameOngoing) //If the game hasnt ender (Winner found || Tie)
            {
                printBoard();
                System.out.println("Player " + currentPlayer + "'s turn. Enter a number between 1-9, or type 'terminate' to go back to the main menu:");

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("terminate")) //If the user input "terminate" at any point during the game then we stop it and return to nain menu
                {
                    System.out.println("Game terminated. Returning to the main menu...");
                    MainFunc.main(new String[0]);
                    Clear_Console();
                    return;
                }

                if (input.matches("[1-9]")) //a valid input was made
                {
                    int move = Integer.parseInt(input) - 1; //cast the char imput to int

                    if (board[move] != 'X' && board[move] != 'O') //if the slot is empty
                    {
                        board[move] = currentPlayer; // put fill the slot with the current player's icon
                        if (checkWin()) //if any of the players won
                        {
                            printBoard();
                            System.out.println("Player " + currentPlayer + " wins!");
                            break;
                        } 
                        else if (isBoardFull()) //if there is no winner and no slots to fill
                        {
                            printBoard();
                            System.out.println("The game is a tie!");
                            break;
                        } 
                        else //otherwise let the other player play
                        {
                            switchPlayer();
                        }
                        Clear_Console();
                    } 
                    else //if the slot is filled
                    {
                        Clear_Console();
                        System.out.println("Invalid move. Try again.");
                    }
                } 
                else //if the user enters an invalid input (a very big number or a character)
                {
                    Clear_Console();
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                }
            }

            System.out.println("Do you want to play again? Type 'yes' to continue or 'terminate' to go back to the main menu.");

            String playAgain = scanner.nextLine();

            boolean stop =false;

            while(!stop) //makes sure the user picks an option (either go back to main menu or play again)
            {
                if (playAgain.equalsIgnoreCase("terminate")) // if the user enters "terminate"
                {
                    System.out.println("Game terminated. Returning to the main menu...");
                    return;
                } 
                else if(playAgain.equalsIgnoreCase("yes")) // if the user enters "yes" in upper or lower case doesnt matter
                {
                    currentPlayer = 'X';
                    gameOngoing = true;
                    Clear_Console();
                    stop=true;
                }
                else // if the user enters an invalid input, the user has to enter something again
                {
                    System.out.print("\nInavailable Option Try Again: ");
                    scanner.reset();
                    playAgain = scanner.nextLine();

                }
                
            }
        }
    }

    public static void initializeBoard()  // creates the board with numbers for the slots
    {
        for (int i = 0; i < 9; i++) 
        {
            board[i] = (char) ('1' + i);
        }
    }

    public static void printBoard() // prints the gaem board
    {
        System.out.println("Current board:");
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("--+---+--");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("--+---+--");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
    }

    public static void switchPlayer() // changes to the other player's turn
    {
        if (currentPlayer == 'X') 
        {
            currentPlayer = 'O';
        } 
        else 
        {
            currentPlayer = 'X';
        }
    }

    public static boolean isBoardFull() //checks if all the slots are full
    {
        for (int i = 0; i < 9; i++) 
        {
            if (board[i] != 'X' && board[i] != 'O') 
            {
                return false;
            }
        }
        return true;
    }

    public static boolean checkWin() //??
    {
        int[][] winConditions = 
        {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) 
        {
            if (board[condition[0]] == currentPlayer &&
                board[condition[1]] == currentPlayer &&
                board[condition[2]] == currentPlayer) 
            {
                return true;
            }
        }

        return false;
    }

    private static void Clear_Console() // clears console
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
}
