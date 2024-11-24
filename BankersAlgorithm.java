import java.util.Scanner;

public class BankersAlgorithm {
    private int numProcesses;
    private int numResources;

    private int[][] allocation;
    private int[][] max;
    private int[][] need;
    private int[] available;

    public BankersAlgorithm(int processes, int resources) {
        numProcesses = processes;
        numResources = resources;
        allocation = new int[processes][resources];
        max = new int[processes][resources];
        need = new int[processes][resources];
        available = new int[resources];
    }

    public void inputMatrices() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Allocation matrix: ");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter Max matrix: ");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter Available resources: ");
        for (int i = 0; i < numResources; i++) {
            available[i] = scanner.nextInt();
        }

        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        
        scanner.close();
    }

    public boolean checkSafeState() {
        boolean[] finished = new boolean[numProcesses];
        int[] work = new int[numResources];
        System.arraycopy(available, 0, work, 0, numResources);

        StringBuilder safeSequence = new StringBuilder();
        int count = 0;

        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finished[i]) {
                    boolean canAllocate = true;
                    for (int j = 0; j < numResources; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int j = 0; j < numResources; j++) {
                            work[j] += allocation[i][j];
                        }
                        safeSequence.append(i).append(" ");
                        finished[i] = true;
                        found = true;
                        count++;
                    }
                }
            }

            if (!found) {
                System.out.println("System is not in a safe state.");
                return false;
            }
        }

        System.out.println("System is in a safe state. Safe sequence is: " + safeSequence);
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        int processes = scanner.nextInt();

        System.out.println("Enter the number of resources: ");
        int resources = scanner.nextInt();

        BankersAlgorithm bankers = new BankersAlgorithm(processes, resources);
        bankers.inputMatrices();

        bankers.checkSafeState();

        
        scanner.close();
    }
}
