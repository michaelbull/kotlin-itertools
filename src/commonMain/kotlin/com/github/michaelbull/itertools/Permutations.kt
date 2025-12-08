package com.github.michaelbull.itertools

public val EmptyPermutation: Sequence<List<Nothing>> = sequenceOf(emptyList())

/**
 * Returns a sequence that yields [length]-sized permutations from this list.
 *
 * The permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .permutations(length = 2)
 *     .toList()
 *     // [[A, B], [A, C], [A, D], [B, A], [B, C], [B, D], [C, A], [C, B], [C, D], [D, A], [D, B], [D, C]]
 *
 * (0..2).toList()
 *     .permutations()
 *     .toList()
 *     // [[0, 1, 2], [0, 2, 1], [1, 0, 2], [1, 2, 0], [2, 0, 1], [2, 1, 0]]
 *
 * listOf(1, 2, 3)
 *     .permutations(length = 0)
 *     .toList()
 *     // [[]]
 *
 * emptyList<Int>()
 *     .permutations()
 *     .toList()
 *     // [[]]
 * ```
 *
 * @throws IllegalArgumentException if [length] is negative.
 */
public fun <T> List<T>.permutations(length: Int = size): Sequence<List<T>> {
    return if (length == 0) {
        EmptyPermutation
    } else if (size < length) {
        emptySequence()
    } else {
        permutations(
            length = length,
            permutation = ::permutation,
        )
    }
}

/**
 * Returns a sequence that yields [Pair] permutations from this list.
 *
 * The permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .pairPermutations()
 *     .toList()
 *     // [(A, B), (A, C), (A, D), (B, A), (B, C), (B, D), (C, A), (C, B), (C, D), (D, A), (D, B), (D, C)]
 *
 * (0..2).toList()
 *     .pairPermutations()
 *     .toList()
 *     // [(0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1)]
 * ```
 */
public fun <T> List<T>.pairPermutations(): Sequence<Pair<T, T>> {
    return if (size < 2) {
        emptySequence()
    } else {
        permutations(
            length = 2,
            permutation = ::pairPermutation
        )
    }
}

/**
 * Returns a sequence that yields [Triple] permutations from this list.
 *
 * The permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .triplePermutations()
 *     .toList()
 *     // [(A, B, C), (A, B, D), (A, C, B), (A, C, D), (A, D, B), (A, D, C), (B, A, C), (B, A, D), (B, C, A), (B, C, D), (B, D, A), (B, D, C), (C, A, B), (C, A, D), (C, B, A), (C, B, D), (C, D, A), (C, D, B), (D, A, B), (D, A, C), (D, B, A), (D, B, C), (D, C, A), (D, C, B)]
 *
 * (0..2).toList()
 *     .triplePermutations()
 *     .toList()
 *     // [(0, 1, 2), (0, 2, 1), (1, 0, 2), (1, 2, 0), (2, 0, 1), (2, 1, 0)]
 * ```
 */
public fun <T> List<T>.triplePermutations(): Sequence<Triple<T, T, T>> {
    return if (size < 3) {
        emptySequence()
    } else {
        permutations(
            length = 3,
            permutation = ::triplePermutation,
        )
    }
}

/**
 * Returns a [length]-sized [List] permutation from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [length] is not positive.
 */
public fun <T> List<T>.permutation(indices: IntArray, length: Int): List<T> {
    require(length > 0) { "length must be positive, but was $length" }

    return List(length) { index ->
        get(indices[index])
    }
}

/**
 * Returns a [Pair] permutation from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [length] is not `2`.
 */
public fun <T> List<T>.pairPermutation(indices: IntArray, length: Int): Pair<T, T> {
    require(length == 2) { "length must be 2, but was $length" }

    val (first, second) = indices

    return Pair(
        first = get(first),
        second = get(second),
    )
}

/**
 * Returns a [Triple] permutation from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [length] is not `3`.
 */
public fun <T> List<T>.triplePermutation(indices: IntArray, length: Int): Triple<T, T, T> {
    require(length == 3) { "length must be 3, but was $length" }

    val (first, second, third) = indices

    return Triple(
        first = get(first),
        second = get(second),
        third = get(third),
    )
}

public typealias PermutationTransform<V> = (indices: IntArray, length: Int) -> V

/**
 * Returns a sequence that yields [length]-sized permutations from this list, using the provided [permutation]
 * function to transform each permutation's [indices] into [V].
 *
 * The permutation tuples are emitted in lexicographic order according to the order of this list.
 *
 * @throws IllegalArgumentException if [length] is negative.
 */
public inline fun <T, V> List<T>.permutations(
    length: Int = size,
    crossinline permutation: PermutationTransform<V>,
): Sequence<V> {
    require(length >= 0) { "length must be non-negative, but was $length" }

    return sequence {
        val indices = IntArray(size) { it }
        val cycles = IntArray(length) { size - it }
        var searching = size > 1

        yield(permutation(indices, length))

        while (searching) {
            var found = false
            var index = length - 1

            while (index >= 0 && !found) {
                val exhausted = cycles[index] == 1

                if (exhausted) {
                    resetCycle(indices, index, cycles)
                    index--
                } else {
                    cycles[index]--
                    indices.swapAt(index, size - cycles[index])

                    yield(permutation(indices, length))
                    found = true
                }
            }

            if (!found) {
                searching = false
            }
        }
    }
}

@PublishedApi
internal fun <T> List<T>.resetCycle(indices: IntArray, index: Int, cycles: IntArray) {
    val current = indices[index]
    val remaining = size - index

    cycles[index] = remaining

    indices.shiftLeftFrom(index)
    indices[lastIndex] = current
}

@PublishedApi
internal fun IntArray.shiftLeftFrom(index: Int) {
    for (index in index..<lastIndex) {
        this[index] = this[index + 1]
    }
}

@PublishedApi
internal fun IntArray.swapAt(a: Int, b: Int) {
    this[a] = this[b].also {
        this[b] = this[a]
    }
}
