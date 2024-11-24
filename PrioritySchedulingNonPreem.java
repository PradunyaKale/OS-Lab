import java.util.*;

class Process {
    int at, bt, pr, pno;

    Process(int pno, int at, int bt, int pr) {
        this.pno = pno;
        this.pr = pr;
        this.at = at;
        this.bt = bt;
    }
}

public class PrioritySchedulingNonPreem {
    static int totalprocess;
    static Process[] proc;

    static void findgc() {
        int[] wt = new int[totalprocess]; // Waiting Time
        int[] tat = new int[totalprocess]; // Turnaround Time
        int[] stime = new int[totalprocess]; // Start Time
        int[] ctime = new int[totalprocess]; // Completion Time
        double wavg = 0, tavg = 0;

        int currentTime = 0;

        for (int i = 0; i < totalprocess; i++) {
            // Start time is the maximum of current time and arrival time
            stime[i] = Math.max(currentTime, proc[i].at);
            ctime[i] = stime[i] + proc[i].bt; // Completion time
            tat[i] = ctime[i] - proc[i].at; // Turnaround time
            wt[i] = tat[i] - proc[i].bt; // Waiting time

            currentTime = ctime[i]; // Update current time

            // Accumulate averages
            wavg += wt[i];
            tavg += tat[i];
        }

        System.out.println("Process_no\tArrival_time\tBurst_time\tPriority\tStart_time\tComplete_time\tTurn_Around_Time\tWaiting_Time");

        for (int i = 0; i < totalprocess; i++) {
            System.out.println(proc[i].pno + "\t\t" + proc[i].at + "\t\t" + proc[i].bt + "\t\t" + proc[i].pr + "\t\t" + stime[i] + "\t\t" + ctime[i] + "\t\t" + tat[i] + "\t\t\t" + wt[i]);
        }

        System.out.println("Average waiting time is : " + (wavg / totalprocess));
        System.out.println("Average turnaround time : " + (tavg / totalprocess));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        totalprocess = sc.nextInt();
        proc = new Process[totalprocess];

        for (int i = 0; i < totalprocess; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int bt = sc.nextInt();
            System.out.print("Enter priority for process " + (i + 1) + ": ");
            int pr = sc.nextInt();
            proc[i] = new Process(i + 1, at, bt, pr);
        }

        // Sort processes by arrival time, then by priority (smaller number is higher priority)
        Arrays.sort(proc, (a, b) -> {
            if (a.at == b.at) {
                return a.pr - b.pr; // Higher priority first
            } else {
                return a.at - b.at; // Earlier arrival first
            }
        });

        findgc();
        sc.close();
    }
}
