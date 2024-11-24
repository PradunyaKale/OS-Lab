import java.util.*;

public class PriorityNonPreem {

    static void findWaitingTime(Process[] proc, int n, int[] wt) {
        int[] service_time = new int[n];
        service_time[0] = proc[0].at;
        wt[0] = 0;

        for (int i = 1; i < n; i++) {
            service_time[i] = service_time[i - 1] + proc[i - 1].bt;
            wt[i] = service_time[i] - proc[i].at;
            if (wt[i] < 0) {
                wt[i] = 0;
            }
        }
    }

    static void findTurnAroundTime(Process[] proc, int n, int[] wt, int[] tat) {
        for (int i = 0; i < n; i++) {
            tat[i] = proc[i].bt + wt[i];
        }
    }

    static void findavgTime(Process[] proc, int n) {
        int[] wt = new int[n], tat = new int[n];
        int total_wt = 0, total_tat = 0;

        findWaitingTime(proc, n, wt);
        findTurnAroundTime(proc, n, wt, tat);

        System.out.println("Processes " + " Arrival time " + " Burst time " + " Waiting time " + " Turn around time");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + proc[i].pid + "\t\t" + proc[i].at + "\t\t" + proc[i].bt + "\t\t " + wt[i] + "\t\t " + tat[i]);
        }

        System.out.println("Average waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int numProcesses = sc.nextInt();

        Process[] proc = new Process[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            String pid = "P" + (i + 1);
            System.out.println("Enter arrival time for " + pid + ":");
            int at = sc.nextInt();
            System.out.println("Enter burst time for " + pid + ":");
            int bt = sc.nextInt();
            System.out.println("Enter priority for " + pid + ":");
            int priority = sc.nextInt();

            proc[i] = new Process(pid, bt, at, priority);
        }

        // Sort processes based on arrival time and then priority
        Arrays.sort(proc, (a, b) -> {
            if (a.at == b.at) {
                return a.priority - b.priority;
            } else {
                return a.at - b.at;
            }
        });

        findavgTime(proc, numProcesses);

        sc.close();
    }
}
