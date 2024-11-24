#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdlib.h>
#include <unistd.h>

#define BUFFER_SIZE 5
#define PRODUCER_THREADS 3
#define CONSUMER_THREADS 3
#define PRODUCE_COUNT 10

int buffer[BUFFER_SIZE];
int in = 0;
int out = 0;

pthread_mutex_t mutex;
sem_t empty;
sem_t full;

void* producer(void* arg) {
    int id = *((int*)arg);
    for(int i = 0; i < PRODUCE_COUNT; i++) {
        int item = rand() % 100;

        sem_wait(&empty);
        pthread_mutex_lock(&mutex);

        buffer[in] = item;
        printf("Producer %d produced item %d at buffer[%d]\n", id, item, in);
        in = (in + 1) % BUFFER_SIZE;

        pthread_mutex_unlock(&mutex);
        sem_post(&full);

        sleep(rand() % 2);
    }
    pthread_exit(NULL);
}

void* consumer(void* arg) {
    int id = *((int*)arg);
    for(int i = 0; i < PRODUCE_COUNT; i++) {
        sem_wait(&full);
        pthread_mutex_lock(&mutex);

        int item = buffer[out];
        printf("\tConsumer %d consumed item %d from buffer[%d]\n", id, item, out);
        out = (out + 1) % BUFFER_SIZE;

        pthread_mutex_unlock(&mutex);
        sem_post(&empty);

        sleep(rand() % 2);
    }
    pthread_exit(NULL);
}

int main() {
    pthread_t producers[PRODUCER_THREADS], consumers[CONSUMER_THREADS];
    int producer_ids[PRODUCER_THREADS], consumer_ids[CONSUMER_THREADS];

    pthread_mutex_init(&mutex, NULL);
    sem_init(&empty, 0, BUFFER_SIZE);
    sem_init(&full, 0, 0);

    for(int i = 0; i < PRODUCER_THREADS; i++) {
        producer_ids[i] = i + 1;
        if(pthread_create(&producers[i], NULL, producer, (void*)&producer_ids[i]) != 0) {
            perror("Failed to create producer thread");
        }
    }

    for(int i = 0; i < CONSUMER_THREADS; i++) {
        consumer_ids[i] = i + 1;
        if(pthread_create(&consumers[i], NULL, consumer, (void*)&consumer_ids[i]) != 0) {
            perror("Failed to create consumer thread");
        }
    }

    for(int i = 0; i < PRODUCER_THREADS; i++) {
        pthread_join(producers[i], NULL);
    }
    for(int i = 0; i < CONSUMER_THREADS; i++) {
        pthread_join(consumers[i], NULL);
    }

    pthread_mutex_destroy(&mutex);
    sem_destroy(&empty);
    sem_destroy(&full);

    printf("All threads have finished execution.\n");
    return 0;
}
