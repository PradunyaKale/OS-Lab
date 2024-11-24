#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

void sstf(int request[], int n, int head);
void scan(int request[], int n, int head, int disk_size, char direction);
void c_look(int request[], int n, int head, char direction);

int main() {
    int n, i, head, disk_size;
    printf("Enter the number of disk requests: ");
    scanf("%d", &n);

    int request[n];
    printf("Enter the disk requests: ");
    for(i = 0; i < n; i++) {
        scanf("%d", &request[i]);
    }

    printf("Enter the initial head position: ");
    scanf("%d", &head);

    printf("Enter the disk size: ");
    scanf("%d", &disk_size);

    int choice;
    char direction;
    do {
        printf("\nDisk Scheduling Algorithms\n");
        printf("1. SSTF\n");
        printf("2. SCAN\n");
        printf("3. C-LOOK\n");
        printf("4. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch(choice) {
            case 1:
                sstf(request, n, head);
                break;
            case 2:
                printf("Enter direction (L for left, R for right): ");
                scanf(" %c", &direction); // The space before %c is to consume any leftover newline characters.
                scan(request, n, head, disk_size, direction);
                break;
            case 3:
                printf("Enter direction (L for left, R for right): ");
                scanf(" %c", &direction);
                c_look(request, n, head, direction);
                break;
            case 4:
                printf("Exiting...\n");
                break;
            default:
                printf("Invalid choice! Try again.\n");
        }
    } while(choice != 4);

    return 0;
}

void sstf(int request[], int n, int head) {
    int seek_sequence[n], completed[n], seek_count = 0;
    int distance, cur_track, min_distance, index;

    for (int i = 0; i < n; i++) {
        completed[i] = 0;
    }

    printf("\nSSTF Disk Scheduling:\n");

    for (int i = 0; i < n; i++) {
        min_distance = INT_MAX;
        for (int j = 0; j < n; j++) {
            if (!completed[j]) {
                distance = abs(head - request[j]);
                if (distance < min_distance) {
                    min_distance = distance;
                    cur_track = request[j];
                    index = j;
                }
            }
        }
        head = cur_track;
        seek_sequence[i] = head;
        completed[index] = 1;
        seek_count += min_distance;
    }

    printf("Seek Sequence: ");
    for (int i = 0; i < n; i++) {
        printf("%d ", seek_sequence[i]);
    }
    printf("\nTotal Seek Count: %d\n", seek_count);
}

void scan(int request[], int n, int head, int disk_size, char direction) {
    int seek_sequence[n+2], seek_count = 0;
    int temp[n+2], i, j;
    int left = 0, right = 0;

    for (i = 0; i < n; i++) {
        temp[i] = request[i];
    }
    temp[n] = head;
    temp[n+1] = disk_size - 1;

    for (i = 0; i < n+2; i++) {
        for (j = i + 1; j < n+2; j++) {
            if (temp[i] > temp[j]) {
                int temp_val = temp[i];
                temp[i] = temp[j];
                temp[j] = temp_val;
            }
        }
    }

    int pos;
    for (i = 0; i < n+2; i++) {
        if (temp[i] == head) {
            pos = i;
            break;
        }
    }

    printf("\nSCAN Disk Scheduling:\n");

    if (direction == 'R') {
        for (i = pos; i < n+2; i++) {
            seek_sequence[i-pos] = temp[i];
        }
        for (i = pos - 1; i >= 0; i--) {
            seek_sequence[n+1 - (pos - 1 - i)] = temp[i];
        }
    } else if (direction == 'L') {
        for (i = pos; i >= 0; i--) {
            seek_sequence[pos - i] = temp[i];
        }
        for (i = pos + 1; i < n+2; i++) {
            seek_sequence[pos + (i - pos)] = temp[i];
        }
    }

    for (i = 0; i < n+1; i++) {
        seek_count += abs(seek_sequence[i+1] - seek_sequence[i]);
    }

    printf("Seek Sequence: ");
    for (i = 0; i < n+2; i++) {
        if (seek_sequence[i] != head) {
            printf("%d ", seek_sequence[i]);
        }
    }
    printf("\nTotal Seek Count: %d\n", seek_count);
}

void c_look(int request[], int n, int head, char direction) {
    int seek_sequence[n], seek_count = 0;
    int temp[n], i, j;

    for (i = 0; i < n; i++) {
        temp[i] = request[i];
    }

    for (i = 0; i < n; i++) {
        for (j = i + 1; j < n; j++) {
            if (temp[i] > temp[j]) {
                int temp_val = temp[i];
                temp[i] = temp[j];
                temp[j] = temp_val;
            }
        }
    }

    int pos;
    for (i = 0; i < n; i++) {
        if (temp[i] > head) {
            pos = i;
            break;
        }
    }

    printf("\nC-LOOK Disk Scheduling:\n");

    if (direction == 'R') {
        for (i = pos; i < n; i++) {
            seek_sequence[i-pos] = temp[i];
        }

        for (i = 0; i < pos; i++) {
            seek_sequence[n-pos+i] = temp[i];
        }
    } else if (direction == 'L') {
        for (i = pos-1; i >= 0; i--) {
            seek_sequence[pos-1-i] = temp[i];
        }

        for (i = n-1; i >= pos; i--) {
            seek_sequence[n-1-(i-pos)] = temp[i];
        }
    }

    for (i = 0; i < n-1; i++) {
        seek_count += abs(seek_sequence[i+1] - seek_sequence[i]);
    }
    seek_count += abs(head - seek_sequence[0]);

    printf("Seek Sequence: ");
    for (i = 0; i < n; i++) {
        printf("%d ", seek_sequence[i]);
    }
    printf("\nTotal Seek Count: %d\n", seek_count);
}