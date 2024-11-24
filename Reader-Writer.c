#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

int read_count = 0;
sem_t mutex, wrt;

void *reader(void *arg);
void *writer(void *arg);

int main() {
    pthread_t rtid[5], wtid[5];

    sem_init(&mutex, 0, 1);
    sem_init(&wrt, 0, 1);

    for (int i = 0; i < 5; i++) {
        pthread_create(&rtid[i], NULL, reader, (void *) (intptr_t) i);
        pthread_create(&wtid[i], NULL, writer, (void *) (intptr_t) i);
    }

    for (int i = 0; i < 5; i++) {
        pthread_join(rtid[i], NULL);
        pthread_join(wtid[i], NULL);
    }

    sem_destroy(&mutex);
    sem_destroy(&wrt);

    return 0;
}

void *reader(void *arg) {
    int num = (intptr_t) arg;

    printf("Reader %d is trying to enter\n", num);
    sem_wait(&mutex);
    read_count++;
    if (read_count == 1) {
        sem_wait(&wrt);
    }
    sem_post(&mutex);

    printf("Reader %d is reading\n", num);
    sleep(1);

    sem_wait(&mutex);
    read_count--;
    if (read_count == 0) {
        sem_post(&wrt);
    }
    sem_post(&mutex);
    printf("Reader %d is exiting\n", num);

    return NULL;
}

void *writer(void *arg) {
    int num = (intptr_t) arg;

    printf("Writer %d is trying to enter\n", num);
    sem_wait(&wrt);
    printf("Writer %d is writing\n", num);
    sleep(1);
    sem_post(&wrt);
    printf("Writer %d is exiting\n", num);

    return NULL;
}
