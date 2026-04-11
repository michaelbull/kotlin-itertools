package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CombinationsTest {

    @Test
    fun `negative k throws`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int(Int.MIN_VALUE..-1)) { elements, k ->
            assertFailsWith<IllegalArgumentException> {
                elements.combinations(k)
            }
        }
    }

    @Test
    fun `k of 0 returns one empty combination`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6)) { elements ->
            assertEquals(
                expected = listOf(emptyList()),
                actual = elements.combinations(0).toList(),
            )
        }
    }

    @Test
    fun `k exceeding size returns empty sequence`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..5), Arb.int(1..6)) { elements, extra ->
            assertEquals(
                expected = emptyList(),
                actual = elements.combinations(elements.size + extra).toList(),
            )
        }
    }

    @Test
    fun `count equals binomial coefficient`() = runTest {
        checkAll(200, Arb.int(PASCALS_TRIANGLE.indices), Arb.int(PASCALS_TRIANGLE.indices)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            assertBinomial(n, k, actual = elements.combinations(k).count())
        }
    }

    @Test
    fun `every combination has exactly k elements`() = runTest {
        checkAll(200, Arb.int(0..6), Arb.int(0..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            elements.combinations(k).forEach { combination ->
                assertEquals(
                    expected = k,
                    actual = combination.size,
                )
            }
        }
    }

    @Test
    fun `every element comes from the source list`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..6), Arb.int(1..6)) { elements, rawK ->
            val k = rawK.coerceAtMost(elements.size)

            elements.combinations(k).forEach { combination ->
                combination.assertAllIn(elements)
            }
        }
    }

    @Test
    fun `no duplicate combinations when elements are distinct`() = runTest {
        checkAll(200, Arb.int(0..6), Arb.int(0..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)
            val results = elements.combinations(k).toList()

            assertEquals(
                expected = results.size,
                actual = results.distinct().size,
            )
        }
    }

    @Test
    fun `elements appear in ascending source order`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..6), Arb.int(1..6)) { elements, rawK ->
            val k = rawK.coerceAtMost(elements.size)

            elements.combinations(k).forEach { combination ->
                combination.assertOrderedSubsequenceOf(elements)
            }
        }
    }


    @Test
    fun `combinations are in lexicographic order`() = runTest {
        checkAll(200, Arb.int(0..6), Arb.int(0..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)
            val results = elements.combinations(k).toList()

            results.assertLexicographicallyOrdered()
        }
    }

    @Test
    fun `pairCombinations equals combinations of 2 as pairs`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 2..6)) { elements ->
            val expected = elements.combinations(2).map { (a, b) -> Pair(a, b) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.pairCombinations().toList(),
            )
        }
    }

    @Test
    fun `tripleCombinations equals combinations of 3 as triples`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 3..6)) { elements ->
            val expected = elements.combinations(3).map { (a, b, c) -> Triple(a, b, c) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.tripleCombinations().toList(),
            )
        }
    }
}
