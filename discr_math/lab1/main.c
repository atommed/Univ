#include "am_matrix/am_matrix.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define LEN(arr) (sizeof(arr) / sizeof((arr)[0]))

void print_am(am_matrix_i *m) {
  for (size_t r = 0; r < am_matrix_i_row_c(m); r++) {
    for (size_t c = 0; c < am_matrix_i_col_c(m); c++)
      printf("%d ", *am_matrix_i_at(m, r, c));
    puts("");
  }
}

static int dosyaj_graph_boolean_map_f(int x) {
  if (x > 0)
    return 1;
  else
    return 0;
}

static int put_zeroes_in_m_map_f(int x) { return 0; }

static inline unsigned int count_graph_cycles(am_matrix_i *sm_m) {
  size_t dism = am_matrix_i_row_c(sm_m);
  unsigned int res = 0;
  for (size_t i = 0; i < dism; i++)
    res += *am_matrix_i_at(sm_m, i, i);
  return res;
}

static inline unsigned int count_graph_paths(am_matrix_i *sm_m) {
  size_t dism = am_matrix_i_row_c(sm_m);
  unsigned int res = 0;
  for (size_t r = 0; r < dism; r++)
    for (size_t c = 0; c < dism; c++)
      res += *am_matrix_i_at(sm_m, r, c);
  return res;
}

void show_paths_cycles(am_matrix_i *graph) {
  size_t dism = am_matrix_i_row_c(graph);
  am_matrix_i *power = am_matrix_i_copy(graph);

  for (size_t i = 1; i < dism; i++) {
    printf("There are %d path(s) and %d cycle(s) of length %zu\n",
           count_graph_paths(power), count_graph_cycles(power), i);
    puts("See:");
    print_am(power);
    am_matrix_i_mul(power, graph, power);
  }

  am_matrix_i_free(power);
}

am_matrix_i *dist_matrix_gen(am_matrix_i *graph) {
  am_matrix_i *curr_len_np = am_matrix_i_copy(graph);
  am_matrix_i *sum_m = am_matrix_i_copy(graph);
  for (size_t i = 0; i < am_matrix_i_row_c(graph) - 2; i++) {
    am_matrix_i_mul(curr_len_np, graph, curr_len_np);
    am_matrix_i_sum(curr_len_np, sum_m, sum_m);
  }
  am_matrix_i_map(sum_m, dosyaj_graph_boolean_map_f, sum_m);
  // Put 1 on diagonal
  for (size_t i = 0; i < am_matrix_i_row_c(graph); i++)
    *am_matrix_i_at(sum_m, i, i) = 1;
  am_matrix_i_free(curr_len_np);
  return sum_m;
}

am_matrix_i *read_graph(void) {
  size_t m_dism = 0;
  char buff[30];
  do {
    puts("Enter number of vertices");
    fgets(buff, LEN(buff), stdin);
  } while (sscanf(buff, "%zu", &m_dism) != 1 || m_dism < 1);

  am_matrix_i *ret = am_matrix_i_new(m_dism, m_dism);
  am_matrix_i_map(ret, put_zeroes_in_m_map_f, ret);
  while (1) {
    puts("Enter pairs or \'q\' to exit");
    fgets(buff, LEN(buff), stdin);
    if (strcmp(buff, "q\n") == 0)
      break;
    size_t a, b;
    if (sscanf(buff, "%zu %zu\n", &a, &b) != 2)
      puts("Invalid input");
    else {
      int *v = am_matrix_i_at(ret, a, b);
      if (v == NULL)
        puts("No such verticies");
      else
        *v = 1;
    }
  }

  puts("You entered");
  print_am(ret);

  return ret;
}

int main(void) {
  am_matrix_i *graph = read_graph();

  puts("\nNow paths and cycles:");
  show_paths_cycles(graph);

  puts("\nDosyaj:");
  am_matrix_i *dist = dist_matrix_gen(graph);
  print_am(dist);

  am_matrix_i_free(dist);
  am_matrix_i_free(graph);
  return EXIT_SUCCESS;
}
