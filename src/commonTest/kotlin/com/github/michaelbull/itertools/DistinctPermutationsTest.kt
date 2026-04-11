package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Precomputed factorials: FACTORIAL[n] = n!
 * https://oeis.org/A000142
 */
private val FACTORIAL = listOf(1, 1, 2, 6, 24, 120, 720)

private fun multinomialCoefficient(elements: List<*>): Int {
    val frequencies = elements.groupingBy { it }.eachCount()
    return FACTORIAL[elements.size] / frequencies.values.fold(1) { acc, freq -> acc * FACTORIAL[freq] }
}

class DistinctPermutationsTest {

    @Test
    fun `empty list returns one empty permutation`() = runTest {
        assertEquals(
            expected = listOf(emptyList()),
            actual = emptyList<Int>().distinctPermutations().toList(),
        )
    }

    @Test
    fun `every permutation has same size as input`() = runTest {
        checkAll(200, Arb.list(Arb.int(0..3), 0..5)) { elements ->
            elements.distinctPermutations().forEach { permutation ->
                assertEquals(
                    expected = elements.size,
                    actual = permutation.size,
                )
            }
        }
    }

    @Test
    fun `every permutation is a rearrangement of input`() = runTest {
        checkAll(200, Arb.list(Arb.int(0..3), 0..5)) { elements ->
            val sortedInput = elements.sorted()

            elements.distinctPermutations().forEach { permutation ->
                assertEquals(
                    expected = sortedInput,
                    actual = permutation.sorted(),
                )
            }
        }
    }

    @Test
    fun `no duplicate permutations`() = runTest {
        checkAll(200, Arb.list(Arb.int(0..3), 0..5)) { elements ->
            val results = elements.distinctPermutations().toList()

            assertEquals(
                expected = results.size,
                actual = results.distinct().size,
            )
        }
    }

    @Test
    fun `count equals multinomial coefficient`() = runTest {
        checkAll(200, Arb.list(Arb.int(0..3), 0..5)) { elements ->
            assertEquals(
                expected = multinomialCoefficient(elements),
                actual = elements.distinctPermutations().count(),
            )
        }
    }

    @Test
    fun `output is in lexicographic order`() = runTest {
        checkAll(200, Arb.list(Arb.int(0..3), 0..5)) { elements ->
            val results = elements.distinctPermutations().toList()

            results.assertLexicographicallyOrdered()
        }
    }
}
