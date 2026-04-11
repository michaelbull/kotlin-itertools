package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CombinationsWithReplacementTest {

    @Test
    fun `negative k throws`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int(Int.MIN_VALUE..-1)) { elements, k ->
            assertFailsWith<IllegalArgumentException> {
                elements.combinationsWithReplacement(k)
            }
        }
    }

    @Test
    fun `k of 0 returns one empty combination`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6)) { elements ->
            assertEquals(
                expected = listOf(emptyList()),
                actual = elements.combinationsWithReplacement(0).toList(),
            )
        }
    }

    @Test
    fun `empty list with positive k returns empty sequence`() = runTest {
        checkAll(200, Arb.int(1..6)) { k ->
            assertEquals(
                expected = emptyList(),
                actual = emptyList<Int>().combinationsWithReplacement(k).toList(),
            )
        }
    }

    @Test
    fun `count equals binomial coefficient`() = runTest {
        val maxNK = PASCALS_TRIANGLE.size / 2

        checkAll(200, Arb.int(1..maxNK), Arb.int(1..maxNK)) { n, k ->
            val elements = (0..<n).toList()

            assertMultichoose(n, k, actual = elements.combinationsWithReplacement(k).count())
        }
    }

    @Test
    fun `every combination has exactly k elements`() = runTest {
        checkAll(200, Arb.int(1..5), Arb.int(1..5)) { n, k ->
            val elements = (0..<n).toList()

            elements.combinationsWithReplacement(k).forEach { combination ->
                assertEquals(
                    expected = k,
                    actual = combination.size,
                )
            }
        }
    }

    @Test
    fun `every element comes from the source list`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5), Arb.int(1..5)) { elements, k ->
            elements.combinationsWithReplacement(k).forEach { combination ->
                combination.assertAllIn(elements)
            }
        }
    }

    @Test
    fun `no duplicate combinations when elements are distinct`() = runTest {
        checkAll(200, Arb.int(1..5), Arb.int(1..5)) { n, k ->
            val elements = (0..<n).toList()
            val results = elements.combinationsWithReplacement(k).toList()

            assertEquals(
                expected = results.size,
                actual = results.distinct().size,
            )
        }
    }

    @Test
    fun `elements appear in non-decreasing source order`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5), Arb.int(1..5)) { elements, k ->
            elements.combinationsWithReplacement(k).forEach { combination ->
                combination.assertNonDecreasingSubsequenceOf(elements)
            }
        }
    }

    @Test
    fun `combinations with replacement are in lexicographic order`() = runTest {
        checkAll(200, Arb.int(1..5), Arb.int(1..5)) { n, k ->
            val elements = (0..<n).toList()
            val results = elements.combinationsWithReplacement(k).toList()

            results.assertLexicographicallyOrdered()
        }
    }

    @Test
    fun `pairCombinationsWithReplacement equals combinationsWithReplacement of 2 as pairs`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5)) { elements ->
            val expected = elements.combinationsWithReplacement(2).map { (a, b) -> Pair(a, b) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.pairCombinationsWithReplacement().toList(),
            )
        }
    }

    @Test
    fun `tripleCombinationsWithReplacement equals combinationsWithReplacement of 3 as triples`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5)) { elements ->
            val expected = elements.combinationsWithReplacement(3).map { (a, b, c) -> Triple(a, b, c) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.tripleCombinationsWithReplacement().toList(),
            )
        }
    }
}
