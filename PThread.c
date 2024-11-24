#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define M 3
#define K 2
#define N 3

int A[M][K] = { {1,4}, {2,5}, {3,6} };
int B[K][N] = { {8,7,6}, {5,4,3} };
int C[M][N];

struct v {
   int i;
   int j;
};

void *runner(void *param);

int main(int argc, char *argv[]) {
   int i, j;
   pthread_t threads[M * N];
   struct v *data_array[M * N];
   
   int count = 0;
   for(i = 0; i < M; i++) {
      for(j = 0; j < N; j++) {
         struct v *data = (struct v *) malloc(sizeof(struct v));
         data->i = i;
         data->j = j;
         data_array[count] = data;
         pthread_create(&threads[count], NULL, runner, data);
         count++;
      }
   }

   for(i = 0; i < count; i++) {
      pthread_join(threads[i], NULL);
      free(data_array[i]);
   }

   for(i = 0; i < M; i++) {
      for(j = 0; j < N; j++) {
         printf("%d ", C[i][j]);
      }
      printf("\n");
   }

   return 0;
}

void *runner(void *param) {
   struct v *data = param;
   int n, sum = 0;

   for(n = 0; n < K; n++) {
      sum += A[data->i][n] * B[n][data->j];
   }

   C[data->i][data->j] = sum;

   pthread_exit(0);
}
