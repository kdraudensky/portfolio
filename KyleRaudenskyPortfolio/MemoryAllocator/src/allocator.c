#include "allocator.h"
#include "dlist.h"
#include "dnode.h"
#include <stdlib.h>
#include <stdio.h>

struct dlist *free_list;
struct dlist *allocated_list;

int allocator_init(size_t size) {
	free_list = dlist_create();
	allocated_list = dlist_create();

	void *ptr = malloc(sizeof(size));
	if (ptr == NULL) {
		return -1;
	}
	
	dlist_add_back(free_list, ptr, size);
	return 0;
}

void *allocate(size_t size) {

	int allocation_type = 0;
	void *addr;
	switch(allocation_type) {
		case 0:
			addr = best_fit(size);
			break;
		case 1:
			addr = worst_fit(size);
			break;
		case 2:
			addr = first_fit(size);
			break;
		
	}

	if (addr == NULL) {
		return NULL;
	}

	void *cur_addr;
	for(cur_addr = dlist_iter_begin(free_list); cur_addr != NULL; cur_addr = dlist_iter_next(free_list)) {
		if(addr == cur_addr) {
			free_list->iter->size -= size;
			free_list->iter->data += size;
		}
	}

	dlist_add_back(allocated_list, addr, size);
	return addr;

}

void *best_fit(size_t size) {
	void *cur;
	void *best;
	size_t small = 0;

	for (cur = dlist_iter_begin(free_list); cur != NULL; cur = dlist_iter_next(free_list)) {
		if (free_list -> iter -> size > size) {
			if (small == 0 || free_list -> iter -> size < small) {
				best = cur;
				small = free_list -> iter -> size;
			}
		}
	}

	if (small == 0) {
		return NULL;
	}
	return best;
}

void *first_fit(size_t size) {
	void *cur;
	for (cur = dlist_iter_begin(free_list); cur != NULL; cur = dlist_iter_next(free_list)) {
		if (size > free_list -> iter -> size) {
			return cur;
		}
	}
	return NULL;
}

void *worst_fit(size_t size) {
	void *cur;
	void *worst;
	size_t big = 0;

	for (cur = dlist_iter_begin(free_list); cur != NULL; cur = dlist_iter_next(free_list)) {
		if (size < free_list -> iter -> size) {
			if (big < free_list -> iter -> size) {
				big = free_list -> iter -> size;
				worst = cur;
			}
		}
	}

	if (big == 0) {
		return NULL;
	}
	return worst;
}

int deallocate(void *ptr) {
	void *cur;
	int cor_addr = 0;

	for (cur = dlist_iter_begin(allocated_list); cur != NULL; cur = dlist_iter_next(allocated_list)) {
		if (ptr == cur) {
			cor_addr = 1;
			break;
		}
	}

	if (cor_addr == 0) {
		return -1;
	}
	else {
		size_t node_size = allocated_list->iter->size;
		dlist_add_back(free_list, cur, node_size);
		cur = dlist_find_remove(allocated_list, ptr);
	}

	return 0;
}
