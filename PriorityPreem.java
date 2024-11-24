import java.util.*;

class Process {
    String pid;
    int bt, at, priority, rt;

    Process(String pid, int at, int bt, int priority) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
        this.rt = bt; // Remaining time is initialized to burst time
    }
}

public class PriorityPreem {

    static void findWaitingTime(Process proc[], int n, int wt[]) {
        int t = 0; // Current time
        int complete = 0; // Completed processes
        int minPriority = Integer.MAX_VALUE;
        int shortest = 0;
        boolean check = false;

        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((proc[j].at <= t) && (proc[j].priority < minPriority) && (proc[j].rt > 0)) {
                    minPriority = proc[j].priority;
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                t++;
                continue;
            }

            proc[shortest].rt--;

            minPriority = proc[shortest].rt == 0 ? Integer.MAX_VALUE : proc[shortest].priority;

            if (proc[shortest].rt == 0) {
                complete++;
                check = false;
                int finish_time = t + 1;
                wt[shortest] = finish_time - proc[shortest].bt - proc[shortest].at;
                if (wt[shortest] < 0) {
                    wt[shortest] = 0;
                }
            }
            t++;
        }
    }

    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = proc[i].bt + wt[i];
        }
    }

    static void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        findWaitingTime(proc, n, wt);
        findTurnAroundTime(proc, n, wt, tat);

        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + proc[i].pid + "\t\t" + proc[i].bt + "\t\t " + wt[i] + "\t\t " + tat[i]);
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

            proc[i] = new Process(pid, at, bt, priority);
        }

        findavgTime(proc, numProcesses);

        sc.close();
    }
}
