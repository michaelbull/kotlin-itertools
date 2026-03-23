package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class CycleTest {

    @Test
    fun `cycle of empty iterable returns empty sequence`() {
        val expected = emptySequence<Int>()
        val actual = emptyList<Int>().cycle()
        assertEquals(expected, actual)
    }

    @Test
    fun `cycle of 1 element repeats indefinitely`() {
        val expected = listOf('A', 'A', 'A', 'A', 'A')
        val actual = "A".toList().cycle().take(5).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `cycle of 2 elements alternates indefinitely`() {
        val expected = listOf('A', 'B', 'A', 'B', 'A', 'B')
        val actual = "AB".toList().cycle().take(6).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `cycle of 3 elements repeats in order`() {
        val expected = listOf('A', 'B', 'C', 'A', 'B', 'C', 'A')
        val actual = "ABC".toList().cycle().take(7).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `cycle take 0 returns empty list`() {
        val expected = emptyList<Char>()
        val actual = "ABC".toList().cycle().take(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `cycle take less than one full cycle`() {
        val expected = listOf('A', 'B')
        val actual = "ABCD".toList().cycle().take(2).toList()
        assertEquals(expected, actual)
    }
}
