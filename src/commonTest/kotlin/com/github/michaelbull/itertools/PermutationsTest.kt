package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PermutationsTest {

    private val zeroElements = emptyList<Char>()
    private val oneElement = "A".toList()
    private val twoElements = "AB".toList()
    private val threeElements = "ABC".toList()
    private val fourElements = "ABCD".toList()

    @Test
    fun `-1 k permutations of 0 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            zeroElements.permutations(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutations of 0 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = zeroElements.permutations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutations of 0 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = zeroElements.permutations(1)
        assertEquals(expected, actual)
    }

    @Test
    fun `2 k permutations of 0 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = zeroElements.permutations(2)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutations of 1 element throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            oneElement.permutations(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutations of 1 element returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = oneElement.permutations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutations of 1 element returns 1 permutation`() {
        val expected = sequenceOf(listOf('A'))
        val actual = oneElement.permutations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 k permutations of 1 element returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = oneElement.permutations(2)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutations of 2 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            twoElements.permutations(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutations of 2 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = twoElements.permutations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutations of 2 elements returns 2 permutations`() {
        val expected = sequenceOf(
            listOf('A'),
            listOf('B'),
        )

        val actual = twoElements.permutations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 k permutations of 2 elements returns 2 permutations`() {
        val expected = sequenceOf(
            listOf('A', 'B'),
            listOf('B', 'A'),
        )

        val actual = twoElements.permutations(2)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `3 k permutations of 2 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = twoElements.permutations(3)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutations of 3 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            threeElements.permutations(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutations of 3 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = threeElements.permutations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutations of 3 elements returns 3 permutations`() {
        val expected = sequenceOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
        )

        val actual = threeElements.permutations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 k permutations of 3 elements returns 6 permutations`() {
        val expected = sequenceOf(
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('B', 'A'),
            listOf('B', 'C'),
            listOf('C', 'A'),
            listOf('C', 'B'),
        )

        val actual = threeElements.permutations(2)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `3 k permutations of 3 elements returns 6 permutations`() {
        val expected = sequenceOf(
            listOf('A', 'B', 'C'),
            listOf('A', 'C', 'B'),
            listOf('B', 'A', 'C'),
            listOf('B', 'C', 'A'),
            listOf('C', 'A', 'B'),
            listOf('C', 'B', 'A'),
        )

        val actual = threeElements.permutations(3)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `4 k permutations of 3 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = threeElements.permutations(4)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 k permutations of 4 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            fourElements.permutations(-1)
        }

        assertEquals("k must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 k permutations of 4 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = fourElements.permutations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 k permutations of 4 elements returns 4 permutations`() {
        val expected = sequenceOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
            listOf('D'),
        )

        val actual = fourElements.permutations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 k permutations of 4 elements returns 12 permutations`() {
        val expected = sequenceOf(
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('A', 'D'),
            listOf('B', 'A'),
            listOf('B', 'C'),
            listOf('B', 'D'),
            listOf('C', 'A'),
            listOf('C', 'B'),
            listOf('C', 'D'),
            listOf('D', 'A'),
            listOf('D', 'B'),
            listOf('D', 'C'),
        )

        val actual = fourElements.permutations(2)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `3 k permutations of 4 elements returns 4 permutations`() {
        val expected = sequenceOf(
            listOf('A', 'B', 'C'),
            listOf('A', 'B', 'D'),
            listOf('A', 'C', 'B'),
            listOf('A', 'C', 'D'),
            listOf('A', 'D', 'B'),
            listOf('A', 'D', 'C'),
            listOf('B', 'A', 'C'),
            listOf('B', 'A', 'D'),
            listOf('B', 'C', 'A'),
            listOf('B', 'C', 'D'),
            listOf('B', 'D', 'A'),
            listOf('B', 'D', 'C'),
            listOf('C', 'A', 'B'),
            listOf('C', 'A', 'D'),
            listOf('C', 'B', 'A'),
            listOf('C', 'B', 'D'),
            listOf('C', 'D', 'A'),
            listOf('C', 'D', 'B'),
            listOf('D', 'A', 'B'),
            listOf('D', 'A', 'C'),
            listOf('D', 'B', 'A'),
            listOf('D', 'B', 'C'),
            listOf('D', 'C', 'A'),
            listOf('D', 'C', 'B'),
        )

        val actual = fourElements.permutations(3)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `4 k permutations of 4 elements returns 24 permutations`() {
        val expected = sequenceOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('A', 'B', 'D', 'C'),
            listOf('A', 'C', 'B', 'D'),
            listOf('A', 'C', 'D', 'B'),
            listOf('A', 'D', 'B', 'C'),
            listOf('A', 'D', 'C', 'B'),
            listOf('B', 'A', 'C', 'D'),
            listOf('B', 'A', 'D', 'C'),
            listOf('B', 'C', 'A', 'D'),
            listOf('B', 'C', 'D', 'A'),
            listOf('B', 'D', 'A', 'C'),
            listOf('B', 'D', 'C', 'A'),
            listOf('C', 'A', 'B', 'D'),
            listOf('C', 'A', 'D', 'B'),
            listOf('C', 'B', 'A', 'D'),
            listOf('C', 'B', 'D', 'A'),
            listOf('C', 'D', 'A', 'B'),
            listOf('C', 'D', 'B', 'A'),
            listOf('D', 'A', 'B', 'C'),
            listOf('D', 'A', 'C', 'B'),
            listOf('D', 'B', 'A', 'C'),
            listOf('D', 'B', 'C', 'A'),
            listOf('D', 'C', 'A', 'B'),
            listOf('D', 'C', 'B', 'A'),
        )

        val actual = fourElements.permutations(4)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `5 k permutations of 4 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = fourElements.permutations(5)
        assertEquals(expected, actual)
    }
}
