package com.github.michaelbull.itertools

import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pascal's triangle.
 *
 * ```kotlin
 * // C(n, k) = binomial(n, k) = n! / (k! * (n - k)!), where 0 <= k <= n
 * val n = 4
 * val k = 2
 * PASCALS_TRIANGLE[n][k] // 6
 * ```
 *
 * See: [Pascal's triangle read by rows](https://oeis.org/A007318)
 */
val PASCALS_TRIANGLE = listOf(
    /* n = 00 */ listOf(1),
    /* n = 01 */ listOf(1, 1),
    /* n = 02 */ listOf(1, 2, 1),
    /* n = 03 */ listOf(1, 3, 3, 1),
    /* n = 04 */ listOf(1, 4, 6, 4, 1),
    /* n = 05 */ listOf(1, 5, 10, 10, 5, 1),
    /* n = 06 */ listOf(1, 6, 15, 20, 15, 6, 1),
    /* n = 07 */ listOf(1, 7, 21, 35, 35, 21, 7, 1),
    /* n = 08 */ listOf(1, 8, 28, 56, 70, 56, 28, 8, 1),
    /* n = 09 */ listOf(1, 9, 36, 84, 126, 126, 84, 36, 9, 1),
    /* n = 10 */ listOf(1, 10, 45, 120, 210, 252, 210, 120, 45, 10, 1),
    /* n = 11 */ listOf(1, 11, 55, 165, 330, 462, 462, 330, 165, 55, 11, 1),
    /* n = 12 */ listOf(1, 12, 66, 220, 495, 792, 924, 792, 495, 220, 66, 12, 1),
)

fun assertBinomial(n: Int, k: Int, actual: Int) {
    assertEquals(
        expected = PASCALS_TRIANGLE[n][k],
        actual = actual,
        message = "C($n, $k)",
    )
}

fun assertMultichoose(n: Int, k: Int, actual: Int) {
    assertEquals(
        expected = PASCALS_TRIANGLE[n + k - 1][k],
        actual = actual,
        message = "C(${n + k - 1}, $k)",
    )
}

fun <T> List<T>.assertAllIn(source: List<T>) {
    assertTrue(
        actual = source.containsAll(this),
        message = "expected $source to contain all of $this",
    )
}

fun <T> List<T>.assertOrderedSubsequenceOf(source: List<T>) {
    assertTrue(
        actual = isOrderedSubsequenceOf(source),
        message = "expected $this to be an ordered subsequence of $source",
    )
}

fun <T> List<T>.assertNonDecreasingSubsequenceOf(source: List<T>) {
    assertTrue(
        actual = isNonDecreasingSubsequenceOf(source),
        message = "expected $this to be a non-decreasing subsequence of $source",
    )
}

fun <T : Comparable<T>> List<List<T>>.assertLexicographicallyOrdered() {
    zipWithNext().forEach { (a, b) ->
        assertTrue(
            actual = a isLexicographicallyBefore b,
            message = "expected $a to be lexicographically before $b",
        )
    }
}

private fun <T> List<T>.isOrderedSubsequenceOf(source: List<T>): Boolean {
    return indexOfSubsequenceIn(source, step = 1) >= 0
}

private fun <T> List<T>.isNonDecreasingSubsequenceOf(source: List<T>): Boolean {
    return indexOfSubsequenceIn(source, step = 0) >= 0
}

private fun <T> List<T>.indexOfSubsequenceIn(source: List<T>, step: Int): Int {
    return fold(0) { sourceIndex, element ->
        val indices = sourceIndex..<source.size
        val found = indices.find { index -> element == source[index] }

        if (found == null) {
            return -1
        } else {
            found + step
        }
    }
}

private infix fun <T : Comparable<T>> List<T>.isLexicographicallyBefore(other: List<T>): Boolean {
    val pairs = zip(other)

    return when (val diff = pairs.find(Pair<T, T>::isDifferent)) {
        null -> size <= other.size
        else -> diff.isAscending()
    }
}

private fun <T : Comparable<T>> Pair<T, T>.isAscending(): Boolean {
    return first < second
}

private fun <A, B> Pair<A, B>.isDifferent(): Boolean {
    return first != second
}
