package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PermutationsWithReplacementTest {

    private val zeroElements = emptyList<Char>()
    private val oneElement = "A".toList()
    private val twoElements = "AB".toList()
    private val threeElements = "ABC".toList()

    @Test
    fun `-1 k permutationsWithReplacement of 0 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            zeroElements.permutationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutationsWithReplacement of 0 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = zeroElements.permutationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutationsWithReplacement of 0 elements returns empty sequence`() {
        val expected = emptyList<List<Char>>()
        val actual = zeroElements.permutationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k permutationsWithReplacement of 0 elements returns empty sequence`() {
        val expected = emptyList<List<Char>>()
        val actual = zeroElements.permutationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutationsWithReplacement of 1 element throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            oneElement.permutationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutationsWithReplacement of 1 element returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = oneElement.permutationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutationsWithReplacement of 1 element returns 1 permutation`() {
        val expected = listOf(listOf('A'))
        val actual = oneElement.permutationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k permutationsWithReplacement of 1 element returns 1 permutation`() {
        val expected = listOf(listOf('A', 'A'))
        val actual = oneElement.permutationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `3 k permutationsWithReplacement of 1 element returns 1 permutation`() {
        val expected = listOf(listOf('A', 'A', 'A'))
        val actual = oneElement.permutationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutationsWithReplacement of 2 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            twoElements.permutationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutationsWithReplacement of 2 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = twoElements.permutationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutationsWithReplacement of 2 elements returns 2 permutations`() {
        val expected = listOf(
            listOf('A'),
            listOf('B'),
        )

        val actual = twoElements.permutationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k permutationsWithReplacement of 2 elements returns 4 permutations`() {
        val expected = listOf(
            listOf('A', 'A'),
            listOf('A', 'B'),
            listOf('B', 'A'),
            listOf('B', 'B'),
        )

        val actual = twoElements.permutationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `3 k permutationsWithReplacement of 2 elements returns 8 permutations`() {
        val expected = listOf(
            listOf('A', 'A', 'A'),
            listOf('A', 'A', 'B'),
            listOf('A', 'B', 'A'),
            listOf('A', 'B', 'B'),
            listOf('B', 'A', 'A'),
            listOf('B', 'A', 'B'),
            listOf('B', 'B', 'A'),
            listOf('B', 'B', 'B'),
        )

        val actual = twoElements.permutationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutationsWithReplacement of 3 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            threeElements.permutationsWithReplacement(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutationsWithReplacement of 3 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = threeElements.permutationsWithReplacement(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutationsWithReplacement of 3 elements returns 3 permutations`() {
        val expected = listOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
        )

        val actual = threeElements.permutationsWithReplacement(1).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k permutationsWithReplacement of 3 elements returns 9 permutations`() {
        val expected = listOf(
            listOf('A', 'A'),
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('B', 'A'),
            listOf('B', 'B'),
            listOf('B', 'C'),
            listOf('C', 'A'),
            listOf('C', 'B'),
            listOf('C', 'C'),
        )

        val actual = threeElements.permutationsWithReplacement(2).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `3 k permutationsWithReplacement of 3 elements returns 27 permutations`() {
        val expected = listOf(
            listOf('A', 'A', 'A'),
            listOf('A', 'A', 'B'),
            listOf('A', 'A', 'C'),
            listOf('A', 'B', 'A'),
            listOf('A', 'B', 'B'),
            listOf('A', 'B', 'C'),
            listOf('A', 'C', 'A'),
            listOf('A', 'C', 'B'),
            listOf('A', 'C', 'C'),
            listOf('B', 'A', 'A'),
            listOf('B', 'A', 'B'),
            listOf('B', 'A', 'C'),
            listOf('B', 'B', 'A'),
            listOf('B', 'B', 'B'),
            listOf('B', 'B', 'C'),
            listOf('B', 'C', 'A'),
            listOf('B', 'C', 'B'),
            listOf('B', 'C', 'C'),
            listOf('C', 'A', 'A'),
            listOf('C', 'A', 'B'),
            listOf('C', 'A', 'C'),
            listOf('C', 'B', 'A'),
            listOf('C', 'B', 'B'),
            listOf('C', 'B', 'C'),
            listOf('C', 'C', 'A'),
            listOf('C', 'C', 'B'),
            listOf('C', 'C', 'C'),
        )

        val actual = threeElements.permutationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `k exceeding size still produces results`() {
        val expected = listOf(
            listOf('A', 'A', 'A'),
            listOf('A', 'A', 'B'),
            listOf('A', 'B', 'A'),
            listOf('A', 'B', 'B'),
            listOf('B', 'A', 'A'),
            listOf('B', 'A', 'B'),
            listOf('B', 'B', 'A'),
            listOf('B', 'B', 'B'),
        )

        val actual = twoElements.permutationsWithReplacement(3).toList()
        assertEquals(expected, actual)
    }
}
