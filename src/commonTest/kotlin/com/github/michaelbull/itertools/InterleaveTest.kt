package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class InterleaveTest {

    @Test
    fun `interleave two equal length lists`() {
        val expected = listOf(1, 2, 3, 4, 5, 6)
        val actual = listOf(1, 3, 5).interleave(listOf(2, 4, 6))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleave with second shorter stops at second`() {
        val expected = listOf(1, 2, 3, 4)
        val actual = listOf(1, 3, 5).interleave(listOf(2, 4))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleave with first shorter stops at first`() {
        val expected = listOf(1, 2)
        val actual = listOf(1).interleave(listOf(2, 4, 6))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleave with empty first returns empty`() {
        val expected = emptyList<Int>()
        val actual = emptyList<Int>().interleave(listOf(1, 2))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleave with empty second returns empty`() {
        val expected = emptyList<Int>()
        val actual = listOf(1, 2).interleave(emptyList())
        assertEquals(expected, actual)
    }

    @Test
    fun `interleave both empty returns empty`() {
        val expected = emptyList<Int>()
        val actual = emptyList<Int>().interleave(emptyList())
        assertEquals(expected, actual)
    }

    @Test
    fun `interleave sequences stops at shorter`() {
        val expected = listOf(1, 2, 3, 4)
        val actual = sequenceOf(1, 3, 5).interleave(sequenceOf(2, 4)).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest two equal length lists`() {
        val expected = listOf(1, 2, 3, 4, 5, 6)
        val actual = listOf(1, 3, 5).interleaveLongest(listOf(2, 4, 6))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest continues past shorter first`() {
        val expected = listOf(1, 2, 4, 6)
        val actual = listOf(1).interleaveLongest(listOf(2, 4, 6))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest continues past shorter second`() {
        val expected = listOf(1, 2, 3, 4, 5)
        val actual = listOf(1, 3, 5).interleaveLongest(listOf(2, 4))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest with empty first returns second`() {
        val expected = listOf(1, 2)
        val actual = emptyList<Int>().interleaveLongest(listOf(1, 2))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest with empty second returns first`() {
        val expected = listOf(1, 2)
        val actual = listOf(1, 2).interleaveLongest(emptyList())
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest both empty returns empty`() {
        val expected = emptyList<Int>()
        val actual = emptyList<Int>().interleaveLongest(emptyList())
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongest sequences continues past shorter`() {
        val expected = listOf(1, 2, 3, 4, 5)
        val actual = sequenceOf(1, 3, 5).interleaveLongest(sequenceOf(2, 4)).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveTo appends to existing collection`() {
        val destination = mutableListOf(0)
        val expected = listOf(0, 1, 2, 3, 4)
        val actual = listOf(1, 3).interleaveTo(destination, listOf(2, 4))
        assertEquals(expected, actual)
    }

    @Test
    fun `interleaveLongestTo appends to existing collection`() {
        val destination = mutableListOf(0)
        val expected = listOf(0, 1, 2, 3, 4, 5)
        val actual = listOf(1, 3, 5).interleaveLongestTo(destination, listOf(2, 4))
        assertEquals(expected, actual)
    }
}
