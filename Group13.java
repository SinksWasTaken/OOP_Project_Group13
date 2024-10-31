import java.util.Arrays;
import java.util.Scanner;


public class Group13
{

    public static void main(String[] args) {
       
        boolean terminate = false;       
        Scanner input = new Scanner(System.in);
        String option;
        Clear_Console();    
        Welcome();
        while(terminate == false)
        {
            Main_Menu();
            
            option = input.nextLine();
       
            switch (option.toUpperCase()) {         
                case "A" -> {
                    Clear_Console();  
                    
                    try
                    {
                        displayStatisticalInformation();
                    }
                     
                    catch(ArithmeticException e)
                    {
                        System.out.println(e);
                        return;
                    }
                    
                    break;
                    
                }
                case "B" -> {
                    Clear_Console();
                    try
                    {
                        MatrixOps();
                    }
                    catch(ArithmeticException e)
                    {
                        System.out.println(e);
                        return;
                    }

                    break;
                }
                case "C" -> {
                    Clear_Console();
                    C_submenu();
                    break;
                }
                case "D" -> {
                    Clear_Console();
                    
                    TicTacToeGame();
                    
                    break;
                }
                case "E" -> {
                    Clear_Console();
                    
                    terminate = true;
                }
                default -> 
                {
                    Clear_Console();
                    System.out.println("INVALID INPUT!!!, no such option exists, Please enter select a valid option (A, B, C, D, E)\n\n");
                    break;
                }
            }     
        }

        input.close();
    }

    /**
     * This method greets the user with a welcome message composed of a "Welcome" text and a list of the groups members' names.
     * 
     */
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

    /**
     * This method displays the names of the tasks the user can select from. Each task is assigned a letter from A to E.
     * 
     */
    private static void Main_Menu(){
       
        System.out.println("Please select one of the following options: ");
        System.out.println("[A] Statistical Information About an Array");
        System.out.println("[B] Matrix Operations,");
        System.out.println("[C] Text Encryption/Decryption");
        System.out.println("[D] Tic-tac-toe HotSeat,");
        System.out.println("[E] Terminate.");
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

    /**
     *  This method contains all the functionality of the Matrix Operations task.
     *  <br>
     *  The user is greeted with a menu composed of a bunch of matrix operations that the user cna pick from.<br>Each option is assigned a number from 1 to 4. Option 5 will return the user to the main menu. 
     *  <br>
     *  Once the user picks a valid option, another method corresponding to their pick, will be called.
     *  <br> 
     *  This method will keep running until the user selects option 5.
     */
    public static void MatrixOps()
    {
        Scanner userInput = new Scanner(System.in);
        boolean exit = false;
        Clear_Console();

        System.out.print("Matrix Operations \n\n");
        while(!exit)
        {
            System.out.print("[1] Transpose of a Matrix\n[2] Multiply 2 Matrices Element-wise\n[3] Multiply 2 Matrices\n[4] Inverse of a Matrix\n[5] Main Menu\n\n");
            
            switch (validateIntInput(userInput)) {
                case 1 -> {
                    Clear_Console();    
                    System.out.println("Transpose of a Matrix");
                    transpose();
                }
                case 2 -> {
                    Clear_Console(); 
                    System.out.println("Multiply 2 Matrices Element-wise");
                    multiplyMatrixElements();
                }
                case 3 -> {
                    Clear_Console(); 
                    System.out.println("Multiply 2 Matrices");
                    multiplyMatrices();
                }
                case 4 -> {
                    Clear_Console(); 
                    System.out.println("Inverse of a Matrix");
                    matrixInverse();
                }
                case 5 -> exit=true;
                default -> {
                    Clear_Console(); 
                    System.out.print("Choose a number between 1 and 5\n\n");
                }
                    
            }
        }

    }

    /**
     * Creates a new matrix
     * <br>
     * This method is used to create a matrix with dimentions "rows","cols". Where "rows" and "cols" are values recieved from the user.
     * 
     * @return A matrix whom the user selects its dimentions
     */
    private static double[][] createMatrix()
    {
        Scanner inputB =  new Scanner(System.in);
        System.out.println("Enter Rows: ");
        int rows = validateIntInput(inputB);
        System.out.println("Enter Columns: ");
        int cols = validateIntInput(inputB);

        double[][] mat = new double[rows][cols];
        

        inputB.reset();
        return mat;
    }

    /**
     * Trasnpose of a Matrix
     * <br>
     * This method lets the user create a matrix and fill it with their desired values. Then prints that matrix's transpose. <br>
     * A matrix's transpose is another matrix with the same dimentions, but the rows and columns swap places. Example:<br><br>
     * 
     * |1 2 3|      |1 4 7|<br>
     * |4 5 6|  ->  |2 5 8|<br>
     * |7 8 9|      |3 6 9|<br>
     */
    private static void transpose()
    {
        
        System.out.println("Matrix: ");
        double[][] mat1 = createMatrix();
        fillMat(mat1);

        double[][] ans = new double[mat1[0].length][mat1.length];

        for(int i =0 ;i< mat1.length;i++)
        {
            for(int j =0; j<mat1[0].length;j++)
            {
                ans[j][i] = mat1[i][j];
            }
        }
    
        printMat(ans);
    }

    /**
     * Multiply elements of 2 Martices. 
     * <br>
     * This method lets the user create 2 matrices, and fill them with whatever values they want. <br>Then prints a new matrix whose values are gotten from multiplying the elements of both matrices.
     * <br>
     * 
     * 
     * First, the rows and columns of both matrices are checked to make sure they are equal, otherwise all the elements cannot be multiplied.<br>
     * Then, a nested for loop iterates over both matrices and multiplies the elements in the same slots and stores their product in a new matrix.<br>
     * Finally, the new matrix with all the products is printed. Example:<br><br>
     * 
     * |1 2 3|     |1 2 3|      |1 4 9|<br>
     * |1 2 3|  *  |4 5 6|  ->  |1 4 9|<br>
     * |1 2 3|     |7 8 9|      |1 4 9|<br>
     * 
     */
    private static void multiplyMatrixElements()
    {
        
        System.out.println("Matrix 1: \n");
        double[][] mat1 = createMatrix();

        System.out.println("Matrix 2: \n");
        double[][] mat2 = createMatrix();


        if(mat1.length!=mat2.length)
        {
            if(mat1[0].length!=mat2[0].length)
            {
                System.out.println("Columns and rows are not equal. Cannot multiply matrices");
            } 
            else
            {
                System.out.println("Rows are not equal. Cannot multiply matrices");
            }
            
        }
        else if(mat1[0].length!=mat2[0].length)
        {
            System.out.println("Columns are not equal. Cannot multiply matrices");
        } 
        else
        {
            System.out.println("Matrix 1: \n");
            fillMat(mat1);
            System.out.println("Matrix 2: \n");
            fillMat(mat2);
            double[][] ans = new double[mat1.length][mat1[0].length];
            for(int i=0; i< mat1.length;i++)
            {
                for(int j=0; j< mat1[0].length;j++)
                {
                    if(Double.MAX_VALUE/Math.abs(mat2[i][j])<Math.abs(mat1[i][j]))
                    {
                        throw new ArithmeticException("Overflow: " + mat2[i][j] + " * " + mat1[i][j]);
                    }
                    else if(-(Double.MAX_VALUE)/Math.abs(mat2[i][j]) > Math.abs(mat1[i][j]))
                    {
                        throw new ArithmeticException("Underflow: " + mat2[i][j] + " * " + mat1[i][j]);
                    }
                    else
                    {
                        ans[i][j] = mat1[i][j] * mat2[i][j];
                    }
                }
                
            }

            printMat(ans);
        }

    }

    /**
     * Prints a matrix
     * <br>
     * This method is used to print all the values of a matrix with upto 2 decimal places.<br>
     * A a nested for loop is used to iterate over the matrix to print the value in each slot.<br>
     * Before the first element and after the last element of each row, a '|' is printed to make it look like a proper matrix.
     * @param mat A 2D matrix
     */
    private static void printMat(double[][] mat)
    {
        for (double[] mat1 : mat) {
            for (int j = 0; j<mat[0].length; j++) 
            {

                if(j==0)
                {
                    System.out.print('|');
                }

                System.out.printf("%.2f",mat1[j]);

                if(j<mat[0].length-1)
                {
                    System.out.print(" ");
                }
                else
                {
                    System.out.print("|\n");
                }
            }
        }
        System.out.print("\n");
    }

    /**
     * Fill in a Matrix
     * <br>
     * This method is used to fill a matrix with numbers.<br>
     * A nested for loop iterates over all the slots of the matrix and asks the user to enter the value, that value is stored in the current slot.<br> 
     * 
     * @param mat A 2D matrix
     */
    private static void fillMat(double[][] mat)
    {
        Scanner matInputs = new Scanner(System.in);
        for(int i =0 ;i< mat.length;i++)
        {
            for(int j =0; j<mat[0].length;j++)
            {
                System.out.printf("Enter the %d - %d slot:",i,j);
                
                mat[i][j] = validateDoubleInput(matInputs);

            }
            
        }
        System.out.println();
        matInputs.reset();
    }

    /**
     * Check Int input
     * <br>
     * This method is used to check whether the input recieved from the user is a valid "int".<br>This is mostly used when creating a matrix, for rows and columns, and for selecting options.
     * <br>
     * If the user's last input is not an "int" the user is asked to re-enter a valid number.
     *  
     * @param inputB A scanner with the latest user input
     * @return an Int value
     */
    private static int validateIntInput(Scanner inputB)
    {
        boolean stop = false;
        int ans = 100;
        while(!stop)
        {
            if(inputB.hasNextInt())
            {
                ans = inputB.nextInt();
                stop =true;
            }
            else
            {             
                System.out.println("Enter a valid NUMBER.");
                inputB.next();
            }
        }
        inputB.reset();
        return ans;
    }

    /**
     * Check Double input
     * <br>
     * This method is used to check whether the input recieved from the user is a valid "double".<br>This is mostly used when creating a matrix for the values inside the matrix.
     * <br>
     * If the user's last input is not a "double" the user is asked to re-enter a valid number.
     *  
     * @param inputB A scanner with the latest user input
     * @return an double value
     */
    private static double validateDoubleInput(Scanner inputB)
    {
        boolean stop = false;
        double ans = 100;
        while(!stop)
        {
            if(inputB.hasNextDouble())
            {
                ans = inputB.nextDouble();
                stop =true;
            }
            else
            {             
                System.out.println("Enter a valid NUMBER.");
                inputB.next();
            }
        }
        inputB.reset();
        return ans;
    }

    /**
     * Matrix Multiplication
     * <br>
     * This method lets the user create 2 matrices, and fill them with whatever values they want. Then a new matrix is created from the product of multiplying the 2 matrices created earlier. 
     * 
     * First, the dimentions of the user created matrices are checked to make sure we can multiply them. (The rows of the first matrix should be equal to the columns of the second matrix)
     * <br>Then, the user is asked to fill in the matrices one by one. 
     * <br>Later, a triple nested for loop is used to get the value of each slot in the product matrix.
     * <br>Finally, the product matrix is printed.<br>
     * 
     */
    private static void multiplyMatrices()
    {
        System.out.println("Matrix 1: \n");
        double[][] mat1 = createMatrix();
        
        System.out.println("Matrix 2: \n");
        double[][] mat2 = createMatrix();

        if(mat1[0].length != mat2.length)
        {
            System.out.println("Matrix 1 Rows are not Equal to Matrix 2 Columns. Cannot Multiply Matrices");
        }
        else
        {
            System.out.println("Matrix 1: \n");
            fillMat(mat1);
            System.out.println("Matrix 2: \n");
            fillMat(mat2);
            double[][] ans = new double[mat1.length][mat2[0].length];
            for(int i=0;i<mat1.length;i++)
            {
                for(int j=0;j<mat2[0].length;j++)
                {
                    for(int m=0;m<mat2.length;m++)
                    {
                        if(Double.MAX_VALUE/Math.abs(mat2[m][j])<Math.abs(mat1[i][m]))
                        {
                            throw new ArithmeticException("Overflow: " + mat2[m][j] + " * " + mat1[i][m]);
                        }
                        else if(-(Double.MAX_VALUE)/Math.abs(mat2[m][j]) > Math.abs(mat1[i][m]))
                        {
                            throw new ArithmeticException("Underflow: " + mat2[m][j] + " * " + mat1[i][m]);
                        }
                        else
                        {
                            if(Double.MAX_VALUE-mat1[i][m] * mat2[m][j]<ans[i][j])
                            {
                                throw new ArithmeticException("Overflow: " + mat1[i][m] * mat2[m][j] + " + " + ans[i][j]);
                            }
                            else if(-Double.MAX_VALUE  > ans[i][j]+mat1[i][m] * mat2[m][j])
                            {
                                throw new ArithmeticException("Underflow: " + mat1[i][m] * mat2[m][j] + " + " + ans[i][j]);
                            }
                            else
                            {
                                ans[i][j]+=mat1[i][m] * mat2[m][j]; 
                            }

                        }

                        
                        
                    }
                }
            }
            printMat(ans);
        }
        
    }

    /**
     * Matrix Multiplication
     * <br>
     * This method multiplies 2 matrices given as parameters and returns the product matrix. 
     * 
     * First, the dimentions of the matrices are checked to make sure we can multiply them. (The rows of the first matrix should be equal to the columns of the second matrix)
     * <br>Then, the user is asked to fill in the matrices one by one. 
     * <br>Later, a triple nested for loop is used to get the value of each slot in the product matrix.
     * <br>Finally, the product matrix is printed.<br>
     * 
     * @param mat1 A 2D matrix
     * @param mat2 A 2D matrix
     * @return A 2D matrix of doubles
     */
    private static double[][] multiplyMatrices(double[][] mat1,double[][] mat2)
    {
        double[][] ans = new double[mat1.length][mat2[0].length];
        if(mat1[0].length != mat2.length)
        {
            System.out.println("Matrix 1 Rows are not Equal to Matrix 2 Columns. Cannot Multiplay Matrices");
        }
        else
        {
            
            for(int i=0;i<mat1.length;i++)
            {
                for(int j=0;j<mat2[0].length;j++)
                {
                    for(int m=0;m<mat2.length;m++)
                    {
                        ans[i][j]+=mat1[i][m] * mat2[m][j]; 
                    }
                }
            }
            
        }
        return ans;
    }

    /**
     * This method applies the gaussJordan elimination algorithm on a copy of a matrix "mat" to return its inverse.<br><br>
     * 
     * First, create a copy of mat (matCopy)<br>
     * Second, create an identity matrix of the same dimentions as "mat"<br>
     * Third, order the rows according to the highest pivot element. <br>This means that for each column find the highest absolute value element and make sure it is on the current pivot's row
     * <br>
     * Then, using forward elimination, make all the numbers below the matrix diagonal equal to zero using the 3 row operations, every operation performed on "mat" will be mirrored on our inverse matrix "ans". <br>(1. Swap two rows, 2. Subtract/Add 2 rows 3.Subtract/Add a multiple of a row to another) 
     * <br>
     * Later, make the pivots/ numbers on the diagonal equal to 1 using row operarions, every operation performed on "mat" will be mirrored on our inverse matrix "ans".<br>
     * Finally, make the numbers above the diagonal equal to 0 using row operations, every operation performed on "mat" will be mirrored on our inverse matrix "ans".<br>
     * <br>
     * The logic behind this is similar to basic division. Where X * 1/X = 1 <br>
     * 
     * In our example we have X as "mat" and 1/X is the inverse of "mat".
     * 
     * 
     * @param mat
     * @return Inverse of "mat"
     */
    private static double[][] gaussJordan(double[][] mat)
    {
        
        double[][] matCopy = new double[mat.length][mat.length];
        for(int i=0;i<matCopy.length;i++) //Deep copy mat to matCopy
        {
                for(int j = 0;j<matCopy.length;j++)
                {
                    matCopy[i][j]=mat[i][j];
                }
            }
        

        
        double[][] ans = new double[matCopy.length][matCopy.length];
        for(int j =0;j<ans.length;j++)//create identity matrix
        {
            ans[j][j]=1;
        }
        
        for(int i = 0; i<matCopy.length-1;i++)
        {
            int pivot = i;
            double pivotValue = matCopy[i][i];
            if(pivotValue<0)
                pivotValue*=-1;

            for(int j=i+1;j<matCopy.length;j++) //make sure the selected column pivot is the highest in the column
            {
                double temp = matCopy[j][i];
                if(temp<0)
                    temp*=-1;
                if(temp>pivotValue)
                {
                    pivot=j;
                    pivotValue=temp;
                }
            }

            if(pivotValue==0)
            {
                System.out.println("Inverse matrix doesn't exist");
                return ans;
            }

            
            

            if(pivot!=i)
            {   
                
                double temp;
                for(int j =0; j<matCopy.length;j++)
                {
                    temp = matCopy[i][j];
                    matCopy[i][j] = matCopy[pivot][j];
                    matCopy[pivot][j] = temp;

                    temp = ans[i][j];
                    ans[i][j] = ans[pivot][j];
                    ans[pivot][j] = temp;
                }
                
            }

            //We made the diagonal matrix YAY!
            //Now make all the numbers below the diag = 0

            for(int j=i+1;j<matCopy.length;j++) //Subtract constant*element of the row above from current row to make the numbers below the diagonal =0
            {
                double constant = matCopy[j][i]/matCopy[i][i];
                
                for(int k = 0;k<matCopy.length;k++)
                {
                    matCopy[j][k]-= matCopy[i][k]*constant;
                    ans[j][k] -= ans[i][k]*constant;
                }
            }
        }

        //Now make the diag numbers =1
        for (int i = 0; i < matCopy.length; i++) //Divide all the elements of each row by the row's pivot.
        {
            double constant = matCopy[i][i];
            for (int j = 0; j < matCopy.length; j++) 
            {
                matCopy[i][j]= matCopy[i][j]/constant;
                ans[i][j]= ans[i][j]/constant;
            }    
        }
        
        for (int i = 0; i < matCopy.length-1; i++) 
        {
            for (int j = i+1; j < matCopy.length; j++) 
            {
                double constant = matCopy[i][j];
                for (int k = 0; k < matCopy.length; k++) 
                {
                    matCopy[i][k] -=matCopy[j][k]*constant;
                    ans[i][k] -=ans[j][k]*constant;
                }    
            }
              
        }
        return ans;

    }

    /**
     * This method allows the user to create a 2D square matrix, fill it with numbers, the uses Gauss-Jordan Elimination method to get the inverse. <br><br>
     * 
     * First, the user creates a matrix.<br>
     * Next, the method checks if the matrix is square. if not, the user is returned to the previous sub-menu.<br>
     * Then, the user fills the matrix with doubles.<br>
     * Finally, the method calls "gaussJordan()" on the matrix the user created earlier and prints the inverse of that matrix. <br>
     */
    private static void matrixInverse()
    {
        System.out.println("Matrix: \n");
        double[][] mat = createMatrix();

        if(mat.length!=mat[0].length)
        {
            System.out.println("Matrix should be square.");
        }
        else
        {
            fillMat(mat);

            System.out.print("Original Matrix: \n");
            printMat(mat);
            
            double[][] ans = gaussJordan(mat);

            System.out.print("Inverse Matrix: \n");
            printMat(ans);
        }

        
    }

    /**
     * This method contains all the functionality of the Statistical Array task.<br><br> 
     * 
     * It calculates and displays statistical information when given an array of numbers, This information includes the median, 
     * arithmetic mean, geometric mean, and harmonic mean.<br>
     * 
     * <p>The user inputs the size and elements of the array, these are used to calculate and display the statistical measures.<br>
     *  In case of invalid input, appropriate error messages are shown.</p>
     */
    public static void displayStatisticalInformation() {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        String option;
        while(!exit)
        {
            System.out.println("STATISTICAL INFORMATION ABOUT AN ARRAY");
            System.out.println("1.Enter Array \n2.Return to main menu");
            Scanner optionScanner = new Scanner(System.in);
            option = optionScanner.nextLine(); 
            
            switch(option)
            {
                case "1"->
                {
                    Clear_Console();
                    
                    System.out.print("Please Enter the size of the array: ");
                    int size;
                    size= validateIntInput(input);
                    

                    while(size <= 0) 
                    {
                        System.out.println("Array size must be greater than zero.");
                        size= validateIntInput(input);
                    }

                    double[] array = new double[size];

                    System.out.println("Enter the elements of the array:");
                    for (int i = 0; i < size; i++) 
                    {
                        array[i] = validateDoubleInput(input);
                        
                    }

                    Arrays.sort(array);
                    System.out.printf("Median: %.2f \n",calculateMedian(array));
                    System.out.printf("Arithmetic Mean: %.2f \n",calculateArithmeticMean(array));
                    System.out.printf("Geometric Mean: %.2f \n\n" ,calculateGeometricMean(array));
                    double harmonicMean = calculateHarmonicMean(array, size);

                    if (harmonicMean == Double.POSITIVE_INFINITY) 
                    {
                        System.out.println("Harmonic Mean is undefined due to zero element.");
                    } 
                    else 
                    {
                        System.out.println("Harmonic Mean: " + harmonicMean);
                    }
                    break;
                }

                case "2"->
                {
                    Clear_Console();
                    return;
                }
                default-> 
                {
                    Clear_Console();
                    System.out.print("Enter a valid option\n\n");

                }
            }
        }

    }

    /**
     * Calculates the median of a sorted array.
     * 
     * First, the method stores the array's length.<br>
     * Then, it checks whether the length is divisivble by 2, if it is then it returns the average of the 2 elements in the middle 2 indicies of the array.
     * Otherwise, it just returns the single element in the middle.
     *
     * @param array : a sorted array of numbers
     * @return returns the median value of the array
     */
    public static double calculateMedian(double[] array) {
        int length = array.length;
        if (length % 2 == 0) {
            return (array[length / 2 - 1] + array[length / 2]) / 2.0;
        } else {
            return array[length / 2];
        }
    }

    /**
     * Calculates the arithmetic mean of an array.<br><br>
     * 
     * First, the method iterates over the whole array and keeps adding the value at the current index to a double variable "sum".<br>
     * Then, the "sum" is divided by the number of elements in the array, and the quotient is returned as a type double.<br><br>
     *
     * @param array an array of numbers
     * @return the arithmetic mean of the array
     */
    public static double calculateArithmeticMean(double[] array) {
        double sum = 0;
        for (double num : array) {
            if(Double.MAX_VALUE-num<sum)
            {
                throw new ArithmeticException("Overflow: " + num + " + " + sum);
            }
            else if(-Double.MAX_VALUE  > sum+num)
            {
                throw new ArithmeticException("Underflow: " + num + " + " + sum);
            }
            else
            {
                sum += num;
            }
            
        }
        return sum / array.length;
    }

    /**
     * Calculates the geometric mean of an array.
     *
     * First, the method iterates over the whole array and keeps multiplying the value at the current index to a double variable "product".
     * Then, the "product" to the power of (1/array's length)'s result is returned as a type double.
     * 
     * @param array : an array of numbers
     * @return returns the geometric mean of the array
     */
    public static double calculateGeometricMean(double[] array) {
        double product = 1;
        for (double num : array) 
        {
            if(Double.MAX_VALUE/Math.abs(num)<Math.abs(product))
            {
                throw new ArithmeticException("Overflow: " + num + " * " + product);
            }
            else if(-(Double.MAX_VALUE)/Math.abs(num) > Math.abs(product))
            {
                throw new ArithmeticException("Underflow: " + num + " * " + product);
            }
            else
            {
                product *= num;
            }
            
        }
        return Math.pow(product, 1.0 / array.length);
    }

    /**
    * Recursively calculates the harmonic mean of an array.<br><br> 
    * If a zero element is present, returns "Infinity" since the harmonic mean is undefined for arrays containing zero.<br>
    * Otherwise, the method keeps recursing until we reach the end of the array and returns the hamonic mean.<br>
    * 
    * @param array : an array of numbers
    * @param n is the size of the array
    * @return returns the harmonic mean of the array
    */

    public static double calculateHarmonicMean(double[] array, int n) {
        if (n <= 0) {
            return 0;
        }
        if (array[n - 1] == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return n / (1 / array[n - 1] + calculateHarmonicMean(array, n - 1));
    }

    /**
     * An array of 9 characters used as the game board.  
     */
    static char[] board = new char[9];
    /**
     * A char that determines which player's turn it currently is.
     */
    private static char currentPlayer = 'X';
    /**
     * This method contains all the functionality of the Tic-Tac-Toe Game task.
     * <br>
     * The user is greeted with a Tic-Tac-Toe board filled with numbers from 1 to 9 for the user to select from.
     * <br>
     * Once the user picks a valid number, the corresponding slot will be filled with the current player's symbol. (X or O) Then the other player's turn start.
     * <br> 
     * The game keeps going on until one of the players win or when the whole board is full.
     * <br>
     * This method will keep running until the user enters the string "terminate".
     */
    public static void TicTacToeGame() {
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
     * This method contains all the funcitonality of the Encryption-Decryption task.<br><br>
     * 
     * It displays the main menu for the "Encryption - Decryption" part. <br>
     * The user is given 3 options :<br> 
     * [1]Encryption<br>
     * [2]Decryption <br>
     * [3]Return to the Main Menu<br>
     * Then when the user enters a valid input, it would take them to the menu of their selected option.<br><br>
     * 
     * [1]Encryption:<br>
     * Calls the function "Encrypt()"<br><br>
     * 
     * [2]Decryption:<br>
     * Calls the function "Decrypt()"<br><br>
     * 
     * [3]Return to the Main Menu<br>
     * Returns from the function to the application's main menu <br><br>
     * 
     * Unless the user enters option [3], this option will keep running in a while loop which is stopped using a boolean "exit" which is set to true only when the user pick [3]<br> 
     * 
     */
    public  static void C_submenu()
    {
        Scanner inputC  = new Scanner(System.in);
        boolean exit = false;

        while(!exit)
        {
            System.out.println("Please Select One of The Following Options From The Submenu: ");
            System.out.println("[1] Encryption");
            System.out.println("[2] Decryption");
            System.out.println("[3] Return to the Main Menu\n");
            
            switch (inputC.nextInt()) 
            {

                case 1 -> {
                    Clear_Console();
                    System.out.println("You have selected option 1\n");
                    System.out.println("Please Enter The Encryption Key to Use (Must be between 26 And -26),\nPostive Values Shift Characters to The Right, Negative Values Shift Characters to The Left, 0 Will Not Shift Characters: ");     
                    
                    int key = inputC.nextInt();
                    inputC.nextLine(); 
                    
                    if (key > 26 || key < -26){
                        System.out.println("\nINVALID input!!!, The Encryption Key Must Be Be Between 26 And -26\nReturning To the subMenu...\n ");
                        break;
    
                    }
                    
                    System.out.println("\nPlease Enter the Text You Would Like to Encrypt: ");     
                    String text = inputC.nextLine();
    
                    String Encrypted_Text = Encrypt(text, key);
                    System.out.println("\n"+ text + " Was Successfully Encrypted To " + Encrypted_Text);
                }
               
                case 2 -> {
                    Clear_Console();
                    System.out.println("You have selected option 2\n");
                    System.out.println("Please Enter The Decryption Key to Use (Must be between 26 And -26)\nPostive Values Shift Characters The Left, Negative Values Shift Characters to The Right, 0 Will Not Shift Characters: ");
                    int key = inputC.nextInt();
                    inputC.nextLine();
                    if (key > 26 || key < -26){
                        System.out.println("\nINVALID input!!!, The Decryption Key Must Be Be Between 26 And -26\nReturning To the Submenu...\n ");
                        break;
                    }
                    System.out.println("\nPlease Enter the Text You Would Like to Decrypt: ");
                    String text = inputC.nextLine();
                    String Decrypted_Text = Decrypt(text, key);
                    System.out.println("\n"+ text + " Was Successfully Dencrypted To " + Decrypted_Text);
                }
    
                case 3 -> {
                    Clear_Console();  
                    exit=true;
                }
                 
                default -> System.out.println("INVALID input!!!, No Such Option Exists, Please Select A valid option From The Submenu (1, 2, 3)\n");
            }   
        }

    }

    /**
     * 
     * This function encrypts a text entered by the user.<br><br>
     * 
     * The user is asked to select a number between -26 and +26 as the Encryption Key.<br>
     * Then is asked to input a string "text" to be encrypted.<br>
     * Then the string's characters are shifted upwards (positively) according to the key from before.<br><br>
     * 
     * @param text A string that will be encrypted
     * @param key An int value that determines how many letters each character in "text" is going to be shifted
     * @return EncryptedString
     */
    private static String Encrypt(String text, int key){

        String ans = "";      
        for (int i = 0; i < text.length(); i++) {
            
            if(Character.isAlphabetic(text.charAt(i)))
            {
                char toAdd = 'a';
                
                if(text.charAt(i)<'a')
                {
                    toAdd = 'A';
                }
                
                char c = (char)(text.charAt(i)-toAdd);

                char c_Encrypted = (char)(((c+key)+26)%26);

                c_Encrypted = (char) (c_Encrypted%26 + toAdd);
                
                ans += c_Encrypted;
            }
            else if(!Character.isAlphabetic(text.charAt(i)))
            {
                ans += text.charAt(i);
            }
        }

        return ans;
    }

    /**
     * 
     * This function decrypts a text entered by the user.<br><br>
     * 
     * The user is asked to select a number between -26 and +26 as the Encryption Key.<br>
     * Then is asked to input a string "text" to be encrypted.<br>
     * Then the string's characters are shifted downwards (negatively) according to the key from before.<br>
     * 
     * @param text A string that will be encrypted
     * @param key An int value that determines how many letters each character in "text" is going to be shifted
     * @return DecryptedString
     */
    private static String Decrypt(String text, int key){
        
        String ans = "";     
        for (int i = 0; i < text.length(); i++) {
            
            if(Character.isAlphabetic(text.charAt(i)))
            {
                char toSubtract = 'a';
                
                if(text.charAt(i)<'a')
                {
                    toSubtract = 'A';
                }

                char c = (char)(text.charAt(i)-toSubtract);

                char c_Decrypted = (char)(((c-key)+26)%26);

                c_Decrypted = (char) (c_Decrypted + toSubtract);

                ans += c_Decrypted;
            }
            else if(!Character.isAlphabetic(text.charAt(i)))
            {
                ans += text.charAt(i);
            }
        }

        return ans;
    }


}
