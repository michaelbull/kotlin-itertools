package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class CombinationsTest {

    @Test
    fun pairCombinations() {
        val input = "ABCD".toList()

        val expected = listOf(
            Pair('A', 'B'),
            Pair('A', 'C'),
            Pair('A', 'D'),
            Pair('B', 'C'),
            Pair('B', 'D'),
            Pair('C', 'D'),
        )

        val actual = input
            .pairCombinations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun tripleCombinations() {
        val input = "ABCD".toList()

        val expected = listOf(
            Triple('A', 'B', 'C'),
            Triple('A', 'B', 'D'),
            Triple('A', 'C', 'D'),
            Triple('B', 'C', 'D'),
        )

        val actual = input
            .tripleCombinations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun combinations() {
        val input = (0..3).toList()

        val expected = listOf(
            listOf(0, 1, 2),
            listOf(0, 1, 3),
            listOf(0, 2, 3),
            listOf(1, 2, 3),
        )

        val actual = input
            .combinations(length = 3)
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun emptyCombinations() {
        val input = emptyList<Nothing>()

        val expected = emptyList<List<Nothing>>()

        val actual = input
            .combinations()
            .toList()

        assertEquals(expected, actual)
    }

    @Test
    fun excessCombinations() {
        val input = "ABCD".toList()

        val expected = emptyList<List<Char>>()

        val actual = input
            .combinations(input.size + 2)
            .toList()

        assertEquals(expected, actual)
    }
}
