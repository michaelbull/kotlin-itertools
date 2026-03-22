package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CombinationsWithReplacementTest {

    private val zeroElements = emptyList<Char>()
    private val oneElement = "A".toList()
    private val twoElements = "AB".toList()
    private val threeElements = "ABC".toList()

    @Test
    fun `-1 k combinationsWithReplacement of 0 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            zeroElements.combinationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k combinationsWithReplacement of 0 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = zeroElements.combinationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k combinationsWithReplacement of 0 elements returns empty sequence`() {
        val expected = emptyList<List<Char>>()
        val actual = zeroElements.combinationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k combinationsWithReplacement of 0 elements returns empty sequence`() {
        val expected = emptyList<List<Char>>()
        val actual = zeroElements.combinationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k combinationsWithReplacement of 1 element throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            oneElement.combinationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k combinationsWithReplacement of 1 element returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = oneElement.combinationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k combinationsWithReplacement of 1 element returns 1 combination`() {
        val expected = listOf(listOf('A'))
        val actual = oneElement.combinationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k combinationsWithReplacement of 1 element returns 1 combination`() {
        val expected = listOf(listOf('A', 'A'))
        val actual = oneElement.combinationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `3 k combinationsWithReplacement of 1 element returns 1 combination`() {
        val expected = listOf(listOf('A', 'A', 'A'))
        val actual = oneElement.combinationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k combinationsWithReplacement of 2 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            twoElements.combinationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k combinationsWithReplacement of 2 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = twoElements.combinationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k combinationsWithReplacement of 2 elements returns 2 combinations`() {
        val expected = listOf(
            listOf('A'),
            listOf('B'),
        )

        val actual = twoElements.combinationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k combinationsWithReplacement of 2 elements returns 3 combinations`() {
        val expected = listOf(
            listOf('A', 'A'),
            listOf('A', 'B'),
            listOf('B', 'B'),
        )

        val actual = twoElements.combinationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `3 k combinationsWithReplacement of 2 elements returns 4 combinations`() {
        val expected = listOf(
            listOf('A', 'A', 'A'),
            listOf('A', 'A', 'B'),
            listOf('A', 'B', 'B'),
            listOf('B', 'B', 'B'),
        )

        val actual = twoElements.combinationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k combinationsWithReplacement of 3 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            threeElements.combinationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k combinationsWithReplacement of 3 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = threeElements.combinationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k combinationsWithReplacement of 3 elements returns 3 combinations`() {
        val expected = listOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
        )

        val actual = threeElements.combinationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k combinationsWithReplacement of 3 elements returns 6 combinations`() {
        val expected = listOf(
            listOf('A', 'A'),
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('B', 'B'),
            listOf('B', 'C'),
            listOf('C', 'C'),
        )

        val actual = threeElements.combinationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `3 k combinationsWithReplacement of 3 elements returns 10 combinations`() {
        val expected = listOf(
            listOf('A', 'A', 'A'),
            listOf('A', 'A', 'B'),
            listOf('A', 'A', 'C'),
            listOf('A', 'B', 'B'),
            listOf('A', 'B', 'C'),
            listOf('A', 'C', 'C'),
            listOf('B', 'B', 'B'),
            listOf('B', 'B', 'C'),
            listOf('B', 'C', 'C'),
            listOf('C', 'C', 'C'),
        )

        val actual = threeElements.combinationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `4 k combinationsWithReplacement of 3 elements returns 15 combinations`() {
        val expected = listOf(
            listOf('A', 'A', 'A', 'A'),
            listOf('A', 'A', 'A', 'B'),
            listOf('A', 'A', 'A', 'C'),
            listOf('A', 'A', 'B', 'B'),
            listOf('A', 'A', 'B', 'C'),
            listOf('A', 'A', 'C', 'C'),
            listOf('A', 'B', 'B', 'B'),
            listOf('A', 'B', 'B', 'C'),
            listOf('A', 'B', 'C', 'C'),
            listOf('A', 'C', 'C', 'C'),
            listOf('B', 'B', 'B', 'B'),
            listOf('B', 'B', 'B', 'C'),
            listOf('B', 'B', 'C', 'C'),
            listOf('B', 'C', 'C', 'C'),
            listOf('C', 'C', 'C', 'C'),
        )

        val actual = threeElements.combinationsWithReplacement(4).toList()
        assertEquals(expected, actual)
    }
}
