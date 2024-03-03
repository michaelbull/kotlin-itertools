package com.github.michaelbull.itertools

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
 * ```
 *
 * @throws IllegalArgumentException if [length] is negative.
 */
public fun <T> List<T>.permutations(length: Int = size): Sequence<List<T>> {
    return permutations(
        length = length,
        permutation = ::permutation,
    )
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
    return permutations(
        length = 2,
        permutation = ::pairPermutation
    )
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
    return permutations(
        length = 3,
        permutation = ::triplePermutation,
    )
}

private fun <T> List<T>.permutation(indices: IntArray, count: Int): List<T> {
    require(count > 0)

    return List(count) { index ->
        get(indices[index])
    }
}

private fun <T> List<T>.pairPermutation(indices: IntArray, count: Int): Pair<T, T> {
    require(count == 2)

    return Pair(
        first = get(indices[0]),
        second = get(indices[1]),
    )
}

private fun <T> List<T>.triplePermutation(indices: IntArray, count: Int): Triple<T, T, T> {
    require(count == 3)

    return Triple(
        first = get(indices[0]),
        second = get(indices[1]),
        third = get(indices[2]),
    )
}

private inline fun <T, V> List<T>.permutations(
    length: Int = size,
    crossinline permutation: (indices: IntArray, count: Int) -> V,
): Sequence<V> = sequence {

    require(length >= 0) { "length must be non-negative, but was $length" }

    if (length in 1..size) {
        val indices = IntArray(size) { it }
        val cycles = IntArray(length) { size - it }
        var searching = true

        yield(permutation(indices, length))

        while (searching) {
            var found = false
            var index = length - 1

            while (index >= 0 && !found) {
                cycles[index]--

                if (cycles[index] == 0) {
                    val indexBefore = indices[index]

                    for (i in index until size - 1) {
                        indices[i] = indices[i + 1]
                    }

                    indices[size - 1] = indexBefore
                    cycles[index] = size - index
                    index--
                } else {
                    val i = size - cycles[index]

                    indices.swapByIndex(index, i)

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

private fun IntArray.swapByIndex(a: Int, b: Int) {
    this[a] = this[b].also {
        this[b] = this[a]
    }
}
