#pragma once

#include <stdlib.h>

typedef struct amHash amHash;
typedef int (*cache_f)(void* key);
typedef void (*free_f)(void* p);

typedef enum {
  AM_HASH_KEXISTS,
  AM_HASH_KNOTEXISTS,
} amHashResult;

amHash* am_hash_new(cache_f cache, size_t key_size, size_t data_size);
void am_hash_set_key_

int am_hash_add(amHash* h, void* key, void* data);
void* am_hash_get(amHash* h, void* key);
amHashResult am_hash_remove(amHash* h, void* key);

void am_hash_free(amHash* hash);
