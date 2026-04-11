package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InterleaveTest {

    @Test
    fun `interleave length equals twice the minimum size`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            assertEquals(
                expected = 2 * minOf(a.size, b.size),
                actual = a.interleave(b).size,
            )
        }
    }

    @Test
    fun `interleave preserves first list elements at even indices`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            val result = a.interleave(b)
            val m = minOf(a.size, b.size)

            assertEquals(
                expected = a.take(m),
                actual = result.filterIndexed { i, _ -> i % 2 == 0 },
            )
        }
    }

    @Test
    fun `interleave preserves second list elements at odd indices`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            val result = a.interleave(b)
            val m = minOf(a.size, b.size)

            assertEquals(
                expected = b.take(m),
                actual = result.filterIndexed { i, _ -> i % 2 == 1 },
            )
        }
    }

    @Test
    fun `interleave sequence variant matches iterable variant`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            assertEquals(
                expected = a.interleave(b),
                actual = a.asSequence().interleave(b.asSequence()).toList(),
            )
        }
    }

    @Test
    fun `interleaveTo appends interleaved elements to destination`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..3), Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { dest, a, b ->
            assertEquals(
                expected = dest + a.interleave(b),
                actual = a.interleaveTo(dest.toMutableList(), b),
            )
        }
    }

    @Test
    fun `interleaveLongest length equals sum of sizes`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            assertEquals(
                expected = a.size + b.size,
                actual = a.interleaveLongest(b).size,
            )
        }
    }

    @Test
    fun `interleaveLongest contains all elements from both lists`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            val result = a.interleaveLongest(b)

            assertEquals(
                expected = (a + b).sorted(),
                actual = result.sorted(),
            )
        }
    }

    @Test
    fun `interleaveLongest sequence variant matches iterable variant`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { a, b ->
            assertEquals(
                expected = a.interleaveLongest(b),
                actual = a.asSequence().interleaveLongest(b.asSequence()).toList(),
            )
        }
    }

    @Test
    fun `interleaveLongestTo appends elements to destination`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..3), Arb.list(Arb.int(), 0..6), Arb.list(Arb.int(), 0..6)) { dest, a, b ->
            assertEquals(
                expected = dest + a.interleaveLongest(b),
                actual = a.interleaveLongestTo(dest.toMutableList(), b),
            )
        }
    }
}
