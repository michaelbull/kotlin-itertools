package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

private fun pow(base: Int, exp: Int): Int {
    return (1..exp).fold(1) { acc, _ -> acc * base }
}

class PowersetTest {

    @Test
    fun `count equals 2 to the power of n`() = runTest {
        checkAll(200, Arb.int(0..6)) { n ->
            val elements = (0..<n).toList()

            assertEquals(
                expected = pow(2, n),
                actual = elements.powerset().count(),
            )
        }
    }

    @Test
    fun `first element is always empty list`() = runTest {
        checkAll(200, Arb.int(0..6)) { n ->
            val elements = (0..<n).toList()

            assertEquals(
                expected = emptyList(),
                actual = elements.powerset().first(),
            )
        }
    }

    @Test
    fun `subsets are ordered by increasing size`() = runTest {
        checkAll(200, Arb.int(0..6)) { n ->
            val elements = (0..<n).toList()
            val sizes = elements.powerset().map { it.size }.toList()

            assertEquals(
                expected = sizes,
                actual = sizes.sorted(),
            )
        }
    }

    @Test
    fun `equals union of all k-combinations`() = runTest {
        checkAll(200, Arb.int(0..6)) { n ->
            val elements = (0..<n).toList()
            val expected = (0..n).flatMap { k -> elements.combinations(k).toList() }

            assertEquals(
                expected = expected,
                actual = elements.powerset().toList(),
            )
        }
    }

    @Test
    fun `every element in every subset comes from source`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6)) { elements ->
            elements.powerset().forEach { subset ->
                subset.assertAllIn(elements)
            }
        }
    }
}
