package com.github.michaelbull.itertools

private val EmptyPermutation = sequenceOf(emptyList<Nothing>())

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
    require(length >= 0) { "length must be non-negative, but was $length" }

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

private fun <T> List<T>.permutation(indices: IntArray, count: Int): List<T> {
    require(count > 0)

    return List(count) { index ->
        get(indices[index])
    }
}

private fun <T> List<T>.pairPermutation(indices: IntArray, count: Int): Pair<T, T> {
    require(count == 2)

    val (first, second) = indices

    return Pair(
        first = get(first),
        second = get(second),
    )
}

private fun <T> List<T>.triplePermutation(indices: IntArray, count: Int): Triple<T, T, T> {
    require(count == 3)

    val (first, second, third) = indices

    return Triple(
        first = get(first),
        second = get(second),
        third = get(third),
    )
}

private inline fun <T, V> List<T>.permutations(
    length: Int = size,
    crossinline permutation: (indices: IntArray, count: Int) -> V,
) = sequence {
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

private fun <T> List<T>.resetCycle(indices: IntArray, index: Int, cycles: IntArray) {
    val current = indices[index]
    val remaining = size - index

    cycles[index] = remaining

    indices.shiftLeftFrom(index)
    indices[lastIndex] = current
}

private fun IntArray.shiftLeftFrom(index: Int) {
    for (index in index..<lastIndex) {
        this[index] = this[index + 1]
    }
}

private fun IntArray.swapAt(a: Int, b: Int) {
    this[a] = this[b].also {
        this[b] = this[a]
    }
}
