import java.util.InputMismatchException;
import java.util.Scanner;
public class MatrixInverseRetry {
    //print the matrix
    public static void printMatrix(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8.3f", matrix[i][j]);
            }
            System.out.println(" ");
        }
    }

    //function to calculate inverse of a matrix
    public static boolean inverseMatrix(double [][]A , double[][]inverse) {
        int n = A.length;
        double[][] augmented = new double[n][2 * n];
        //Augment the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = A[i][j];
            }
            for (int j = n; j < 2 + n; j++) {
                augmented[i][j] = (i == (j - n)) ? 1 : 0;
            }
        }
        //gauss jordan elimination
        for(int i=0;i<n;i++){
            int maxRow=i;
            for(int k= i+1;k<n;k++ ){
                if(Math.abs(augmented[k][i]) > Math.abs(augmented[maxRow][i]) ){
                    maxRow = k;
                }
            }
            //if pivot element is zero,matrix is singular
            if (Math.abs(augmented[maxRow][i]) < 1e-10){
                System.out.println("matrix is singular,inverse cannot be found");
                return false;
            }
            //swap rows if needed
            if (i !=maxRow){
                double[] temp= augmented[i];
                    augmented[i]=augmented[maxRow];
                    augmented[maxRow]=temp;
            }
            //normalise the pivot row
            double pivot= augmented[i][i];
            for(int j=0;j<2*n;j++){
                augmented[i][j] /= pivot;
            }
            //eliminate other rows
            for (int k=0;k<n;k++){
                if (k!=i){
                    double factor= augmented[k][i];
                    for(int j = 0;j<2*n;j++){
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }
        for (int i= 0 ;i<n ; i++){
            for(int j=0;j<n;j++){
                inverse[i][j] = augmented[i][j+n];
            }
        }
        return true;
    }
    //retry option
    public static boolean askRetry(Scanner sc){
        boolean validChoice=  false;
        boolean retry=  false;
         while (!validChoice){
             System.out.println("do you want to try another matrix(Y/N)");
             String choice = sc.next();
             if(choice.equalsIgnoreCase("Y")){
                 validChoice = true;
                 retry = true;
                 System.out.println("\n /\\ /\\ /\\ /\\ /\\ /\\ /\\ \n");
             } else if (choice.equalsIgnoreCase("N")) {
                 validChoice=true;
                 retry=false;
                 System.out.println("thanks for using matrix inverse calculator /\\");
             }else{
                 System.out.println("invalid choice, please enter \"Y\" or \"N\" ");
             }
         }return retry;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        boolean contProgram = true;
        System.out.println("---MatrixInverseCalculator---\n");
        while(contProgram){
            int n=0;
            boolean validInput= false;
            while(!validInput){
                try{
                    System.out.println("enter the size of the square matrix (n): ");
                    n = sc.nextInt();
                    if(n<=0) {
                        System.out.println("invalid matrix size: ");
                    }
                    else{
                            validInput = true;
                        }
                    }
                catch(InputMismatchException e){
                    System.out.println("invalid input");
                    sc.next();
                }
            }
            double[][] matrix = new double [n][n];
            //get matrix elements
            System.out.println("enter the element of the matrix(row wise):");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++){
                    boolean validElement = false;
                    while(!validElement){
                        try{
                            System.out.printf("element [%d][%d]",i+1,j+1);
                            matrix[i][j]=sc.nextDouble();
                            validElement = true;
                        }
                        catch(InputMismatchException e){
                            System.out.println("invalid input");
                            sc.next();
                        }
                    }
                }
            }
            double[][] inverse = new double[n][n];
            //show original matrix
            System.out.println("\noriginal matrix");
            printMatrix(matrix);
            //find inverse
            boolean hasInverse = inverseMatrix(matrix,inverse);
            if(hasInverse){
                System.out.println("\n inverse of the matrix is:");
                printMatrix(inverse);
            }
            else{
                System.out.println("\n matrix  is singular, does not have an inverse");
            }
            //retry option
            contProgram = askRetry(sc);
        }
        sc.close();
    }
}