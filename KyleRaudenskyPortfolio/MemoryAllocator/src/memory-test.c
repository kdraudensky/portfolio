#include "allocator.h"
#include <stdio.h>
#include <stdlib.h>
#include "dlist.h"

void print_free_list() {
	int i = 0;
	void *n_addr;
	printf("Free list\n");
	for (n_addr = dlist_iter_begin(free_list); n_addr != NULL; n_addr = dlist_iter_next(free_list)) {
		printf("Node index = %d, address = %p, size = %zu\n", i, n_addr, free_list->iter->size);
	}
	if (i==0) {
		printf("Empty");
	}
}

void print_alloc_list() {
	int i = 0;
	void *n_addr;
	printf("Allocated list\n");
	for (n_addr = dlist_iter_begin(allocated_list); n_addr != NULL; n_addr = dlist_iter_next(allocated_list)) {
		printf("Node index = %d, address = %p, size = %zu\n", i, n_addr, allocated_list->iter->size);
	}
	if (i==0) {
		printf("Empty");
	}
}

int main(int argc, char *argv[]) {
	if (allocator_init(100) != 0) {
		printf("Allocator initialization failed.");
		return -1;
	}

	print_free_list();
	print_alloc_list();

	//allocate memory for an int
	int *num1 = allocate(4);
	*num1 = 5;
	printf("Assumed value: 5\n");
	printf("Actual value: %d\n", *num1);

	//allocate memory too big for allocator
	void *var = allocate(150);
	printf("Assumed value: NULL\n");
	printf("Actual value: %p\n", var);

	//allocate memory for a char
	char *car = allocate(1);
	*car = 'g';
	printf("Assumed value: g\n");
	printf("Actual value: %c\n", *car);


	//deallocate var
	deallocate(var);

	print_free_list();
	print_alloc_list();

	return (0);
}
