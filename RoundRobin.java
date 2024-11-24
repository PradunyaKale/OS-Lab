import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int[] bt = new int[n]; // Burst Time
        int[] art = new int[n]; // Arrival Time
        int[] wt = new int[n]; // Waiting Time
        int[] tat = new int[n]; // Turnaround Time
        int[] ct = new int[n]; // Completion Time
        int[] rem_bt = new int[n]; // Remaining Burst Time

        // Input Burst Time
        System.out.println("Enter the burst time of the processes:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " = ");
            bt[i] = sc.nextInt();
            rem_bt[i] = bt[i];
        }

        // Input Arrival Time
        System.out.println("Enter the arrival time of the processes:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " = ");
            art[i] = sc.nextInt();
        }

        System.out.print("Enter the quantum time: ");
        int qt = sc.nextInt();

        // Round Robin Scheduling
        Queue<Integer> queue = new LinkedList<>();
        int t = 0, completed = 0;

        boolean[] visited = new boolean[n];
        queue.add(0);
        visited[0] = true;

        while (completed != n) {
            int index = queue.poll();

            if (rem_bt[index] > qt) {
                t += qt;
                rem_bt[index] -= qt;
            } else {
                t += rem_bt[index];
                rem_bt[index] = 0;
                ct[index] = t;
                tat[index] = ct[index] - art[index];
                wt[index] = tat[index] - bt[index];
                completed++;
            }

            // Add newly arrived processes to the queue
            for (int i = 0; i < n; i++) {
                if (!visited[i] && art[i] <= t && rem_bt[i] > 0) {
                    queue.add(i);
                    visited[i] = true;
                }
            }

            // Re-add the current process if it's not finished
            if (rem_bt[index] > 0) {
                queue.add(index);
            }

            // If the queue is empty but processes are still remaining, advance time
            if (queue.isEmpty() && completed != n) {
                for (int i = 0; i < n; i++) {
                    if (rem_bt[i] > 0) {
                        queue.add(i);
                        visited[i] = true;
                        break;
                    }
                }
            }
        }

        // Calculate Average Times
        float total_wt = 0, total_tat = 0;
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
        }
        float avg_wt = total_wt / n;
        float avg_tat = total_tat / n;

        // Output Results
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Process\tBurst Time\tArrival Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        System.out.println("--------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + bt[i] + "\t\t" + art[i] + "\t\t" + ct[i] + "\t\t" + tat[i] + "\t\t" + wt[i]);
        }
        System.out.println("\nAverage waiting time = " + avg_wt);
        System.out.println("Average turnaround time = " + avg_tat);

        sc.close();
    }
}
