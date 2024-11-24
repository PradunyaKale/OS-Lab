#include <stdio.h>
#include <limits.h>
#include <stdbool.h>

void printFrames(int frames[], int capacity) {
    printf("[ ");
    for (int i = 0; i < capacity; i++) {
        if (frames[i] == -1)
            printf("- ");
        else
            printf("%d ", frames[i]);
    }
    printf("]");
}

void fifo(int pages[], int n, int capacity) {
    int frames[capacity];
    int faults = 0, front = 0;

    for (int i = 0; i < capacity; i++)
        frames[i] = -1;

    printf("\nFIFO:\n");
    for (int i = 0; i < n; i++) {
        bool found = false;
        for (int j = 0; j < capacity; j++) {
            if (frames[j] == pages[i]) {
                found = true;
                break;
            }
        }

        if (!found) {
            frames[front] = pages[i];
            front = (front + 1) % capacity;
            faults++;
            printf("Page %d: ", pages[i]);
            printFrames(frames, capacity);
            printf(" - Page Fault\n");
        } else {
            printf("Page %d: ", pages[i]);
            printFrames(frames, capacity);
            printf(" - Page Hit\n");
        }
    }

    printf("Total FIFO Page Faults: %d\n", faults);
}

void lru(int pages[], int n, int capacity) {
    int frames[capacity];
    int timestamps[capacity];
    int faults = 0;

    for (int i = 0; i < capacity; i++) {
        frames[i] = -1;
        timestamps[i] = -1;
    }

    printf("\nLRU:\n");
    for (int i = 0; i < n; i++) {
        bool found = false;
        for (int j = 0; j < capacity; j++) {
            if (frames[j] == pages[i]) {
                timestamps[j] = i;
                found = true;
                break;
            }
        }

        if (!found) {
            int lru = 0;
            for (int j = 1; j < capacity; j++) {
                if (timestamps[j] < timestamps[lru])
                    lru = j;
            }
            frames[lru] = pages[i];
            timestamps[lru] = i;
            faults++;
            printf("Page %d: ", pages[i]);
            printFrames(frames, capacity);
            printf(" - Page Fault\n");
        } else {
            printf("Page %d: ", pages[i]);
            printFrames(frames, capacity);
            printf(" - Page Hit\n");
        }
    }

    printf("Total LRU Page Faults: %d\n", faults);
}

void optimal(int pages[], int n, int capacity) {
    int frames[capacity];
    int faults = 0;

    for (int i = 0; i < capacity; i++)
        frames[i] = -1;

    printf("\nOptimal:\n");
    for (int i = 0; i < n; i++) {
        bool found = false;
        for (int j = 0; j < capacity; j++) {
            if (frames[j] == pages[i]) {
                found = true;
                break;
            }
        }

        if (!found) {
            int replace = -1, farthest = i + 1;
            for (int j = 0; j < capacity; j++) {
                int next_use = INT_MAX;
                for (int k = i + 1; k < n; k++) {
                    if (frames[j] == pages[k]) {
                        next_use = k;
                        break;
                    }
                }
                if (next_use > farthest) {
                    farthest = next_use;
                    replace = j;
                }
            }
            if (replace == -1)
                replace = 0;

            frames[replace] = pages[i];
            faults++;
            printf("Page %d: ", pages[i]);
            printFrames(frames, capacity);
            printf(" - Page Fault\n");
        } else {
            printf("Page %d: ", pages[i]);
            printFrames(frames, capacity);
            printf(" - Page Hit\n");
        }
    }

    printf("Total Optimal Page Faults: %d\n", faults);
}

int main() {
    int n, capacity;

    printf("Enter number of pages in reference string: ");
    scanf("%d", &n);
    int pages[n];

    printf("Enter the reference string: ");
    for (int i = 0; i < n; i++) {
        scanf("%d", &pages[i]);
    }

    printf("Enter the number of frames: ");
    scanf("%d", &capacity);

    fifo(pages, n, capacity);
    lru(pages, n, capacity);
    optimal(pages, n, capacity);

    return 0;
}
