/*
 * CSE 351 Lab 4 (Caches and Cache-Friendly Code)
 * Part 2 - Optimizing Matrix Transpose
 *
 * Name(s):  Neal Wang
 * NetID(s): nealwang
 *
 * Each transpose function must have a prototype of the form:
 * void trans(int M, int N, int A[M][N], int B[N][M]);
 * and compute B = A^T.
 *
 * A transpose function is evaluated by counting the number of misses
 * on a 1 KiB direct mapped cache with a block size of 32 bytes.
 */

#include "support/cachelab.h"
#include <stdio.h>

#define BLOCK_SIZE(size) (size == 32 ? 8 : 4)

int is_transpose(int M, int N, int A[M][N], int B[N][M]);

/*
 * transpose_submit - This is the transpose function that you will be graded
 *     on. Do not change the description string "Transpose submission", as the
 *     driver searches for that string to identify the transpose function to be
 *     graded.
 */
char transpose_submit_desc[] = "Transpose submission";
void transpose_submit(int M, int N, int A[M][N], int B[N][M]) {
        // although we technically don't need 8 elements for `BLOCK_SIZE = 64`,
        // this helps satisfy the variable rules checker
        int i, j, k, l, tmp[8];

        for (i = 0; i < M; i += BLOCK_SIZE(M)) {
                for (j = 0; j < N; j += BLOCK_SIZE(M)) {
                        for (k = i; k < i + BLOCK_SIZE(M); ++k) {
                                // first load an entire row of `BLOCK_SIZE`
                                // (1 cache miss)
                                for (l = 0; l < BLOCK_SIZE(M); ++l) {
                                        tmp[l] = A[k][j + l];
                                }
                                // then write into `B`
                                // since we're blocking by `BLOCK_SIZE`, we
                                // should only expect 1 cache miss after each
                                // first iteration of `k` due to diagonal
                                // problem
                                for (l = 0; l < BLOCK_SIZE(M); ++l) {
                                        B[j + l][k] = tmp[l];
                                }
                        }
                }
        }
}

// You can define additional transpose functions below. We've defined a simple
// one below to help you get started.

/*
 * trans - A simple baseline transpose function, not optimized for the cache.
 */
char trans_desc[] = "Simple row-wise scan transpose";
void trans(int M, int N, int A[M][N], int B[N][M]) {
        int i, j, tmp;

        for (i = 0; i < M; i++) {
                for (j = 0; j < N; j++) {
                        tmp = A[i][j];
                        B[j][i] = tmp;
                }
        }
}

/*
 * registerFunctions - This function registers your transpose
 *     functions with the driver.  At runtime, the driver will
 *     evaluate each of the registered functions and summarize their
 *     performance. This is a handy way to experiment with different
 *     transpose strategies.
 */
void registerFunctions() {
        /* Register your solution function */
        registerTransFunction(transpose_submit, transpose_submit_desc);

        /* Register any additional transpose functions */
        registerTransFunction(trans, trans_desc);
}

/*
 * is_transpose - This helper function checks if B is the transpose of
 *     A. You can check the correctness of your transpose by calling
 *     it before returning from the transpose function.
 */
int is_transpose(int M, int N, int A[M][N], int B[N][M]) {
        int i, j;

        for (i = 0; i < M; i++) {
                for (j = 0; j < N; ++j) {
                        if (A[i][j] != B[j][i]) {
                                return 0;
                        }
                }
        }
        return 1;
}
