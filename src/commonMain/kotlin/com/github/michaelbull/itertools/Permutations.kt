package com.github.michaelbull.itertools

public val EmptyPermutation: Sequence<List<Nothing>> = sequenceOf(emptyList())

/**
 * Returns a [Sequence] that yields [k]-sized [List] permutations of elements from this list.
 *
 * Unlike [distinctPermutations], elements are treated as unique based on their position, not their value — if this
 * list contains duplicate values, the output may contain duplicate permutations.
 *
 * Unlike [permutationsWithReplacement], each element may appear at most once per permutation.
 *
 * The permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .permutations(k = 2)
 *     .toList()
 *     // [[A, B], [A, C], [A, D], [B, A], [B, C], [B, D], [C, A], [C, B], [C, D], [D, A], [D, B], [D, C]]
 *
 * listOf(1, 2, 3)
 *     .permutations(k = 0)
 *     .toList()
 *     // [[]]
 *
 * emptyList<Int>()
 *     .permutations()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/permutations](https://github.com/clojure/math.combinatorics#permutations)
 * - Julia [Combinatorics.permutations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.permutations)
 * - Python [itertools.permutations](https://docs.python.org/3/library/itertools.html#itertools.permutations)
 * - Ruby [Array#permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-permutation)
 * - Rust [Itertools::permutations](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.permutations)
 * - Scala [SeqOps.permutations](https://www.scala-lang.org/api/current/scala/collection/SeqOps.html#permutations:Iterator[C])
 * - Swift [permutations(ofCount:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Permutations.md)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public fun <T> List<T>.permutations(k: Int = size): Sequence<List<T>> {
    return if (k == 0) {
        EmptyPermutation
    } else {
        permutations(
            k = k,
            permutation = ::permutation,
        )
    }
}

/**
 * Returns a [Sequence] that yields [Pair] permutations from this list.
 *
 * Elements are treated as unique based on their position, not their value. The permutation tuples are emitted in
 * lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .pairPermutations()
 *     .toList()
 *     // [(A, B), (A, C), (A, D), (B, A), (B, C), (B, D), (C, A), (C, B), (C, D), (D, A), (D, B), (D, C)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/permutations](https://github.com/clojure/math.combinatorics#permutations)
 * - Julia [Combinatorics.permutations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.permutations)
 * - Python [itertools.permutations](https://docs.python.org/3/library/itertools.html#itertools.permutations)
 * - Ruby [Array#permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-permutation)
 * - Rust [Itertools::permutations](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.permutations)
 * - Scala [SeqOps.permutations](https://www.scala-lang.org/api/current/scala/collection/SeqOps.html#permutations:Iterator[C])
 * - Swift [permutations(ofCount:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Permutations.md)
 */
public fun <T> List<T>.pairPermutations(): Sequence<Pair<T, T>> {
    return permutations(
        k = 2,
        permutation = ::pairPermutation,
    )
}

/**
 * Returns a [Sequence] that yields [Triple] permutations from this list.
 *
 * Elements are treated as unique based on their position, not their value. The permutation tuples are emitted in
 * lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .triplePermutations()
 *     .toList()
 *     // [(A, B, C), (A, B, D), (A, C, B), (A, C, D), (A, D, B), (A, D, C), (B, A, C), (B, A, D), (B, C, A), (B, C, D), (B, D, A), (B, D, C), (C, A, B), (C, A, D), (C, B, A), (C, B, D), (C, D, A), (C, D, B), (D, A, B), (D, A, C), (D, B, A), (D, B, C), (D, C, A), (D, C, B)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/permutations](https://github.com/clojure/math.combinatorics#permutations)
 * - Julia [Combinatorics.permutations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.permutations)
 * - Python [itertools.permutations](https://docs.python.org/3/library/itertools.html#itertools.permutations)
 * - Ruby [Array#permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-permutation)
 * - Rust [Itertools::permutations](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.permutations)
 * - Scala [SeqOps.permutations](https://www.scala-lang.org/api/current/scala/collection/SeqOps.html#permutations:Iterator[C])
 * - Swift [permutations(ofCount:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Permutations.md)
 */
public fun <T> List<T>.triplePermutations(): Sequence<Triple<T, T, T>> {
    return permutations(
        k = 3,
        permutation = ::triplePermutation,
    )
}

/**
 * Returns a [k]-sized [List] permutation from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [k] is not positive.
 */
public fun <T> List<T>.permutation(indices: IntArray, k: Int): List<T> {
    require(k > 0) { "k must be positive, but was $k" }

    return List(k) { index ->
        get(indices[index])
    }
}

/**
 * Returns a [Pair] permutation from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [k] is not `2`.
 */
public fun <T> List<T>.pairPermutation(indices: IntArray, k: Int): Pair<T, T> {
    require(k == 2) { "k must be 2, but was $k" }

    val (first, second) = indices

    return Pair(
        first = get(first),
        second = get(second),
    )
}

/**
 * Returns a [Triple] permutation from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [k] is not `3`.
 */
public fun <T> List<T>.triplePermutation(indices: IntArray, k: Int): Triple<T, T, T> {
    require(k == 3) { "k must be 3, but was $k" }

    val (first, second, third) = indices

    return Triple(
        first = get(first),
        second = get(second),
        third = get(third),
    )
}

public typealias PermutationTransform<V> = (indices: IntArray, k: Int) -> V

/**
 * Returns a [Sequence] that yields [k]-sized permutations of elements from this list, using the provided [permutation]
 * function to transform each permutation's [indices] into [V].
 *
 * Elements are treated as unique based on their position, not their value. The permutation tuples are emitted in
 * lexicographic order according to the order of this list.
 *
 * - Clojure [clojure.math.combinatorics/permutations](https://github.com/clojure/math.combinatorics#permutations)
 * - Julia [Combinatorics.permutations](https://juliamath.github.io/Combinatorics.jl/stable/#Combinatorics.permutations)
 * - Python [itertools.permutations](https://docs.python.org/3/library/itertools.html#itertools.permutations)
 * - Ruby [Array#permutation](https://ruby-doc.org/3.3.0/Array.html#method-i-permutation)
 * - Rust [Itertools::permutations](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.permutations)
 * - Scala [SeqOps.permutations](https://www.scala-lang.org/api/current/scala/collection/SeqOps.html#permutations:Iterator[C])
 * - Swift [permutations(ofCount:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Permutations.md)
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public inline fun <T, V> List<T>.permutations(
    k: Int = size,
    crossinline permutation: PermutationTransform<V>,
): Sequence<V> {
    require(k >= 0) { "k must be non-negative, but was $k" }

    return if (size < k) {
        emptySequence()
    } else {
        sequence {
            val indices = IntArray(size) { it }
            val cycles = IntArray(k) { size - it }

            yield(permutation(indices, k))

            while (advancePermutation(indices, k - 1, cycles)) {
                yield(permutation(indices, k))
            }
        }
    }
}

@PublishedApi
internal tailrec fun <T> List<T>.advancePermutation(indices: IntArray, index: Int, cycles: IntArray): Boolean {
    if (index < 0) return false

    return if (exhaustedPermutation(cycles, index)) {
        resetPermutationCycle(indices, index, cycles)
        advancePermutation(indices, index - 1, cycles)
    } else {
        cycles[index]--
        indices.swapPermutationAt(index, size - cycles[index])
        true
    }
}

@PublishedApi
internal fun exhaustedPermutation(cycles: IntArray, index: Int): Boolean {
    return cycles[index] == 1
}

@PublishedApi
internal fun <T> List<T>.resetPermutationCycle(indices: IntArray, fromIndex: Int, cycles: IntArray) {
    val current = indices[fromIndex]
    val remaining = size - fromIndex

    cycles[fromIndex] = remaining

    indices.shiftPermutationLeft(fromIndex)
    indices[lastIndex] = current
}

@PublishedApi
internal fun IntArray.shiftPermutationLeft(fromIndex: Int) {
    for (index in fromIndex..<lastIndex) {
        this[index] = this[index + 1]
    }
}

@PublishedApi
internal fun IntArray.swapPermutationAt(a: Int, b: Int) {
    this[a] = this[b].also {
        this[b] = this[a]
    }
}
