package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class IntersperseTest {

    @Test
    fun `intersperse on empty list returns empty list`() {
        val expected = emptyList<Char>()
        val actual = emptyList<Char>().intersperse('-')
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperse on single element returns single element`() {
        val expected = listOf('A')
        val actual = listOf('A').intersperse('-')
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperse on two elements inserts separator`() {
        val expected = listOf('A', '-', 'B')
        val actual = "AB".toList().intersperse('-')
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperse on three elements inserts two separators`() {
        val expected = listOf('A', '-', 'B', '-', 'C')
        val actual = "ABC".toList().intersperse('-')
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperse on sequence returns lazy sequence`() {
        val expected = listOf('A', '-', 'B', '-', 'C')
        val actual = "ABC".toList().asSequence().intersperse('-').toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperse on empty sequence returns empty sequence`() {
        val expected = emptyList<Char>()
        val actual = emptySequence<Char>().intersperse('-').toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperse on single element sequence returns single element`() {
        val expected = listOf('A')
        val actual = sequenceOf('A').intersperse('-').toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperseTo appends to existing collection`() {
        val destination = mutableListOf('X')
        val expected = listOf('X', 'A', '-', 'B', '-', 'C')
        val actual = "ABC".toList().intersperseTo(destination, '-')
        assertEquals(expected, actual)
    }

    @Test
    fun `intersperseTo on empty list leaves destination unchanged`() {
        val destination = mutableListOf('X')
        val expected = listOf('X')
        val actual = emptyList<Char>().intersperseTo(destination, '-')
        assertEquals(expected, actual)
    }
}
