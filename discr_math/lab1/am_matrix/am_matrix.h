#pragma once

#include <stdlib.h>

typedef struct am_matrix_i am_matrix_i;

am_matrix_i* am_matrix_i_new(size_t rows, size_t cols);
void am_matrix_i_free(am_matrix_i* m);
am_matrix_i* am_matrix_i_copy(const am_matrix_i* src);

size_t am_matrix_i_row_c(const am_matrix_i* m);
size_t am_matrix_i_col_c(const am_matrix_i* m);

int* am_matrix_i_at(am_matrix_i* m, size_t row, size_t col);
int* am_matrix_i_getcol(const am_matrix_i* m, size_t col, int res[]);

am_matrix_i* am_matrix_i_mul(const am_matrix_i* m1,
			     const am_matrix_i* m2,
			     am_matrix_i* res);
