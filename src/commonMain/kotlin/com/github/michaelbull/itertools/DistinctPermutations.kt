package com.github.michaelbull.itertools

/**
 * Returns a [Sequence] that yields distinct [List] permutations of elements from this list.
 *
 * Unlike [permutations], elements are treated as unique based on their value, not their position. Equal elements are
 * interchangeable, and the resulting permutations contain no duplicates. The elements must be [Comparable], as they are
 * sorted to establish lexicographic order. The permutations are emitted in lexicographic order.
 *
 * Unlike [permutationsWithReplacement], each element may appear at most as many times as it occurs in this list.
 *
 * The number of distinct permutations is `n! / (k1! · k2! · ... · km!)`, where `k1, k2, ..., km` are the frequencies
 * of each distinct element.
 *
 * ```
 * "AAB".toList()
 *     .distinctPermutations()
 *     .toList()
 *     // [[A, A, B], [A, B, A], [B, A, A]]
 *
 * "ABC".toList()
 *     .distinctPermutations()
 *     .toList()
 *     // [[A, B, C], [A, C, B], [B, A, C], [B, C, A], [C, A, B], [C, B, A]]
 *
 * emptyList<Int>()
 *     .distinctPermutations()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - C++ [std::next_permutation](https://en.cppreference.com/w/cpp/algorithm/next_permutation)
 * - Clojure [clojure.math.combinatorics/permutations](https://github.com/clojure/math.combinatorics#permutations) (auto-deduplicates)
 * - Julia [Combinatorics.multiset_permutations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.multiset_permutations)
 * - Python [more_itertools.distinct_permutations](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.distinct_permutations)
 * - Scala [SeqOps.permutations](https://www.scala-lang.org/api/current/scala/collection/SeqOps.html#permutations:Iterator[C]) (auto-deduplicates)
 */
public fun <T : Comparable<T>> List<T>.distinctPermutations(): Sequence<List<T>> {
    return if (isEmpty()) {
        EmptyPermutation
    } else {
        sequence {
            val elements = sorted().toMutableList()

            yield(elements.toList())

            while (advanceDistinctPermutation(elements)) {
                yield(elements.toList())
            }
        }
    }
}

/**
 * Advances the [elements] to the next lexicographically greater permutation.
 *
 * Returns `true` if a next permutation was found, `false` if the elements are in their last (descending) order.
 *
 * Uses [Algorithm L](https://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order) (Knuth):
 * 1. Find the largest index `i` such that `elements[i] < elements[i + 1]`.
 * 2. Find the largest index `j > i` such that `elements[i] < elements[j]`.
 * 3. Swap `elements[i]` and `elements[j]`.
 * 4. Reverse the suffix starting at `elements[i + 1]`.
 */
private fun <T : Comparable<T>> advanceDistinctPermutation(elements: MutableList<T>): Boolean {
    val i = (elements.size - 2 downTo 0).firstOrNull { elements[it] < elements[it + 1] }

    return if (i == null) {
        false
    } else {
        val j = (elements.lastIndex downTo i + 1).first { elements[i] < elements[it] }

        val temp = elements[i]
        elements[i] = elements[j]
        elements[j] = temp

        elements.subList(i + 1, elements.size).reverse()

        true
    }
}
