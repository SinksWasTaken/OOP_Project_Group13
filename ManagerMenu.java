import java.util.*;

public class ManagerMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size;
        while (true) {
            System.out.println("Enter dataset size (1000 - 10000): ");
            size = scanner.nextInt();
            if (size >= 1000 && size <= 10000) {
                break;
            }
            System.out.println("Invalid size. Please try again.");
        }

        int[] dataset = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            dataset[i] = random.nextInt(20001) - 10000;
        }

        System.out.println("Executing Radix Sort...");
        long start = System.nanoTime();
        int[] radixSorted = dataset.clone();
        radixSort(radixSorted);
        System.out.println("Radix Sort execution time: " + (System.nanoTime() - start) / 1_000_000 + " ms");
        validateSorting("Radix Sort", dataset, radixSorted);

        System.out.println("Executing Shell Sort...");
        start = System.nanoTime();
        int[] shellSorted = dataset.clone();
        shellSort(shellSorted);
        System.out.println("Shell Sort execution time: " + (System.nanoTime() - start) / 1_000_000 + " ms");
        validateSorting("Shell Sort", dataset, shellSorted);

        System.out.println("Executing Heap Sort...");
        start = System.nanoTime();
        int[] heapSorted = dataset.clone();
        heapSort(heapSorted);
        System.out.println("Heap Sort execution time: " + (System.nanoTime() - start) / 1_000_000 + " ms");
        validateSorting("Heap Sort", dataset, heapSorted);

        System.out.println("Executing Insertion Sort...");
        start = System.nanoTime();
        int[] insertionSorted = dataset.clone();
        insertionSort(insertionSorted);
        System.out.println("Insertion Sort execution time: " + (System.nanoTime() - start) / 1_000_000 + " ms");
        validateSorting("Insertion Sort", dataset, insertionSorted);
    }

    public static void radixSort(int[] data) {
        // Separate negative and positive numbers
        int[] negatives = new int[data.length];
        int[] positives = new int[data.length];
        int negCount = 0, posCount = 0;

        for (int num : data) {
            if (num < 0) {
                negatives[negCount++] = -num; // Convert negatives to positive for sorting
            } else {
                positives[posCount++] = num;
            }
        }

        // Sort positives and negatives separately
        radixSortGroup(positives, posCount);
        radixSortGroup(negatives, negCount);

        // Combine negatives and positives back into the original array
        int index = 0;
        for (int i = negCount - 1; i >= 0; i--) { 
            data[index++] = -negatives[i];
        }
        for (int i = 0; i < posCount; i++) { 
            data[index++] = positives[i];
        }
    }

    // Radix Sort logic for sorting non-negative numbers
    public static void radixSortGroup(int[] data, int size) {
        if (size == 0) return;

        // Find the largest number to figure out how many digit places we need to process
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (data[i] > max) max = data[i];
        }

        // Sort the array by each digit place 
        int currentPlace = 1;
        while (max / currentPlace > 0) {
            int[] count = new int[10]; // Keeps track of digit frequency
            int[] sorted = new int[size];

            // Count occurrences of each digit at the current place
            for (int i = 0; i < size; i++) {
                int digit = (data[i] / currentPlace) % 10;
                count[digit]++;
            }

            // Adjust count to represent digit positions in the sorted array
            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }

            // Sort numbers based on the current digit
            for (int i = size - 1; i >= 0; i--) {
                int digit = (data[i] / currentPlace) % 10;
                sorted[count[digit] - 1] = data[i];
                count[digit]--;
            }

            // Copy the sorted numbers back to the original array
            for (int i = 0; i < size; i++) {
                data[i] = sorted[i];
            }

            currentPlace *= 10; // Move to the next digit place 
        }
    }

    public static void shellSort(int[] data) {
        for (int gap = data.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < data.length; i++) {
                int temp = data[i];
                int j = i;
                while (j >= gap && data[j - gap] > temp) {
                    data[j] = data[j - gap];
                    j -= gap;
                }
                data[j] = temp;
            }
        }
    }

    public static void heapSort(int[] data) {
        int size = data.length;

        // max heap 
        for (int i = size / 2 - 1; i >= 0; i--) {
            adjustHeap(data, size, i);
        }

        // Extract elements from the heap
        for (int i = size - 1; i > 0; i--) {
            int temp = data[0];
            data[0] = data[i];
            data[i] = temp;

            adjustHeap(data, i, 0);
        }
    }

    // Adjust the heap to maintain the max-heap property
    private static void adjustHeap(int[] data, int size, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size && data[left] > data[largest]) {
            largest = left;
        }

        if (right < size && data[right] > data[largest]) {
            largest = right;
        }

        if (largest != root) {
            int temp = data[root];
            data[root] = data[largest];
            data[largest] = temp;

            adjustHeap(data, size, largest);
        }
    }

    public static void insertionSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int key = data[i];
            int j = i - 1;

            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = key;
        }
    }

    // Validation method to compare the sorted array with Java's Arrays.sort()
    public static void validateSorting(String algorithmName, int[] original, int[] sorted) {
        int[] expected = original.clone();
        Arrays.sort(expected);
        if (Arrays.equals(expected, sorted)) {
            System.out.println(algorithmName + " validation with Arrays.sort(): Successful");
        } else {
            System.out.println(algorithmName + " validation with Arrays.sort(): Failed");
        }
    }
}


