package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private fun pow(base: Int, exp: Int): Int {
    return (1..exp).fold(1) { acc, _ -> acc * base }
}

class PermutationsWithReplacementTest {

    @Test
    fun `negative k throws`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int(Int.MIN_VALUE..-1)) { elements, k ->
            assertFailsWith<IllegalArgumentException> {
                elements.permutationsWithReplacement(k)
            }
        }
    }

    @Test
    fun `k of 0 returns one empty permutation`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6)) { elements ->
            assertEquals(
                expected = listOf(emptyList()),
                actual = elements.permutationsWithReplacement(0).toList(),
            )
        }
    }

    @Test
    fun `empty list with positive k returns empty sequence`() = runTest {
        checkAll(200, Arb.int(1..6)) { k ->
            assertEquals(
                expected = emptyList(),
                actual = emptyList<Int>().permutationsWithReplacement(k).toList(),
            )
        }
    }

    @Test
    fun `count equals n to the power of k`() = runTest {
        checkAll(200, Arb.int(1..4), Arb.int(1..4)) { n, k ->
            val elements = (0..<n).toList()

            assertEquals(
                expected = pow(n, k),
                actual = elements.permutationsWithReplacement(k).count(),
            )
        }
    }

    @Test
    fun `every permutation has exactly k elements`() = runTest {
        checkAll(200, Arb.int(1..4), Arb.int(1..4)) { n, k ->
            val elements = (0..<n).toList()

            elements.permutationsWithReplacement(k).forEach { permutation ->
                assertEquals(
                    expected = k,
                    actual = permutation.size,
                )
            }
        }
    }

    @Test
    fun `every element comes from the source list`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..4), Arb.int(1..4)) { elements, k ->
            elements.permutationsWithReplacement(k).forEach { permutation ->
                permutation.assertAllIn(elements)
            }
        }
    }

    @Test
    fun `no duplicate permutations when elements are distinct`() = runTest {
        checkAll(200, Arb.int(1..4), Arb.int(1..4)) { n, k ->
            val elements = (0..<n).toList()
            val results = elements.permutationsWithReplacement(k).toList()

            assertEquals(
                expected = results.size,
                actual = results.distinct().size,
            )
        }
    }

    @Test
    fun `permutations with replacement are in lexicographic order`() = runTest {
        checkAll(200, Arb.int(1..4), Arb.int(1..4)) { n, k ->
            val elements = (0..<n).toList()
            val results = elements.permutationsWithReplacement(k).toList()

            results.assertLexicographicallyOrdered()
        }
    }

    @Test
    fun `pairPermutationsWithReplacement equals permutationsWithReplacement of 2 as pairs`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5)) { elements ->
            val expected = elements.permutationsWithReplacement(2).map { (a, b) -> Pair(a, b) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.pairPermutationsWithReplacement().toList(),
            )
        }
    }

    @Test
    fun `triplePermutationsWithReplacement equals permutationsWithReplacement of 3 as triples`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..4)) { elements ->
            val expected = elements.permutationsWithReplacement(3).map { (a, b, c) -> Triple(a, b, c) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.triplePermutationsWithReplacement().toList(),
            )
        }
    }
}
