package com.github.michaelbull.itertools

/**
 * Returns a [Sequence] that yields [k]-sized [List] combinations of elements from this list, allowing individual
 * elements to be repeated.
 *
 * Like [combinations], elements are treated as unique based on their position, not their value, and the combination
 * tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [combinations], elements may appear more than once in each combination. Because elements can be reused, [k]
 * may exceed the size of this list and still produce results.
 *
 * ```
 * "ABC".toList()
 *     .combinationsWithReplacement(k = 2)
 *     .toList()
 *     // [[A, A], [A, B], [A, C], [B, B], [B, C], [C, C]]
 *
 * listOf(1, 2, 3)
 *     .combinationsWithReplacement(k = 0)
 *     .toList()
 *     // [[]]
 *
 * emptyList<Int>()
 *     .combinationsWithReplacement()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - Julia [Combinatorics.with_replacement_combinations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.with_replacement_combinations)
 * - Python [itertools.combinations_with_replacement](https://docs.python.org/3/library/itertools.html#itertools.combinations_with_replacement)
 * - Ruby [Array#repeated_combination](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_combination)
 * - Rust [Itertools::combinations_with_replacement](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.combinations_with_replacement)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public fun <T> List<T>.combinationsWithReplacement(k: Int = size): Sequence<List<T>> {
    return if (k == 0) {
        EmptyCombination
    } else {
        combinationsWithReplacement(
            k = k,
            combination = ::combination,
        )
    }
}

/**
 * Returns a [Sequence] that yields [Pair] combinations from this list, allowing individual elements to be repeated.
 *
 * Like [pairCombinations], elements are treated as unique based on their position, not their value, and the
 * combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [pairCombinations], elements may appear more than once in each combination.
 *
 * ```
 * "ABC".toList()
 *     .pairCombinationsWithReplacement()
 *     .toList()
 *     // [(A, A), (A, B), (A, C), (B, B), (B, C), (C, C)]
 * ```
 *
 * - Julia [Combinatorics.with_replacement_combinations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.with_replacement_combinations)
 * - Python [itertools.combinations_with_replacement](https://docs.python.org/3/library/itertools.html#itertools.combinations_with_replacement)
 * - Ruby [Array#repeated_combination](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_combination)
 * - Rust [Itertools::combinations_with_replacement](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.combinations_with_replacement)
 */
public fun <T> List<T>.pairCombinationsWithReplacement(): Sequence<Pair<T, T>> {
    return combinationsWithReplacement(
        k = 2,
        combination = ::pairCombination,
    )
}

/**
 * Returns a [Sequence] that yields [Triple] combinations from this list, allowing individual elements to be repeated.
 *
 * Like [tripleCombinations], elements are treated as unique based on their position, not their value, and the
 * combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [tripleCombinations], elements may appear more than once in each combination.
 *
 * ```
 * "ABC".toList()
 *     .tripleCombinationsWithReplacement()
 *     .toList()
 *     // [(A, A, A), (A, A, B), (A, A, C), (A, B, B), (A, B, C), (A, C, C), (B, B, B), (B, B, C), (B, C, C), (C, C, C)]
 * ```
 *
 * - Julia [Combinatorics.with_replacement_combinations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.with_replacement_combinations)
 * - Python [itertools.combinations_with_replacement](https://docs.python.org/3/library/itertools.html#itertools.combinations_with_replacement)
 * - Ruby [Array#repeated_combination](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_combination)
 * - Rust [Itertools::combinations_with_replacement](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.combinations_with_replacement)
 */
public fun <T> List<T>.tripleCombinationsWithReplacement(): Sequence<Triple<T, T, T>> {
    return combinationsWithReplacement(
        k = 3,
        combination = ::tripleCombination,
    )
}

/**
 * Returns a [Sequence] that yields [k]-sized combinations of elements from this list, allowing individual elements
 * to be repeated, using the provided [combination] function to transform each combination's [indices] into [V].
 *
 * Like [combinations], elements are treated as unique based on their position, not their value, and the combination
 * tuples are emitted in lexicographic order according to the order of this list.
 *
 * Unlike [combinations], elements may appear more than once in each combination. Because elements can be reused, [k]
 * may exceed the size of this list and still produce results.
 *
 * - Julia [Combinatorics.with_replacement_combinations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.with_replacement_combinations)
 * - Python [itertools.combinations_with_replacement](https://docs.python.org/3/library/itertools.html#itertools.combinations_with_replacement)
 * - Ruby [Array#repeated_combination](https://ruby-doc.org/3.3.0/Array.html#method-i-repeated_combination)
 * - Rust [Itertools::combinations_with_replacement](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.combinations_with_replacement)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public inline fun <T, V> List<T>.combinationsWithReplacement(
    k: Int = size,
    crossinline combination: CombinationTransform<V>,
): Sequence<V> {
    require(k >= 0) { "k must be non-negative, but was $k" }

    return if (isEmpty() && k > 0) {
        emptySequence()
    } else {
        sequence {
            val indices = IntArray(k) { 0 }

            yield(combination(indices, k))

            while (advanceWithReplacement(indices, k - 1)) {
                yield(combination(indices, k))
            }
        }
    }
}

@PublishedApi
internal tailrec fun <T> List<T>.advanceWithReplacement(indices: IntArray, index: Int): Boolean {
    if (index < 0) return false

    return if (exhaustedWithReplacement(indices, index)) {
        advanceWithReplacement(indices, index - 1)
    } else {
        indices[index]++
        indices.resetTailWithReplacement(index + 1, indices[index])
        true
    }
}

@PublishedApi
internal fun <T> List<T>.exhaustedWithReplacement(indices: IntArray, index: Int): Boolean {
    return indices[index] == lastIndex
}

@PublishedApi
internal fun IntArray.resetTailWithReplacement(fromIndex: Int, value: Int) {
    for (i in fromIndex..lastIndex) {
        this[i] = value
    }
}
