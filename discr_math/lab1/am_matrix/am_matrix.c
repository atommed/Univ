#include "am_matrix.h"
#include <stdlib.h>
#include <string.h>

static inline size_t am_matrix_idx(size_t row, size_t col, size_t col_c) {
  return row * col_c + col;
}

typedef struct am_matrix_i {
  size_t rows;
  size_t cols;
  int data[];
} am_matrix_i;

#define AM_MATRIX_I_SLEN(r, c)                                                 \
  (sizeof(struct am_matrix_i) + sizeof(int) * r * c)

am_matrix_i *am_matrix_i_new(size_t rows, size_t cols) {
  am_matrix_i *ret = malloc(AM_MATRIX_I_SLEN(rows, cols));
  ret->rows = rows;
  ret->cols = cols;
  return ret;
}

void am_matrix_i_free(am_matrix_i *m) { free(m); }

am_matrix_i *am_matrix_i_copy(const am_matrix_i *src) {
  size_t rows = src->rows;
  size_t cols = src->cols;
  am_matrix_i *ret = malloc(AM_MATRIX_I_SLEN(rows, cols));
  memcpy(ret, src, AM_MATRIX_I_SLEN(rows, cols));
  return ret;
}

size_t am_matrix_i_row_c(const am_matrix_i *m) { return m->rows; }
size_t am_matrix_i_col_c(const am_matrix_i *m) { return m->cols; }

int *am_matrix_i_at(am_matrix_i *m, size_t row, size_t col) {
  if (row >= m->rows || col >= m->cols)
    return NULL;
  return &m->data[am_matrix_idx(row, col, m->cols)];
}

int *am_matrix_i_getcol(const am_matrix_i *m, size_t col, int res[]) {
  if (res == NULL)
    res = malloc(sizeof(int) * m->rows);
  size_t curr_idx = col;
  for (size_t row = 0; row < m->rows; row++) {
    res[row] = m->data[curr_idx];
    curr_idx += m->cols;
  }
  return res;
}

static inline int am_matrix_i_vm(const int *a, const int *b, size_t len) {
  int res = 0;
  for (size_t i = 0; i < len; i++)
    res += a[i] * b[i];
  return res;
}

am_matrix_i *am_matrix_i_mul(const am_matrix_i *l, const am_matrix_i *r,
                             am_matrix_i *res) {
  am_matrix_i *l_copy = NULL;
  am_matrix_i *r_copy = NULL;
  size_t n_r = l->rows;
  size_t n_c = r->cols;
  if (res == NULL)
    res = am_matrix_i_new(n_r, n_c);
  if (res->rows != n_r || res->cols != n_c)
    return NULL;
  if (res == l) {
    l_copy = am_matrix_i_copy(l);
    l = l_copy;
  }
  if (res == r) {
    if (l_copy != NULL)
      r = l;
    else {
      r_copy = am_matrix_i_copy(r);
      r = r_copy;
    }
  }

  int *r_col_buff = malloc(sizeof(int) * r->rows);

  for (size_t r_col = 0; r_col < r->cols; r_col++) {
    am_matrix_i_getcol(r, r_col, r_col_buff);
    for (size_t l_row = 0; l_row < l->rows; l_row++) {
      res->data[am_matrix_idx(l_row, r_col, n_c)] =
          am_matrix_i_vm(&(l->data[l->cols * l_row]), r_col_buff, l->cols);
    }
  }

  free(r_col_buff);
  if (l_copy)
    am_matrix_i_free(l_copy);
  if (r_copy)
    am_matrix_i_free(r_copy);
  return res;
}

am_matrix_i *am_matrix_i_sum(const am_matrix_i *l, const am_matrix_i *r,
                             am_matrix_i *res) {
  am_matrix_i *l_copy = NULL;
  am_matrix_i *r_copy = NULL;
  size_t n_r = l->rows;
  size_t n_c = l->cols;
  if (res == NULL)
    res = am_matrix_i_new(n_r, n_c);
  if (res->rows != n_r || res->cols != n_c)
    return NULL;
  if (res == l) {
    l_copy = am_matrix_i_copy(l);
    l = l_copy;
  }
  if (res == r) {
    if (l_copy != NULL)
      r = l;
    else {
      r_copy = am_matrix_i_copy(r);
      r = r_copy;
    }
  }

  for (size_t i = 0; i < n_r * n_c; i++)
    res->data[i] = l->data[i] + r->data[i];

  if (l_copy)
    am_matrix_i_free(l_copy);
  if (r_copy)
    am_matrix_i_free(r_copy);
  return res;
}

am_matrix_i *am_matrix_i_map(const am_matrix_i *m, int (*f)(int x),
                             am_matrix_i *res) {
  if (res == NULL)
    res = am_matrix_i_new(m->rows, m->cols);

  for (size_t i = 0; i < m->rows * m->cols; i++)
    res->data[i] = f(m->data[i]);

  return res;
}
