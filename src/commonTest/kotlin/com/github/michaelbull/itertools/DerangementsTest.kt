package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

/**
 * Partial derangement counts: PARTIAL_DERANGEMENT[n][k] = D(n, k)
 * The number of k-permutations of n elements where no element appears in its original position.
 * https://oeis.org/A000166 (diagonal k=n is the subfactorial sequence)
 */
private val PARTIAL_DERANGEMENT = listOf(
    listOf(1),
    listOf(1, 0),
    listOf(1, 1, 1),
    listOf(1, 2, 3, 2),
    listOf(1, 3, 7, 11, 9),
    listOf(1, 4, 13, 32, 53, 44),
    listOf(1, 5, 21, 71, 181, 309, 265),
)

class DerangementsTest {

    @Test
    fun `negative k throws`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int(Int.MIN_VALUE..-1)) { elements, k ->
            assertFailsWith<IllegalArgumentException> {
                elements.derangements(k)
            }
        }
    }

    @Test
    fun `k of 0 returns one empty derangement`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6)) { elements ->
            assertEquals(
                expected = listOf(emptyList()),
                actual = elements.derangements(0).toList(),
            )
        }
    }

    @Test
    fun `k exceeding size returns empty sequence`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..5), Arb.int(1..6)) { elements, extra ->
            assertEquals(
                expected = emptyList(),
                actual = elements.derangements(elements.size + extra).toList(),
            )
        }
    }

    @Test
    fun `no element at position i equals source element at position i`() = runTest {
        checkAll(200, Arb.int(1..6), Arb.int(1..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            elements.derangements(k).forEach { derangement ->
                derangement.forEachIndexed { i, element ->
                    assertNotEquals(
                        illegal = elements[i],
                        actual = element,
                    )
                }
            }
        }
    }

    @Test
    fun `every derangement has exactly k elements`() = runTest {
        checkAll(200, Arb.int(1..6), Arb.int(1..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            elements.derangements(k).forEach { derangement ->
                assertEquals(
                    expected = k,
                    actual = derangement.size,
                )
            }
        }
    }

    @Test
    fun `no duplicate derangements when elements are distinct`() = runTest {
        checkAll(200, Arb.int(0..5), Arb.int(0..5)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)
            val results = elements.derangements(k).toList()

            assertEquals(
                expected = results.size,
                actual = results.distinct().size,
            )
        }
    }

    @Test
    fun `count equals partial derangement formula`() = runTest {
        checkAll(200, Arb.int(PARTIAL_DERANGEMENT.indices), Arb.int(PARTIAL_DERANGEMENT.indices)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            assertEquals(
                expected = PARTIAL_DERANGEMENT[n][k],
                actual = elements.derangements(k).count(),
            )
        }
    }

    @Test
    fun `derangements are in lexicographic order`() = runTest {
        checkAll(200, Arb.int(1..6), Arb.int(1..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)
            val results = elements.derangements(k).toList()

            results.assertLexicographicallyOrdered()
        }
    }
}
