import java.util.Scanner;

class Process {
    int pid; // Process ID
    int bt;  // Burst Time
    int art; // Arrival Time

    public Process(int pid, int bt, int art) {
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }
}

public class sjfnonpreem {
    static void findWaitingTime(Process proc[], int n, int wt[], int ct[]) {
        int[] rt = new int[n]; // Remaining Time

        // Initialize remaining times
        for (int i = 0; i < n; i++)
            rt[i] = proc[i].bt;

        int complete = 0, t = 0; // Complete tracks the number of completed processes
        int minm = Integer.MAX_VALUE;
        int shortest = 0;
        boolean check = false;

        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((proc[j].art <= t) && (rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                t++;
                continue;
            }

            // Process the selected process
            t += rt[shortest];
            rt[shortest] = 0;
            minm = Integer.MAX_VALUE;

            if (rt[shortest] == 0) {
                complete++;
                check = false;

                // Calculate completion time
                ct[shortest] = t;

                // Calculate waiting time
                wt[shortest] = ct[shortest] - proc[shortest].bt - proc[shortest].art;

                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }
        }
    }

    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[], int ct[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = ct[i] - proc[i].art; // TAT = CT - AT
        }
    }

    static void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n], ct[] = new int[n];
        int total_wt = 0, total_tat = 0;

        // Calculate waiting time and completion time
        findWaitingTime(proc, n, wt, ct);

        // Calculate turnaround time
        findTurnAroundTime(proc, n, wt, tat, ct);

        System.out.println("Processes " + " Burst time " + " Arrival time " + " Completion time " + " Waiting time "
                + " Turn around time");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + proc[i].pid + "\t\t" + proc[i].bt + "\t\t " + proc[i].art + "\t\t " + ct[i]
                    + "\t\t " + wt[i] + "\t\t " + tat[i]);
        }

        System.out.println("\nAverage waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int numProcesses = sc.nextInt();

        Process[] proc = new Process[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter process " + (i + 1) + " details:");
            System.out.print("Process ID: ");
            int pid = sc.nextInt();
            System.out.print("Burst Time: ");
            int bt = sc.nextInt();
            System.out.print("Arrival Time: ");
            int art = sc.nextInt();

            proc[i] = new Process(pid, bt, art);
        }

        findavgTime(proc, proc.length);

        sc.close();
    }
}
