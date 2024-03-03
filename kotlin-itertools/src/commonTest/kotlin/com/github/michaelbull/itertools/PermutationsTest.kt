package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class PermutationsTest {

    @Test
    fun pairPermutations() {
        val input = "ABCD".toList()

        val expected = listOf(
            Pair('A', 'B'),
            Pair('A', 'C'),
            Pair('A', 'D'),
            Pair('B', 'A'),
            Pair('B', 'C'),
            Pair('B', 'D'),
            Pair('C', 'A'),
            Pair('C', 'B'),
            Pair('C', 'D'),
            Pair('D', 'A'),
            Pair('D', 'B'),
            Pair('D', 'C'),
        )

        val actual = input
            .pairPermutations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun triplePermutations() {
        val input = "ABCD".toList()

        val expected = listOf(
            Triple('A', 'B', 'C'),
            Triple('A', 'B', 'D'),
            Triple('A', 'C', 'B'),
            Triple('A', 'C', 'D'),
            Triple('A', 'D', 'B'),
            Triple('A', 'D', 'C'),
            Triple('B', 'A', 'C'),
            Triple('B', 'A', 'D'),
            Triple('B', 'C', 'A'),
            Triple('B', 'C', 'D'),
            Triple('B', 'D', 'A'),
            Triple('B', 'D', 'C'),
            Triple('C', 'A', 'B'),
            Triple('C', 'A', 'D'),
            Triple('C', 'B', 'A'),
            Triple('C', 'B', 'D'),
            Triple('C', 'D', 'A'),
            Triple('C', 'D', 'B'),
            Triple('D', 'A', 'B'),
            Triple('D', 'A', 'C'),
            Triple('D', 'B', 'A'),
            Triple('D', 'B', 'C'),
            Triple('D', 'C', 'A'),
            Triple('D', 'C', 'B'),
        )

        val actual = input
            .triplePermutations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun permutations() {
        val input = (0..2).toList()

        sequenceOf(5, 6)

        val expected = listOf(
            listOf(0, 1, 2),
            listOf(0, 2, 1),
            listOf(1, 0, 2),
            listOf(1, 2, 0),
            listOf(2, 0, 1),
            listOf(2, 1, 0),
        )

        val actual = input
            .permutations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun emptyPermutations() {
        val input = emptyList<Nothing>()

        val expected = emptyList<Nothing>()

        val actual = input
            .permutations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun excessPermutations() {
        val input = "ABCD".toList()

        val expected = emptyList<List<Char>>()

        val actual = input
            .permutations(input.size + 2)
            .toList()

        assertEquals(expected, actual)
    }
}
