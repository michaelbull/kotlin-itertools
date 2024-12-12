package com.github.michaelbull.itertools

private val EmptyCombination = sequenceOf(emptyList<Nothing>())

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
 *
 * listOf(1, 2, 3)
 *     .combinations(length = 0)
 *     .toList()
 *     // [[]]
 *
 * emptyList<Int>()
 *     .combinations()
 *     .toList()
 *     // [[]]
 * ```
 *
 * @throws IllegalArgumentException if [length] is negative.
 */
public fun <T> List<T>.combinations(length: Int = size): Sequence<List<T>> {
    require(length >= 0) { "length must be non-negative, but was $length" }

    return if (length == 0) {
        EmptyCombination
    } else if (size < length) {
        emptySequence()
    } else {
        combinations(
            length = length,
            combination = ::combination,
        )
    }
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
    return if (size < 2) {
        emptySequence()
    } else {
        combinations(
            length = 2,
            combination = ::pairCombination,
        )
    }
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
    return if (size < 3) {
        emptySequence()
    } else {
        combinations(
            length = 3,
            combination = ::tripleCombination,
        )
    }
}

private fun <T> List<T>.combination(indices: IntArray, count: Int): List<T> {
    require(count > 0)

    return List(count) { index ->
        get(indices[index])
    }
}

private fun <T> List<T>.pairCombination(indices: IntArray, count: Int): Pair<T, T> {
    require(count == 2)

    val (first, second) = indices

    return Pair(
        first = get(first),
        second = get(second),
    )
}

private fun <T> List<T>.tripleCombination(indices: IntArray, count: Int): Triple<T, T, T> {
    require(count == 3)

    val (first, second, third) = indices

    return Triple(
        first = get(first),
        second = get(second),
        third = get(third),
    )
}

private inline fun <T, V> List<T>.combinations(
    length: Int = size,
    crossinline combination: (indices: IntArray, count: Int) -> V,
) = sequence {
    val indices = IntArray(length) { it }
    var searching = length < size

    yield(combination(indices, length))

    while (searching) {
        var found = false
        var index = length - 1

        while (index >= 0 && !found) {
            if (indices[index] == index + size - length) {
                index--
            } else {
                indices[index]++

                for (j in index + 1..<length) {
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
