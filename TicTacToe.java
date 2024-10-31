import java.util.Scanner;

public class TicTacToe 
{
    /**
     * An array of 9 characters used as the game board.  
     */
    static char[] board = new char[9];
    /**
     * A char that determines which player's turn it currently is.
     */
    private static char currentPlayer = 'X';
    /**
     * This method contains all the functionality of this class.
     * <br>
     * The user is greeted with a Tic-Tac-Toe board filled with numbers from 1 to 9 for the user to select from.
     * <br>
     * Once the user picks a valid number, the corresponding slot will be filled with the current player's symbol. (X or O) Then the other player's turn start.
     * <br> 
     * The game keeps going on until one of the players win or when the whole board is full.
     * <br>
     * This method will keep running until the user enters the string "terminate".
     */
    public static void TicTacToeGame(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean gameOngoing = true;

        while (true) {
            Clear_Console();
            initializeBoard();
            System.out.println("New game started!");

            while (gameOngoing) {
                
                printBoard();
                System.out.println("Player " + currentPlayer + "'s turn. Enter a number between 1-9, or type 'terminate' to go back to the main menu:");

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("terminate")) 
                {
                    System.out.println("Game terminated. Returning to the main menu...");
                    
                    Clear_Console();
                    
                    return;
                }

                if (input.matches("[1-9]")) 
                {
                    int move = Integer.parseInt(input) - 1;

                    if (board[move] != 'X' && board[move] != 'O') 
                    {
                        board[move] = currentPlayer;
                        if (checkWin()) {
                            printBoard();
                            System.out.println("Player " + currentPlayer + " wins!");
                            break;
                        } else if (isBoardFull()) {
                            printBoard();
                            System.out.println("The game is a tie!");
                            break;
                        } else {
                            switchPlayer();
                        }
                        Clear_Console();
                    } 
                    
                    else 
                    {
                        Clear_Console();
                        System.out.println("Invalid move. Try again.");
                    }
                } 
                
                else 
                {
                    Clear_Console();
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                }
            }

            System.out.println("Do you want to play again? Type 'yes' to continue or 'terminate' to go back to the main menu.");

            String playAgain = scanner.nextLine();

            boolean stop =false;

            while(!stop)
            {
                if (playAgain.equalsIgnoreCase("terminate")) {
                    System.out.println("Game terminated. Returning to the main menu...");
                    
                    return;
                } 
                else if(playAgain.equalsIgnoreCase("yes")) {
                    currentPlayer = 'X';
                    gameOngoing = true;
                    Clear_Console();
                    stop=true;
                }
                else
                {
                    System.out.print("\nInavailable Option Try Again: ");
                    scanner.reset();
                    playAgain = scanner.nextLine();

                }
                
            }
        }
    }

    /**
     * Create empty board
     * <br>
     * This method fills in the character array "board" with numbers from '1' to '9' that is used for players to play on.
     */
    public static void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = (char) ('1' + i);
        }
    }

    /**
     * Print game board
     * 
     * This prints the board array in a tic-tac-toe game board notation.
     */

    public static void printBoard() {
        System.out.println("Current board:");
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("--+---+--");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("--+---+--");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
    }

    /**
     * Next player
     * 
     * 
     * This method is used to change to the other player. Used after a player places a piece on the board.
     */

    public static void switchPlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    /**
     * Check full board.
     * 
     * This method is used to determine if the current game board is full (no more spaces to play).<br>
     * A for loop iterated over the 9 slots on the board, if there is a slot that does not have an 'X' nor an 'O' the method returns false. Otherwise the method returns true.
     * @return A boolean that determines if the game board is full
     */

    public static boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                return false;
            }
        }
        return true;
    }

    /**
     * Check win condition.
     * 
     * This method is used to determine if the current game board state is a winning condition for one of the players.<br>
     * A matrix that contains all the winning condtions is defined. <br>
     * Using that matrix, a nested for loop is used to iterate over the game board to determine if a player has 3 pieces next to each other in a horizontal, vertical, or diagonal order.  
     * @return A boolean that determines if one of the players has won
     */

    public static boolean checkWin() 
    {
        int[][] winConditions = 
        {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},    //Horizontal Win
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},    //Vertical Win
            {0, 4, 8}, {2, 4, 6}                //Diagonal Win
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] == currentPlayer &&
                board[condition[1]] == currentPlayer &&
                board[condition[2]] == currentPlayer) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method clears the terminal
     * 
     * It uses a sequence of escape codes.<br>"\033" marks the start of the ANSI sequence.<br>"[H" moves the cursor to the top-left.<br>"[2J" clears everything after the cursor.<br>
     */
    private static void Clear_Console()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
}
