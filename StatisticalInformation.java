import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;   

public class StatisticalInformation {

    public static void main(String[] args) {
        displayStatisticalInformation();
    }

    public static void displayStatisticalInformation() {
        Scanner input = new Scanner(System.in);
        
        try {
            System.out.println("STATISTICAL INFORMATION ABOUT AN ARRAY");
            System.out.print("Please Enter the size of the array: ");
            int size = input.nextInt();

            if (size <= 0) {
                System.out.println("Array size must be greater than zero.");
                return;
            }

            double[] array = new double[size];
            System.out.println("Enter the elements of the array:");
            for (int i = 0; i < size; i++) {
                array[i] = input.nextDouble();
            }

            Arrays.sort(array);
            System.out.println("Median: " + calculateMedian(array));
            System.out.println("Arithmetic Mean: " + calculateArithmeticMean(array));
            System.out.println("Geometric Mean: " + calculateGeometricMean(array));
            double harmonicMean = calculateHarmonicMean(array, size);
            if (harmonicMean == Double.POSITIVE_INFINITY) {
                System.out.println("Harmonic Mean is undefined due to zero element.");
            } else {
                System.out.println("Harmonic Mean: " + harmonicMean);
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numeric values.");
        } finally {
            input.close();
        }
    }

    public static double calculateMedian(double[] array) {
        int length = array.length;
        if (length % 2 == 0) {
            return (array[length / 2 - 1] + array[length / 2]) / 2.0;
        } else {
            return array[length / 2];
        }
    }

    public static double calculateArithmeticMean(double[] array) {
        double sum = 0;
        for (double num : array) {
            sum += num;
        }
        return sum / array.length;
    }

    public static double calculateGeometricMean(double[] array) {
        double product = 1;
        for (double num : array) {
            product *= num;
        }
        return Math.pow(product, 1.0 / array.length);
    }

    public static double calculateHarmonicMean(double[] array, int n) {
        if (n <= 0) {
            return 0;
        }
        if (array[n - 1] == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return n / (1 / array[n - 1] + calculateHarmonicMean(array, n - 1));
    }
}
