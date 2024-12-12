package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class ProductTest {

    private val twoElements = "AB".toList()
    private val threeElements = "ABC".toList()

    @Test
    fun `product of empty list returns empty sequence`() {
        val expected = emptySequence<List<Int>>()
        val actual = emptyList<List<Int>>().product()
        assertEquals(expected, actual)
    }

    @Test
    fun `product of 1 list with 3 elements returns 3 products`() {
        val expected = listOf(
            listOf('A'),
            listOf('B'),
            listOf('C'),
        )

        val actual = listOf(
            threeElements
        ).product().toList()

        assertEquals(expected, actual)
    }

    @Test
    fun `product of 2 lists with empty returns empty sequence`() {
        val expected = emptyList<List<Char>>()

        val actual = listOf(
            twoElements,
            emptyList(),
        ).product().toList()

        assertEquals(expected, actual)
    }

    @Test
    fun `product of 2 lists each with 2 elements returns 4 products`() {
        val expected = listOf(
            listOf('A', 'A'),
            listOf('A', 'B'),
            listOf('B', 'A'),
            listOf('B', 'B'),
        )

        val actual = listOf(
            twoElements,
            twoElements,
        ).product().toList()

        assertEquals(expected, actual)
    }

    @Test
    fun `product of 3 lists each with 2 elements returns 8 products`() {
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

        val actual = listOf(
            twoElements,
            twoElements,
            twoElements,
        ).product().toList()

        assertEquals(expected, actual)
    }

    @Test
    fun `product of 2 lists with 2 and 3 elements respectively returns 6 products`() {
        val expected = listOf(
            listOf('A', 'A'),
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('B', 'A'),
            listOf('B', 'B'),
            listOf('B', 'C'),
        )

        val actual = listOf(
            twoElements,
            threeElements,
        ).product().toList()

        assertEquals(expected, actual)
    }
}
