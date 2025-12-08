package com.github.michaelbull.itertools

public val EmptyCombination: Sequence<List<Nothing>> = sequenceOf(emptyList())

/**
 * Returns a sequence that yields [k]-sized [List] combinations from this list.
 *
 * The combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * "ABCD".toList()
 *     .combinations(k = 2)
 *     .toList()
 *     // [[A, B], [A, C], [A, D], [B, C], [B, D], [C, D]]
 *
 * (0..3).toList()
 *     .combinations(k = 3)
 *     .toList()
 *     // [[0, 1, 2], [0, 1, 3], [0, 2, 3], [1, 2, 3]]
 *
 * listOf(1, 2, 3)
 *     .combinations(k = 0)
 *     .toList()
 *     // [[]]
 *
 * emptyList<Int>()
 *     .combinations()
 *     .toList()
 *     // [[]]
 * ```
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public fun <T> List<T>.combinations(k: Int = size): Sequence<List<T>> {
    return if (k == 0) {
        EmptyCombination
    } else {
        combinations(
            k = k,
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
    return combinations(
        k = 2,
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
        k = 3,
        combination = ::tripleCombination,
    )
}

/**
 * Returns a [k]-sized [List] combination from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [k] is not positive.
 */
public fun <T> List<T>.combination(indices: IntArray, k: Int): List<T> {
    require(k > 0) { "k must be positive, but was $k" }

    return List(k) { index ->
        get(indices[index])
    }
}

/**
 * Returns a [Pair] combination from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [k] is not `2`.
 */
public fun <T> List<T>.pairCombination(indices: IntArray, k: Int): Pair<T, T> {
    require(k == 2) { "k must be 2, but was $k" }

    val (first, second) = indices

    return Pair(
        first = get(first),
        second = get(second),
    )
}

/**
 * Returns a [Triple] combination from this list at the given [indices].
 *
 * @throws IllegalArgumentException if [k] is not `3`.
 */
public fun <T> List<T>.tripleCombination(indices: IntArray, k: Int): Triple<T, T, T> {
    require(k == 3) { "k must be 3, but was $k" }

    val (first, second, third) = indices

    return Triple(
        first = get(first),
        second = get(second),
        third = get(third),
    )
}

public typealias CombinationTransform<V> = (indices: IntArray, k: Int) -> V

/**
 * Returns a sequence that yields [k]-sized combinations from this list, using the provided [combination]
 * function to transform each combination's [indices] into [V].
 *
 * The combination tuples are emitted in lexicographic order according to the order of this list.
 *
 * @throws IllegalArgumentException if [k] is negative.
 */
public inline fun <T, V> List<T>.combinations(
    k: Int = size,
    crossinline combination: CombinationTransform<V>,
): Sequence<V> {
    require(k >= 0) { "k must be non-negative, but was $k" }

    return if (size < k) {
        emptySequence()
    } else {
        sequence {
            val indices = IntArray(k) { it }
            var searching = k < size

            yield(combination(indices, k))

            while (searching) {
                var found = false
                var index = k - 1

                while (index >= 0 && !found) {
                    if (indices[index] == index + size - k) {
                        index--
                    } else {
                        indices[index]++

                        for (j in (index + 1)..<k) {
                            indices[j] = indices[j - 1] + 1
                        }

                        yield(combination(indices, k))
                        found = true
                    }
                }

                if (!found) {
                    searching = false
                }
            }
        }
    }
}
