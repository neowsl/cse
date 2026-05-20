/*
 * CSE 351 Lab 4 (Caches and Cache-Friendly Code)
 * Part 1 - Inferring Mystery Cache Geometries
 *
 * Name(s):  Neal Wang
 * NetID(s): nealwang
 *
 * NOTES:
 * 1. When using access_cache() you do not need to provide a "real" memory
 * addresses. You can use any convenient integer value as a memory address,
 * you should not be able to cause a segmentation fault by providing a memory
 * address out of your programs address space as the argument to access_cache.
 *
 * 2. Do NOT change the provided main function, especially the print statement.
 * If you do so, the autograder may fail to grade your code even if it produces
 * the correct result.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "support/mystery-cache.h"

/* Returns the size (in B) of each block in the cache. */
int get_block_size(void) {
        // reset cache
        flush_cache();

        access_cache(0);

        // since block size is guaranteed to be a power of 2, we can double the
        // address each time
        for (addr_t possible_size = 1; TRUE; possible_size <<= 1) {
                // keep probing until we get a cache miss, meaning we needed to
                // pull a new block into the cache
                if (!access_cache(possible_size))
                        return possible_size;
        }
}

/* Returns the size (in B) of the cache. */
int get_cache_size(int block_size) {
        // since cache size is guaranteed to be a power of 2, we can double the
        // number of blocks we check each time
        for (int possible_blocks = 1; TRUE; possible_blocks <<= 1) {
                flush_cache();

                // refill the cache to avoid LRU infinite loop
                for (int block_num = 0; block_num < possible_blocks;
                     ++block_num) {
                        // cast to `addr_t` in case there's overflow
                        access_cache((addr_t)block_num * block_size);
                }

                // block #0 got evicted = found the cache limit!
                // (except we went one step too far so divide by 2)
                if (!access_cache(0))
                        return (possible_blocks / 2) * block_size;
        }
}

/* Returns the associativity of the cache. */
int get_cache_assoc(int cache_size) {
        // suppose we have some assoc `possible_assoc` (311 moment)
        for (int possible_assoc = 1; possible_assoc <= cache_size;
             ++possible_assoc) {
                flush_cache();

                // if `possible_assoc` is correct, then after querying
                // `possible_assoc + 1` blocks, we expect the first block to be
                // evicted
                for (int i = 0; i <= possible_assoc; ++i) {
                        access_cache((addr_t)i * (cache_size / possible_assoc));
                }
                if (!access_cache(0))
                        return possible_assoc;
        }

        return -1;
}

/* Run the functions above on a given cache and print the results. */
int main(int argc, char *argv[]) {
        int size;
        int assoc;
        int block_size;
        char do_block_size, do_size, do_assoc;
        do_block_size = do_size = do_assoc = 0;
        if (argc == 1) {
                do_block_size = do_size = do_assoc = 1;
        } else {
                for (int i = 1; i < argc; i++) {
                        if (strcmp(argv[i], "block_size") == 0) {
                                do_block_size = 1;
                                continue;
                        }
                        if (strcmp(argv[i], "size") == 0) {
                                do_size = 1;
                                continue;
                        }
                        if (strcmp(argv[i], "assoc") == 0) {
                                do_assoc = 1;
                        }
                }
        }

        if (!do_block_size && !do_size && !do_assoc) {
                printf("No function requested!\n");
                printf("Usage: ./cache-test\n");
                printf("Usage: ./cache-test {block_size/size/assoc}\n");
                printf("\tyou may specify multiple functions\n");
                return EXIT_FAILURE;
        }

        cache_init(0, 0);

        block_size = size = assoc = -1;
        if (do_block_size) {
                block_size = get_block_size();
                printf("Cache block size: %d bytes\n", block_size);
        }
        if (do_size) {
                if (block_size == -1)
                        block_size = get_block_size();
                size = get_cache_size(block_size);
                printf("Cache size: %d bytes\n", size);
        }
        if (do_assoc) {
                if (block_size == -1)
                        block_size = get_block_size();
                if (size == -1)
                        size = get_cache_size(block_size);
                assoc = get_cache_assoc(size);
                printf("Cache associativity: %d\n", assoc);
        }
}
