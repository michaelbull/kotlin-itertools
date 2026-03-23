package com.github.michaelbull.itertools

/**
 * Returns a [Sequence] that yields [k]-sized [List] derangements of elements from this list.
 *
 * A derangement is a permutation where no element appears in its original position. Elements are treated as unique
 * based on their position, not their value. The derangement tuples are emitted in lexicographic order according to the
 * order of this list.
 *
 * ```
 * "ABC".toList()
 *     .derangements()
 *     .toList()
 *     // [[B, C, A], [C, A, B]]
 *
 * "ABCD".toList()
 *     .derangements(k = 2)
 *     .toList()
 *     // [[B, A], [B, C], [B, D], [C, A], [C, D], [D, A], [D, C]]
 *
 * emptyList<Int>()
 *     .derangements()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - Haskell [Math.Combinat.Permutations.derangements](https://hackage.haskell.org/package/combinat/docs/Math-Combinat-Permutations.html#v:derangements)
 * - Python [derangements (itertools recipe)](https://docs.python.org/3/library/itertools.html#itertools-recipes)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public fun <T> List<T>.derangements(k: Int = size): Sequence<List<T>> {
    require(k >= 0) { "k must be non-negative, but was $k" }

    return if (k == 0) {
        EmptyPermutation
    } else {
        permutations(k).filter { permutation ->
            permutation.indices.all { i -> permutation[i] != this[i] }
        }
    }
}
