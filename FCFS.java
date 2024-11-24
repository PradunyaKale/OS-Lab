import java.util.Scanner;
import java.util.Arrays;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        
        int n = sc.nextInt();
        int[] bt = new int[n];
        int[] at = new int[n];
        
        System.out.println("\nEnter the Arrival Time for each process.");       
        for (int i = 0; i < n; i++) {
            System.out.print("\nFor Process " + (i + 1) + ": ");
            at[i] = sc.nextInt();       
        }
        
        System.out.println("\nEnter the Burst Time for each process.");       
        for (int i = 0; i < n; i++) {
            System.out.print("\nFor Process " + (i + 1) + ": ");
            bt[i] = sc.nextInt();       
        }
        
        avg_wt_tt_ct(n, at, bt);

        sc.close();
    }

    private static void waiting_time(int n, int[] at, int[] bt, int[] wt) {
        int[] service_time = new int[n];
        service_time[0] = at[0];
        wt[0] = 0;

        for (int i = 1; i < n; i++) {
            service_time[i] = service_time[i - 1] + bt[i - 1];
            wt[i] = service_time[i] - at[i];
            if (wt[i] < 0) {
                wt[i] = 0;
            }
        }
    }

    private static void turnaround_time(int n, int[] bt, int[] wt, int[] tt) {
        for (int i = 0; i < n; i++) {
            tt[i] = bt[i] + wt[i];
        }   
    }

    private static void completion_time(int n, int[] at, int[] tt, int[] ct) {
        for (int i = 0; i < n; i++) {
            ct[i] = at[i] + tt[i];
        }
    }

    private static void avg_wt_tt_ct(int n, int[] at, int[] bt) {
        int[] wt = new int[n];
        int[] tt = new int[n];
        int[] ct = new int[n];
        int[][] processes = new int[n][3];

        for (int i = 0; i < n; i++) {
            processes[i][0] = i + 1;
            processes[i][1] = at[i];
            processes[i][2] = bt[i];
        }

        Arrays.sort(processes, (a, b) -> a[1] - b[1]);

        int[] sorted_bt = new int[n];
        int[] sorted_at = new int[n];
        
        for (int i = 0; i < n; i++) {
            sorted_at[i] = processes[i][1];
            sorted_bt[i] = processes[i][2];
        }

        waiting_time(n, sorted_at, sorted_bt, wt);
        turnaround_time(n, sorted_bt, wt, tt);
        completion_time(n, sorted_at, tt, ct);

        System.out.println("\nProcesses || Arrival Time || Burst Time || Completion Time || Waiting Time || Turn-Around Time ");
        
        float awt = 0;
        float att = 0;

        for (int i = 0; i < n; i++) {
            awt += wt[i];
            att += tt[i];
            System.out.println(processes[i][0] + "\t||\t" + sorted_at[i] + "\t||\t" + sorted_bt[i] + "\t||\t" + ct[i] + "\t||\t" + wt[i] + "\t||\t " + tt[i]);       
        }

        awt /= n;
        att /= n;

        System.out.println("\nAverage waiting time = " + awt);       
        System.out.println("\nAverage turn around time = " + att);   
    }
}