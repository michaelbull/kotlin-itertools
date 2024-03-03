package com.github.michaelbull.itertools

/**
 * Returns a sequence that yields [length]-sized combinations from this list.
 *
 * The combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .combinations(length = 2)
 *     .toList()
 *     // [[A, B], [A, C], [A, D], [B, C], [B, D], [C, D]]
 *
 * (0..3).toList()
 *     .combinations(length = 3)
 *     .toList()
 *     // [[0, 1, 2], [0, 1, 3], [0, 2, 3], [1, 2, 3]]
 * ```
 *
 * @throws IllegalArgumentException if [length] is negative.
 */
public fun <T> List<T>.combinations(length: Int = size): Sequence<List<T>> {
    return combinations(
        length = length,
        combination = ::combination,
    )
}

/**
 * Returns a sequence that yields [Pair] combinations from this list.
 *
 * The combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABDC".toList()
 *     .pairCombinations()
 *     .toList()
 *     // [(A, B), (A, D), (A, C), (B, D), (B, C), (D, C)]
 *
 * (0..3).toList()
 *     .pairCombinations()
 *     .toList()
 *     // [(0, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 3)]
 * ```
 */
public fun <T> List<T>.pairCombinations(): Sequence<Pair<T, T>> {
    return combinations(
        length = 2,
        combination = ::pairCombination,
    )
}

/**
 * Returns a sequence that yields [Triple] combinations from this list.
 *
 * The combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABDC".toList()
 *     .tripleCombinations()
 *     .toList()
 *     // [(A, B, C), (A, B, D), (A, C, D), (B, C, D)]
 *
 * (0..3).toList()
 *     .tripleCombinations()
 *     .toList()
 *     // [(0, 1, 2), (0, 1, 3), (0, 2, 3), (1, 2, 3)]
 * ```
 */
public fun <T> List<T>.tripleCombinations(): Sequence<Triple<T, T, T>> {
    return combinations(
        length = 3,
        combination = ::tripleCombination,
    )
}

private fun <T> List<T>.combination(indices: IntArray, count: Int): List<T> {
    require(count > 0)

    return indices.map(this::get)
}

private fun <T> List<T>.pairCombination(indices: IntArray, count: Int): Pair<T, T> {
    require(count == 2)

    return Pair(
        first = get(indices[0]),
        second = get(indices[1]),
    )
}

private fun <T> List<T>.tripleCombination(indices: IntArray, count: Int): Triple<T, T, T> {
    require(count == 3)

    return Triple(
        first = get(indices[0]),
        second = get(indices[1]),
        third = get(indices[2]),
    )
}

private inline fun <T, V> List<T>.combinations(
    length: Int = size,
    crossinline combination: (indices: IntArray, count: Int) -> V,
): Sequence<V> = sequence {

    require(length >= 0) { "length must be non-negative, but was $length" }

    if (length in 1..size) {
        val indices = IntArray(length) { it }
        var searching = true

        yield(combination(indices, length))

        while (searching) {
            var found = false
            var index = length - 1

            while (index >= 0 && !found) {
                if (indices[index] == index + size - length) {
                    index--
                } else {
                    indices[index]++

                    for (j in index + 1 until length) {
                        indices[j] = indices[j - 1] + 1
                    }

                    yield(combination(indices, length))
                    found = true
                }
            }

            if (!found) {
                searching = false
            }
        }
    }
}
