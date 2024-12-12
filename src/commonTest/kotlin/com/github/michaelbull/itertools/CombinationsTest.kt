package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CombinationsTest {

    private val zeroElements = emptyList<Char>()
    private val oneElement = "A".toList()
    private val twoElements = "AB".toList()
    private val threeElements = "ABC".toList()
    private val fourElements = "ABCD".toList()

    @Test
    fun `-1 length combinations of 0 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            zeroElements.combinations(-1)
        }

        assertEquals("length must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 length combinations of 0 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = zeroElements.combinations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 length combinations of 0 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = zeroElements.combinations(1)
        assertEquals(expected, actual)
    }

    @Test
    fun `2 length combinations of 0 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = zeroElements.combinations(2)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 length combinations of 1 element throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            oneElement.combinations(-1)
        }

        assertEquals("length must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 length combinations of 1 element returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = oneElement.combinations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 length combinations of 1 element returns 1 combination`() {
        val expected = sequenceOf(listOf('A'))
        val actual = oneElement.combinations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 length combinations of 1 element returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = oneElement.combinations(2)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 length combinations of 2 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            twoElements.combinations(-1)
        }

        assertEquals("length must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 length combinations of 2 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = twoElements.combinations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 length combinations of 2 elements returns 2 combinations`() {
        val expected = sequenceOf(
            listOf('A'),
            listOf('B'),
        )

        val actual = twoElements.combinations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 length combinations of 2 elements returns 1 combination`() {
        val expected = sequenceOf(listOf('A', 'B'))
        val actual = twoElements.combinations(2)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `3 length combinations of 2 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = twoElements.combinations(3)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 length combinations of 3 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            threeElements.combinations(-1)
        }

        assertEquals("length must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 length combinations of 3 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = threeElements.combinations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 length combinations of 3 elements returns 3 combinations`() {
        val expected = sequenceOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
        )

        val actual = threeElements.combinations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 length combinations of 3 elements returns 3 combinations`() {
        val expected = sequenceOf(
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('B', 'C'),
        )

        val actual = threeElements.combinations(2)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `3 length combinations of 3 elements returns 1 combination`() {
        val expected = sequenceOf(listOf('A', 'B', 'C'))
        val actual = threeElements.combinations(3)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `4 length combinations of 3 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = threeElements.combinations(4)
        assertEquals(expected, actual)
    }

    @Test
    fun `-1 length combinations of 4 elements throws`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            fourElements.combinations(-1)
        }

        assertEquals("length must be non-negative, but was -1", exception.message)
    }

    @Test
    fun `0 length combinations of 4 elements returns 1 combination`() {
        val expected = listOf(emptyList<Char>())
        val actual = fourElements.combinations(0).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `1 length combinations of 4 elements returns 4 combinations`() {
        val expected = sequenceOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
            listOf('D'),
        )

        val actual = fourElements.combinations(1)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `2 length combinations of 4 elements returns 6 combinations`() {
        val expected = sequenceOf(
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('A', 'D'),
            listOf('B', 'C'),
            listOf('B', 'D'),
            listOf('C', 'D'),
        )

        val actual = fourElements.combinations(2)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `3 length combinations of 4 elements returns 4 combinations`() {
        val expected = sequenceOf(
            listOf('A', 'B', 'C'),
            listOf('A', 'B', 'D'),
            listOf('A', 'C', 'D'),
            listOf('B', 'C', 'D'),
        )

        val actual = fourElements.combinations(3)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `4 length combinations of 4 elements returns 1 combinations`() {
        val expected = sequenceOf(listOf('A', 'B', 'C', 'D'))
        val actual = fourElements.combinations(4)
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun `5 length combinations of 4 elements returns empty sequence`() {
        val expected = emptySequence<List<Char>>()
        val actual = fourElements.combinations(5)
        assertEquals(expected, actual)
    }
}
