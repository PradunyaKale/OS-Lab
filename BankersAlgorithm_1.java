import java.util.Scanner;

public class BankersAlgorithm {

    // Number of processes and resources
    private int numberOfProcesses, numberOfResources;
    private int[] available;
    private int[][] max;
    private int[][] allocation;
    private int[][] need;
    private int[] safeSequence;

    // Constructor to initialize matrices
    public BankersAlgorithm(int numberOfProcesses, int numberOfResources) {
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfResources = numberOfResources;
        available = new int[numberOfResources];
        max = new int[numberOfProcesses][numberOfResources];
        allocation = new int[numberOfProcesses][numberOfResources];
        need = new int[numberOfProcesses][numberOfResources];
        safeSequence = new int[numberOfProcesses];
    }

    // Function to input the data
    public void input() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Available Resources for each type:");
        for (int i = 0; i < numberOfResources; i++) {
            available[i] = sc.nextInt();
        }

        System.out.println("Enter the Maximum Matrix:");
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter the Allocation Matrix:");
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }

        // Calculate Need matrix
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    // Function to check if the system is in a safe state
    public boolean isSafe() {
        boolean[] finish = new boolean[numberOfProcesses];
        int[] work = available.clone();
        int count = 0;

        while (count < numberOfProcesses) {
            boolean found = false;
            for (int i = 0; i < numberOfProcesses; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < numberOfResources; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == numberOfResources) {
                        // Allocate the process and mark it finished
                        for (int k = 0; k < numberOfResources; k++) {
                            work[k] += allocation[i][k];
                        }
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("The system is in an unsafe state.");
                return false;
            }
        }
        System.out.println("The system is in a safe state.");
        printSafeSequence();
        return true;
    }

    // Function to print the safe sequence
    public void printSafeSequence() {
        System.out.println("Safe Sequence:");
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.print("P" + safeSequence[i]);
            if (i != numberOfProcesses - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int numberOfProcesses = sc.nextInt();

        System.out.print("Enter number of resource types: ");
        int numberOfResources = sc.nextInt();

        BankersAlgorithm bankersAlgorithm = new BankersAlgorithm(numberOfProcesses, numberOfResources);
        bankersAlgorithm.input();

        if (bankersAlgorithm.isSafe()) {
            System.out.println("System is in a safe state.");
        } else {
            System.out.println("System is not in a safe state.");
        }
    }
}
