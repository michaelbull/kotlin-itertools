package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IntersperseTest {

    @Test
    fun `intersperse length equals max of 0 and 2n minus 1`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int()) { elements, separator ->
            assertEquals(
                expected = maxOf(0, 2 * elements.size - 1),
                actual = elements.intersperse(separator).size,
            )
        }
    }

    @Test
    fun `intersperse even-indexed elements are originals in order`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int()) { elements, separator ->
            val result = elements.intersperse(separator)

            assertEquals(
                expected = elements,
                actual = result.filterIndexed { i, _ -> i % 2 == 0 },
            )
        }
    }

    @Test
    fun `intersperse odd-indexed elements are all the separator`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 2..6), Arb.int()) { elements, separator ->
            val result = elements.intersperse(separator)
            val separators = result.filterIndexed { i, _ -> i % 2 == 1 }

            assertEquals(
                expected = List(elements.size - 1) { separator },
                actual = separators,
            )
        }
    }

    @Test
    fun `intersperse sequence variant matches iterable variant`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.int()) { elements, separator ->
            assertEquals(
                expected = elements.intersperse(separator),
                actual = elements.asSequence().intersperse(separator).toList(),
            )
        }
    }

    @Test
    fun `intersperseTo appends interspersed elements to destination`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..3), Arb.list(Arb.int(), 0..6), Arb.int()) { dest, elements, separator ->
            assertEquals(
                expected = dest + elements.intersperse(separator),
                actual = elements.intersperseTo(dest.toMutableList(), separator),
            )
        }
    }
}
