#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    pid_t pid;

    pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printf("Child process sleeping for 5 seconds...\n");
        sleep(5);
        printf("Child process executing after parent terminated.\n");
        exit(0);
    } else {
        // Parent process
        printf("Parent process exiting immediately...\n");
        exit(0);
    }

    return 0;
}
