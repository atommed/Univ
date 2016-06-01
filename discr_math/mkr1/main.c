#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>

#define LEN(arr) (sizeof(arr)/sizeof((arr)[0]))

static inline unsigned int midx(unsigned int row,unsigned int col, unsigned int col_c){
  return (row-1)*col_c + col -1;
}

typedef enum {
  FLOYD,
  BELLMAN
} Method;

#define NO_PATH (INT_MAX)

void print_smat(int* arr,unsigned int n){
  unsigned int i,j;
  for(i = 1; i<=n; i++){
    for(j = 1; j <=n;j++){
      int val = arr[midx(i,j,n)];
      if(val != NO_PATH)
      printf("%d ",arr[midx(i,j,n)]);
      else printf("~ ");
    }
    puts("");
  }
}

static void initial_fill_mat(int* arr, unsigned int length){
  for(size_t i = 0; i < length; i++)
    arr[i] = NO_PATH;
}

void floyd_method(int* d, int* pm, unsigned int n){
  unsigned int k,i,j;
  int w_ik, w_kj,w_cur;
  
  puts("Begin Floyd algo");

  for(i = 1; i <= n; i++)
    for(j = 1; j <= n; j++)
      pm[midx(i,j,n)] = j;
  
  for(k = 1; k <= n; k++){
    for(i = 1; i <= n; i++)
      for(j = 1; j <= n; j++){
	w_cur = d[midx(i,j,n)];
	w_ik = d[midx(i,k,n)];
	w_kj = d[midx(k,j,n)];
	if(w_ik != NO_PATH & w_kj != NO_PATH)
	  if(w_ik + w_kj < w_cur){
	    d[midx(i,j,n)] = w_ik + w_kj;
	    pm[midx(i,j,n)] = pm[midx(i,k,n)];
	  }
      }
    printf("D%u\n",k);
    print_smat(d,n);
    printf("S%u\n",k);
    print_smat(pm,n);
  }
}

int main(int argc, char* argv[]){
  int* matrix, *path_matrix;
  unsigned int i,j;
  unsigned int nvert;
  Method method;
  char input_buff[512];
  
  matrix = NULL;
  
  if(argc!=3){
    fprintf(stderr, "Usage: %s method(floyd|bellman) graph_vertex_count\n",argv[0]);
    exit(EXIT_FAILURE);
  }
  if(!strcmp("floyd",argv[1])){
    method = FLOYD;
  } else if(!strcmp("bellman",argv[1])) {
    method = BELLMAN;
  } else {
    fputs("Method can be only \"floyd\" or \"bellman\"",stderr);
    exit(EXIT_FAILURE);
  }
  if(sscanf(argv[2],"%ul",&nvert) != 1){
    fputs("Can't parse vertex count",stderr);
    exit(EXIT_FAILURE);
  }

  matrix = malloc(sizeof(int) * nvert * nvert);
  path_matrix = malloc(sizeof(unsigned int) * nvert * nvert);
  initial_fill_mat(matrix, nvert*nvert);

  puts("Enter the matrix");
  for(i = 1; i <= nvert; i++){
    char* curr_number;
    fgets(input_buff, LEN(input_buff), stdin);
    curr_number =  strtok(input_buff," \n");
    for(j = 1; j <= nvert; j++){
      if(curr_number == NULL) {
	puts("No enough data, reenter this row");
	i--;
	break;
      }
      if(!strcmp(curr_number,"~")) {
	matrix[midx(i,j,nvert)] = NO_PATH;
      } else if(sscanf(curr_number,"%ul", &matrix[midx(i,j,nvert)]) != 1){
	printf("Failed to parse \"%s\", reenter this row\n", curr_number);
	i--;
	break;
      }
      if(curr_number != NULL)
	curr_number = strtok(NULL," \n");
    }
  }

  puts("You entered");
  print_smat(matrix, nvert);

  if(method == FLOYD){
    floyd_method(matrix, path_matrix,nvert);
    puts("Floyd res:");
    print_smat(matrix,nvert);
    puts("Floyd path:");
    print_smat(path_matrix, nvert);
  }

  free(matrix);
  free(path_matrix);
  exit(EXIT_SUCCESS);
}
