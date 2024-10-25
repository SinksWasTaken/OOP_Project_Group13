import java.util.Scanner;


public class MatrixOperations
{
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
                case 1:
                    clr();    
                    System.out.println("Transpose of a Matrix");
                    transpose();
                    
                    
                    break;
                case 2:
                    clr();
                    System.out.println("Multiply 2 Matrices Element-wise");
                    multiplyMatrixElements();
                    
                    break;
                case 3:
                    clr();
                    System.out.println("Multiply 2 Matrices");
                    multiplyMatrices();

                    break;
                case 4:
                    clr();
                    System.out.println("Inverse of a Matrix");
                    matrixInverse();

                    break;
                case 5:
                    exit=true;
                    break;
                default:
                    clr();
                    System.out.print("Choose a number between 1 and 5\n\n");
                    
            }
        }

    }

    static double[][] createMatrix()
    {
        Scanner input =  new Scanner(System.in);
        System.out.println("Enter Rows: ");
        int rows = validateIntInput(input);
        System.out.println("Enter Columns: ");
        int cols = validateIntInput(input);

        double[][] mat = new double[rows][cols];
        

        input.reset();
        return mat;
    }

    static void transpose()
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

    static void multiplyMatrixElements()
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

    static void printMat(double[][] mat)
    {
        for (double[] mat1 : mat) {
            for (int j = 0; j<mat[0].length; j++) {
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

    static void fillMat(double[][] mat)
    {
        Scanner matInputs = new Scanner(System.in);
        for(int i =0 ;i< mat.length;i++)
        {
            for(int j =0; j<mat[0].length;j++)
            {
                System.out.printf("Enter the %d - %d slot:",i,j);
                
                mat[i][j] = validateIntInput(matInputs);

            }
            
        }
        System.out.println();
        matInputs.reset();
    }

    static int validateIntInput(Scanner input)
    {
        boolean stop = false;
        int ans = 100;
        while(!stop)
        {
            if(input.hasNextInt())
            {
                ans = input.nextInt();
                stop =true;
            }
            else
            {             
                System.out.println("Enter a valid NUMBER.");
               input.next();
                
                
            }
        }
        input.reset();
        return ans;
    }

    static void multiplyMatrices()
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

    static double matrixDeteminant(double[][] mat)
    {
        if(mat.length==2)
        {
            return (mat[0][0]*mat[1][1]) - (mat[1][0]*mat[0][1]);
        }
        else if(mat.length==3)
        {
            return (mat[0][0]*mat[1][1]*mat[2][2]) + (mat[0][1]*mat[1][2]*mat[2][0])+ (mat[0][2]*mat[1][0]*mat[2][1]) - (mat[0][0]*mat[1][2]*mat[2][1]) - (mat[0][1]*mat[1][0]*mat[2][2]) - (mat[0][2]*mat[1][1]*mat[2][0]);
        }

        return mat[0][0];
    }

    static double[][] ignoreIndicies(double[][] mat,int row,int col)
    {
        double[][] ans = new double[mat.length-1][mat[0].length-1];

        int counterRows=0,counterCols=0;

        for(int i=0; i< mat.length;i++)
        {
            if(i==row)
            {
                continue;
            }
            for(int j=0; j< mat[0].length;j++)
            {
                if(j==col)
                {
                    continue;
                }
                
                ans[counterRows][counterCols]=mat[i][j];
                counterCols++;
                
            }
            counterCols=0;
            counterRows++;
        }
        return ans;
    }
    
    static void matrixInverse()
    {
        System.out.println("Matrix: \n");
        double[][] mat = createMatrix();
        fillMat(mat);

        printMat(mat);
        

        if(mat.length!=mat[0].length)
        {
            System.out.println("Matrix should be square.");
        }
        else if(mat.length>3)
        {
            System.out.println("Matrix is too big.");
        }
        else
        {
            if(mat.length==1)
            {
                printMat(mat);
            }
            else if(mat.length==2)
            {
                double det = matrixDeteminant(mat);
                if(det!=0)
                {
                    double constantNumber = 1/matrixDeteminant(mat);
                    double[][] ans = new double[mat.length][mat.length];

                    ans[0][0]=mat[1][1]     *constantNumber;
                    ans[0][1]=mat[0][1] *-1 *constantNumber;
                    ans[1][0]=mat[1][0] *-1 *constantNumber;
                    ans[1][1]=mat[0][0]     *constantNumber;

                    printMat(ans);
                }
                else
                {
                    System.out.println("Inverse matrix doesn't exist");
                }
                
            }
            else
            {
                double det = matrixDeteminant(mat);

                if(det!=0)
                {
                    double constantNumber = 1/matrixDeteminant(mat);
                    double[][] ans = new double[mat.length][mat.length];
                    
                    for (int i = 0; i < mat.length; i++)
                    {
                        for(int j = 0; j < mat.length; j++) 
                        {
                            ans[i][j]=matrixDeteminant(ignoreIndicies(mat, j, i))*constantNumber;
                            if(i==1 ^ j==1)
                            {
                                ans[i][j]*=-1;
                            }
                        }    
                    }

                    printMat(ans);
                }
                else
                {
                    System.out.println("Inverse matrix doesn't exist");
                }

                
            }
        }
    }

    static void clr()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

}
