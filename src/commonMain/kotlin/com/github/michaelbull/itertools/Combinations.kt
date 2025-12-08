package com.github.michaelbull.itertools

public val EmptyCombination: Sequence<List<Nothing>> = sequenceOf(emptyList())

/**
 * Returns a sequence that yields [length]-sized [List] combinations from this list.
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

/**
 * Returns a [length]-sized [List] combination from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [length] is not positive.
 */
public fun <T> List<T>.combination(indices: IntArray, length: Int): List<T> {
    require(length > 0) { "length must be positive, but was $length" }

    return List(length) { index ->
        get(indices[index])
    }
}

/**
 * Returns a [Pair] combination from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [length] is not `2`.
 */
public fun <T> List<T>.pairCombination(indices: IntArray, length: Int): Pair<T, T> {
    require(length == 2) { "length must be 2, but was $length" }

    val (first, second) = indices

    return Pair(
        first = get(first),
        second = get(second),
    )
}

/**
 * Returns a [Triple] combination from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [length] is not `3`.
 */
public fun <T> List<T>.tripleCombination(indices: IntArray, length: Int): Triple<T, T, T> {
    require(length == 3) { "length must be 3, but was $length" }

    val (first, second, third) = indices

    return Triple(
        first = get(first),
        second = get(second),
        third = get(third),
    )
}

public typealias CombinationTransform<V> = (indices: IntArray, length: Int) -> V

/**
 * Returns a sequence that yields [length]-sized combinations from this list, using the provided [combination]
 * function to transform each combination's [indices] into [V].
 *
 * The combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * @throws IllegalArgumentException if [length] is negative.
 */
public inline fun <T, V> List<T>.combinations(
    length: Int = size,
    crossinline combination: CombinationTransform<V>,
): Sequence<V> {
    require(length >= 0) { "length must be non-negative, but was $length" }

    return sequence {
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
}
