package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CycleTest {

    @Test
    fun `cycle of empty iterable returns empty sequence`() = runTest {
        assertEquals(
            expected = emptyList(),
            actual = emptyList<Int>().cycle().toList(),
        )
    }

    @Test
    fun `cycle take n has exactly n elements`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5), Arb.int(0..20)) { elements, n ->
            assertEquals(
                expected = n,
                actual = elements.cycle().take(n).count(),
            )
        }
    }

    @Test
    fun `cycle element at index i equals source element at i mod size`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5), Arb.int(1..20)) { elements, n ->
            elements.cycle().take(n).forEachIndexed { i, element ->
                assertEquals(
                    expected = elements[i % elements.size],
                    actual = element,
                )
            }
        }
    }

    @Test
    fun `cycle with non-positive times returns empty sequence`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..5), Arb.int(Int.MIN_VALUE..0)) { elements, times ->
            assertEquals(
                expected = emptyList(),
                actual = elements.cycle(times).toList(),
            )
        }
    }

    @Test
    fun `cycle with times has expected size`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5), Arb.int(1..5)) { elements, times ->
            assertEquals(
                expected = elements.size * times,
                actual = elements.cycle(times).count(),
            )
        }
    }

    @Test
    fun `cycle with times element at index i equals source element at i mod size`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 1..5), Arb.int(1..5)) { elements, times ->
            elements.cycle(times).forEachIndexed { i, element ->
                assertEquals(
                    expected = elements[i % elements.size],
                    actual = element,
                )
            }
        }
    }
}
