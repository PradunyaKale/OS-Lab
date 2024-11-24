#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void bubbleSort(int arr[], int n) {
    int i, j, temp;
    for (i = 0; i < n-1; i++) {
        for (j = 0; j < n-i-1; j++) {
            if (arr[j] > arr[j+1]) {
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
}

void printProcessInfo(const char *processType) {
    printf("%s - PID: %d, PPID: %d, PGID: %d, UID: %d, GID: %d\n",
           processType, getpid(), getppid(), getpgid(0), getuid(), getgid());
}

void createZombieProcess() {
    pid_t pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printProcessInfo("Zombie Child");
        printf("Zombie Child process (PID: %d) is exiting.\n", getpid());
        exit(0);
    } else {
        // Parent process
        sleep(5);
        printf("Parent process (PID: %d) is now reaping the zombie child.\n", getpid());
        wait(NULL);
    }
}

void createOrphanProcess() {
    pid_t pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printProcessInfo("Orphan Child");
        printf("Orphan Child process (PID: %d) is waiting for parent to exit.\n", getpid());
        sleep(5);
        printProcessInfo("Orphaned Child");
        printf("Orphan process (PID: %d) is now adopted by init (PID 1).\n", getpid());
    } else {
        // Parent process
        printProcessInfo("Orphan Parent");
        printf("Orphan Parent process (PID: %d) is exiting.\n", getpid());
        exit(0);
    }
}

int main() {
    int n, i;

    printf("Enter the number of elements: ");
    scanf("%d", &n);

    int arr[n];
    printf("Enter %d integers:\n", n);
    for (i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    pid_t pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printProcessInfo("Sorting Child");
        printf("Sorting Child process sorting...\n");
        bubbleSort(arr, n);
        printf("Sorted array by child process:\n");
        for (i = 0; i < n; i++) {
            printf("%d ", arr[i]);
        }
        printf("\n");
        createZombieProcess();
        createOrphanProcess();
        exit(0);
    } else {
        // Parent process
        wait(NULL);
        printProcessInfo("Sorting Parent");
        printf("Sorting Parent process sorting...\n");
        bubbleSort(arr, n);
        printf("Sorted array by parent process:\n");
        for (i = 0; i < n; i++) {
            printf("%d ", arr[i]);
        }
        printf("\n");
    }

    return 0;
}
