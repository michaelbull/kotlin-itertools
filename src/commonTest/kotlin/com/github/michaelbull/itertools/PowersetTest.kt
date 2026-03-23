package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class PowersetTest {

    @Test
    fun `powerset of 0 elements returns 1 subset`() {
        val expected = listOf(emptyList<Char>())
        val actual = emptyList<Char>().powerset().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `powerset of 1 element returns 2 subsets`() {
        val expected = listOf(
            emptyList(),
            listOf('A'),
        )

        val actual = "A".toList().powerset().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `powerset of 2 elements returns 4 subsets`() {
        val expected = listOf(
            emptyList(),
            listOf('A'),
            listOf('B'),
            listOf('A', 'B'),
        )

        val actual = "AB".toList().powerset().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `powerset of 3 elements returns 8 subsets`() {
        val expected = listOf(
            emptyList(),
            listOf('A'),
            listOf('B'),
            listOf('C'),
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('B', 'C'),
            listOf('A', 'B', 'C'),
        )

        val actual = "ABC".toList().powerset().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `powerset of 4 elements returns 16 subsets`() {
        val expected = listOf(
            emptyList(),
            listOf('A'),
            listOf('B'),
            listOf('C'),
            listOf('D'),
            listOf('A', 'B'),
            listOf('A', 'C'),
            listOf('A', 'D'),
            listOf('B', 'C'),
            listOf('B', 'D'),
            listOf('C', 'D'),
            listOf('A', 'B', 'C'),
            listOf('A', 'B', 'D'),
            listOf('A', 'C', 'D'),
            listOf('B', 'C', 'D'),
            listOf('A', 'B', 'C', 'D'),
        )

        val actual = "ABCD".toList().powerset().toList()
        assertEquals(expected, actual)
    }
}