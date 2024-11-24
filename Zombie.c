#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    pid_t pid;

    pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printf("Child process exiting...\n");
        exit(0);
    } else {
        // Parent process
        printf("Parent process sleeping for 10 seconds...\n");
        sleep(10);
        wait(NULL);
        printf("Parent process collecting child's exit status. No more zombie.\n");
    }

    return 0;
}
