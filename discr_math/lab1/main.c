#include "am_matrix/am_matrix.h"
#include <stdlib.h>
#include <stdio.h>

void print_am(am_matrix_i* m){
  for(size_t r = 0; r< am_matrix_i_row_c(m);r++){
    for(size_t c=  0; c< am_matrix_i_col_c(m);c++)
      printf("%d ",*am_matrix_i_at(m,r,c));
    puts("");
  }
      
}

int main(void){
  am_matrix_i* s = am_matrix_i_new(3,3);
  for(size_t r = 0; r< 3; r++)
    for(size_t c = 0; c< 3; c++)
      *am_matrix_i_at(s,r,c) = 10;
  
  am_matrix_i* a = am_matrix_i_new(3,4);
  for(size_t r=0; r< 3; r++)
    for(size_t c = 0; c<4;c++)
      *am_matrix_i_at(a,r,c) = 2;
  *am_matrix_i_at(a,1,1) = 42;

  am_matrix_i* b = am_matrix_i_new(4,5);
  for(size_t r = 0; r<4;r++)
    for(size_t c  = 0; c<5;c++)
      *am_matrix_i_at(b,r,c) = 2;
  *am_matrix_i_at(b,0,2) = -2;

  am_matrix_i* cm = am_matrix_i_mul(a,b,NULL);
  print_am(cm);

  am_matrix_i_mul(s,s,s);
  print_am(s);
  am_matrix_i_mul(s,s,s);
  print_am(s);
  am_matrix_i_mul(s,s,s);
  print_am(s);
  am_matrix_i_mul(s,s,s);
  print_am(s);

  am_matrix_i_free(s);
  am_matrix_i_free(a);
  am_matrix_i_free(b);
  am_matrix_i_free(cm);
  return EXIT_SUCCESS;
}
