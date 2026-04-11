package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Precomputed factorials: FACTORIAL[n] = n!
 * https://oeis.org/A000142
 */
private val FACTORIAL = listOf(1, 1, 2, 6, 24, 120, 720)

class PermutationsTest {

    @Test
    fun `negative k throws`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int(Int.MIN_VALUE..-1)) { elements, k ->
            assertFailsWith<IllegalArgumentException> {
                elements.permutations(k)
            }
        }
    }

    @Test
    fun `k of 0 returns one empty permutation`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6)) { elements ->
            assertEquals(
                expected = listOf(emptyList()),
                actual = elements.permutations(0).toList(),
            )
        }
    }

    @Test
    fun `k exceeding size returns empty sequence`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..5), Arb.int(1..6)) { elements, extra ->
            assertEquals(
                expected = emptyList(),
                actual = elements.permutations(elements.size + extra).toList(),
            )
        }
    }

    @Test
    fun `count equals partial permutation formula`() = runTest {
        checkAll(200, Arb.int(FACTORIAL.indices), Arb.int(FACTORIAL.indices)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            assertEquals(
                expected = FACTORIAL[n] / FACTORIAL[n - k],
                actual = elements.permutations(k).count(),
            )
        }
    }

    @Test
    fun `every permutation has exactly k elements`() = runTest {
        checkAll(200, Arb.int(0..6), Arb.int(0..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            elements.permutations(k).forEach { permutation ->
                assertEquals(
                    expected = k,
                    actual = permutation.size,
                )
            }
        }
    }

    @Test
    fun `every element comes from the source list`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..6), Arb.int(1..6)) { elements, rawK ->
            val k = rawK.coerceAtMost(elements.size)

            elements.permutations(k).forEach { permutation ->
                permutation.assertAllIn(elements)
            }
        }
    }

    @Test
    fun `no duplicate elements within a permutation when source is distinct`() = runTest {
        checkAll(200, Arb.int(1..6), Arb.int(1..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)

            elements.permutations(k).forEach { permutation ->
                assertEquals(
                    expected = permutation.size,
                    actual = permutation.distinct().size,
                )
            }
        }
    }

    @Test
    fun `no duplicate permutations when elements are distinct`() = runTest {
        checkAll(200, Arb.int(0..6), Arb.int(0..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)
            val results = elements.permutations(k).toList()

            assertEquals(
                expected = results.size,
                actual = results.distinct().size,
            )
        }
    }

    @Test
    fun `permutations are in lexicographic order`() = runTest {
        checkAll(200, Arb.int(0..6), Arb.int(0..6)) { n, rawK ->
            val elements = (0..<n).toList()
            val k = rawK.coerceAtMost(n)
            val results = elements.permutations(k).toList()

            results.assertLexicographicallyOrdered()
        }
    }

    @Test
    fun `pairPermutations equals permutations of 2 as pairs`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 2..6)) { elements ->
            val expected = elements.permutations(2).map { (a, b) -> Pair(a, b) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.pairPermutations().toList(),
            )
        }
    }

    @Test
    fun `triplePermutations equals permutations of 3 as triples`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 3..6)) { elements ->
            val expected = elements.permutations(3).map { (a, b, c) -> Triple(a, b, c) }.toList()

            assertEquals(
                expected = expected,
                actual = elements.triplePermutations().toList(),
            )
        }
    }
}
