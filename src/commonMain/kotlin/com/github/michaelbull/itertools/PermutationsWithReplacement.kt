package com.github.michaelbull.itertools

/**
 * Returns a [Sequence] that yields [k]-sized [List] permutations of elements from this list, allowing individual
 * elements to be repeated.
 *
 * Like [permutations], elements are treated as unique based on their position, not their value, and the permutation
 * tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [permutations], elements may appear more than once in each permutation. Because elements can be reused, [k]
 * may exceed the size of this list and still produce results.
 *
 * ```
 * "ABCD".toList()
 *     .permutationsWithReplacement(k = 2)
 *     .toList()
 *     // [[A, A], [A, B], [A, C], [A, D], [B, A], [B, B], [B, C], [B, D], [C, A], [C, B], [C, C], [C, D], [D, A], [D, B], [D, C], [D, D]]
 *
 * listOf(1, 2, 3)
 *     .permutationsWithReplacement(k = 0)
 *     .toList()
 *     // [[]]
 *
 * emptyList<Int>()
 *     .permutationsWithReplacement()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/selections](https://github.com/clojure/math.combinatorics#selections)
 * - Ruby [Array#repeated_permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_permutation)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public fun <T> List<T>.permutationsWithReplacement(k: Int = size): Sequence<List<T>> {
    return if (k == 0) {
        EmptyPermutation
    } else {
        permutationsWithReplacement(
            k = k,
            permutation = ::permutation,
        )
    }
}

/**
 * Returns a [Sequence] that yields [Pair] permutations from this list, allowing individual elements to be repeated.
 *
 * Like [pairPermutations], elements are treated as unique based on their position, not their value, and the
 * permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [pairPermutations], elements may appear more than once in each permutation.
 *
 * ```
 * "ABC".toList()
 *     .pairPermutationsWithReplacement()
 *     .toList()
 *     // [(A, A), (A, B), (A, C), (B, A), (B, B), (B, C), (C, A), (C, B), (C, C)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/selections](https://github.com/clojure/math.combinatorics#selections)
 * - Ruby [Array#repeated_permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_permutation)
 */
public fun <T> List<T>.pairPermutationsWithReplacement(): Sequence<Pair<T, T>> {
    return permutationsWithReplacement(
        k = 2,
        permutation = ::pairPermutation,
    )
}

/**
 * Returns a [Sequence] that yields [Triple] permutations from this list, allowing individual elements to be repeated.
 *
 * Like [triplePermutations], elements are treated as unique based on their position, not their value, and the
 * permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [triplePermutations], elements may appear more than once in each permutation.
 *
 * ```
 * "ABC".toList()
 *     .triplePermutationsWithReplacement()
 *     .toList()
 *     // [(A, A, A), (A, A, B), (A, A, C), (A, B, A), (A, B, B), (A, B, C), (A, C, A), (A, C, B), (A, C, C), (B, A, A), (B, A, B), (B, A, C), (B, B, A), (B, B, B), (B, B, C), (B, C, A), (B, C, B), (B, C, C), (C, A, A), (C, A, B), (C, A, C), (C, B, A), (C, B, B), (C, B, C), (C, C, A), (C, C, B), (C, C, C)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/selections](https://github.com/clojure/math.combinatorics#selections)
 * - Ruby [Array#repeated_permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_permutation)
 */
public fun <T> List<T>.triplePermutationsWithReplacement(): Sequence<Triple<T, T, T>> {
    return permutationsWithReplacement(
        k = 3,
        permutation = ::triplePermutation,
    )
}

/**
 * Returns a [Sequence] that yields [k]-sized permutations of elements from this list, allowing individual elements
 * to be repeated, using the provided [permutation] function to transform each permutation's [indices] into [V].
 *
 * Like [permutations], elements are treated as unique based on their position, not their value, and the permutation
 * tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [permutations], elements may appear more than once in each permutation. Because elements can be reused, [k]
 * may exceed the size of this list and still produce results.
 *
 * - Clojure [clojure.math.combinatorics/selections](https://github.com/clojure/math.combinatorics#selections)
 * - Ruby [Array#repeated_permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_permutation)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public inline fun <T, V> List<T>.permutationsWithReplacement(
    k: Int = size,
    crossinline permutation: PermutationTransform<V>,
): Sequence<V> {
    require(k >= 0) { "k must be non-negative, but was $k" }

    return if (isEmpty() && k > 0) {
        emptySequence()
    } else {
        sequence {
            val indices = IntArray(k) { 0 }

            yield(permutation(indices, k))

            while (advancePermutationWithReplacement(indices, k - 1)) {
                yield(permutation(indices, k))
            }
        }
    }
}

@PublishedApi
internal tailrec fun <T> List<T>.advancePermutationWithReplacement(indices: IntArray, index: Int): Boolean {
    if (index < 0) return false

    return if (indices[index] == lastIndex) {
        indices[index] = 0
        advancePermutationWithReplacement(indices, index - 1)
    } else {
        indices[index]++
        true
    }
}
