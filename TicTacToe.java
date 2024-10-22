import java.util.Scanner;

public class TicTacToe {

    private static char[] board = new char[9];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();
        playGame();
    }

    public static void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = (char) ('1' + i);
        }
    }

    public static void printBoard() {
        System.out.println("Current board:");
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("--+---+--");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("--+---+--");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOngoing = true;

        while (gameOngoing) {
            printBoard();
            System.out.println("Player " + currentPlayer + "'s turn. Enter a number between 1-9, or type 'terminate' to go back to the main menu:");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("terminate")) {
                System.out.println("Game terminated. Returning to the main menu...");
                MainFunc.main(new String[0]);
                return;
            }

            int move = Integer.parseInt(input) - 1;

            if (move >= 0 && move < 9 && board[move] != 'X' && board[move] != 'O') {
                board[move] = currentPlayer;
                if (checkWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameOngoing = false;
                } else if (isBoardFull()) {
                    printBoard();
                    System.out.println("The game is a tie!");
                    gameOngoing = false;
                } else {
                    switchPlayer();
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
    }

    public static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                return false;
            }
        }
        return true;
    }

    public static boolean checkWin() {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // columns
            {0, 4, 8}, {2, 4, 6}              // diagonals
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
}
