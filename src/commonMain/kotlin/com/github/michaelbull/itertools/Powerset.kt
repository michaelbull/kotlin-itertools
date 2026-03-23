package com.github.michaelbull.itertools

/**
 * Returns a [Sequence] that yields all subsets of this list, from the empty set to the full set.
 *
 * Subsets are emitted in order of increasing size. Within each size, subsets are emitted in lexicographic order
 * according to the order of this list.
 *
 * ```
 * "ABC".toList()
 *     .powerset()
 *     .toList()
 *     // [[], [A], [B], [C], [A, B], [A, C], [B, C], [A, B, C]]
 *
 * emptyList<Int>()
 *     .powerset()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - Python [powerset (itertools recipe)](https://docs.python.org/3/library/itertools.html#itertools-recipes)
 * - Rust [Itertools::powerset](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.powerset)
 */
public fun <T> List<T>.powerset(): Sequence<List<T>> {
    return if (isEmpty()) {
        EmptyCombination
    } else {
        sequence {
            yield(emptyList())

            for (k in 1..size) {
                val indices = IntArray(k) { it }

                yield(combination(indices, k))

                while (advance(indices, k - 1, k)) {
                    yield(combination(indices, k))
                }
            }
        }
    }
}
