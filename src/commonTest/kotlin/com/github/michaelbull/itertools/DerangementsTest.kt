package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DerangementsTest {

    @Test
    fun `-1 k derangements of 0 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            emptyList<Char>().derangements(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k derangements of 0 elements returns 1 derangement`() {
        val expected = listOf(emptyList<Char>())
        val actual = emptyList<Char>().derangements(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `derangements of 0 elements returns 1 derangement`() {
        val expected = listOf(emptyList<Char>())
        val actual = emptyList<Char>().derangements().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `derangements of 1 element returns empty sequence`() {
        val expected = emptyList<List<Char>>()
        val actual = "A".toList().derangements().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `derangements of 2 elements returns 1 derangement`() {
        val expected = listOf(
            listOf('B', 'A'),
        )

        val actual = "AB".toList().derangements().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `derangements of 3 elements returns 2 derangements`() {
        val expected = listOf(
            listOf('B', 'C', 'A'),
            listOf('C', 'A', 'B'),
        )

        val actual = "ABC".toList().derangements().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `derangements of 4 elements returns 9 derangements`() {
        val expected = listOf(
            listOf('B', 'A', 'D', 'C'),
            listOf('B', 'C', 'D', 'A'),
            listOf('B', 'D', 'A', 'C'),
            listOf('C', 'A', 'D', 'B'),
            listOf('C', 'D', 'A', 'B'),
            listOf('C', 'D', 'B', 'A'),
            listOf('D', 'A', 'B', 'C'),
            listOf('D', 'C', 'A', 'B'),
            listOf('D', 'C', 'B', 'A'),
        )

        val actual = "ABCD".toList().derangements().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k derangements of 3 elements returns 3 derangements`() {
        val expected = listOf(
            listOf('B', 'A'),
            listOf('B', 'C'),
            listOf('C', 'A'),
        )

        val actual = "ABC".toList().derangements(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k derangements of 4 elements returns 7 derangements`() {
        val expected = listOf(
            listOf('B', 'A'),
            listOf('B', 'C'),
            listOf('B', 'D'),
            listOf('C', 'A'),
            listOf('C', 'D'),
            listOf('D', 'A'),
            listOf('D', 'C'),
        )

        val actual = "ABCD".toList().derangements(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `k exceeding size returns empty sequence`() {
        val expected = emptyList<List<Char>>()
        val actual = "AB".toList().derangements(3).toList()
        assertEquals(expected, actual)
    }
}
