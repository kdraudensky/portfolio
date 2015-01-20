#ifndef _ALLOCATOR_H_
#define _ALLOCATOR_H_

#include <stdlib.h>
#include <stdio.h>

extern struct dlist *free_list;
extern struct dlist *allocated_list;

int allocator_init(size_t size);

void *allocate(size_t size);

int deallocate(void *ptr);

void *best_fit(size_t size);

void *worst_fit(size_t size);

void *first_fit(size_t size);

#endif  /* _ALLOCATOR_H_ */