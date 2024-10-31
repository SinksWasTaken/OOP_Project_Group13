import java.util.Scanner;


public class MatrixOperations
{
    /**
     *  This method contains all the functionality of this class.
     *  <br>
     *  The user is greeted with a menu composed of a bunch of matrix operations that the user cna pick from.<br>Each option is assigned a number from 1 to 4. Option 5 will return the user to the main menu. 
     *  <br>
     *  Once the user picks a valid option, another method corresponding to their pick, will be called.
     *  <br> 
     *  This method will keep running until the user selects option 5.
     */
    public static void main(String[] args) 
    {
        Scanner userInput = new Scanner(System.in);
        boolean exit = false;
        clr();

        System.out.print("Matrix Operations \n\n");
        while(!exit)
        {
            System.out.print("[1] Transpose of a Matrix\n[2] Multiply 2 Matrices Element-wise\n[3] Multiply 2 Matrices\n[4] Inverse of a Matrix\n[5] Main Menu\n\n");
            
            switch (validateIntInput(userInput)) {
                case 1 -> {
                    clr();    
                    System.out.println("Transpose of a Matrix");
                    transpose();
                }
                case 2 -> {
                    clr();
                    System.out.println("Multiply 2 Matrices Element-wise");
                    multiplyMatrixElements();
                }
                case 3 -> {
                    clr();
                    System.out.println("Multiply 2 Matrices");
                    multiplyMatrices();
                }
                case 4 -> {
                    clr();
                    System.out.println("Inverse of a Matrix");
                    matrixInverse();
                }
                case 5 -> exit=true;
                default -> {
                    clr();
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
                System.out.println("Columns and rows are not equal. Cannot add matrices");
            } 
            else
            {
                System.out.println("Rows are not equal. Cannot add matrices");
            }
            
        }
        else if(mat1[0].length!=mat2[0].length)
        {
            System.out.println("Columns are not equal. Cannot add matrices");
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
                    ans[i][j] = mat1[i][j] * mat2[i][j];
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
            System.out.println("Matrix 1 Rows are not Equal to Matrix 2 Columns. Cannot Multiplay Matrices");
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
                        ans[i][j]+=mat1[i][m] * mat2[m][j]; 
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
        System.out.print("Main Matrix: \n");
        printMat(mat);
        System.out.print("Copy Matrix: \n");
        printMat(matCopy);
        System.out.print("Product Matrix: \n");
        printMat(ans);

        System.out.print("Checking Matrix: \n");
        printMat(multiplyMatrices(mat,ans));

        return ans;

    }

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
            
            double[][] ans = gaussJordan(mat);

            System.out.print("Inverse Matrix: \n");
            printMat(ans);
        }

        
    }

    /**
     * This method clears the terminal
     * 
     * It uses a sequence of escape codes.<br>"\033" marks the start of the ANSI sequence.<br>"[H" moves the cursor to the top-left.<br>"[2J" clears everything after the cursor.<br>
     */
    private static void clr()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

}
