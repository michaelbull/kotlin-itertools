package com.github.michaelbull.itertools

import kotlin.test.Test
import kotlin.test.assertEquals

class DistinctPermutationsTest {

    @Test
    fun `distinct permutations of 0 elements returns 1 permutation`() {
        val expected = listOf(emptyList<Char>())
        val actual = emptyList<Char>().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 1 element returns 1 permutation`() {
        val expected = listOf(listOf('A'))
        val actual = "A".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 2 distinct elements returns 2 permutations`() {
        val expected = listOf(
            listOf('A', 'B'),
            listOf('B', 'A'),
        )

        val actual = "AB".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 2 identical elements returns 1 permutation`() {
        val expected = listOf(listOf('A', 'A'))
        val actual = "AA".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 3 distinct elements returns 6 permutations`() {
        val expected = listOf(
            listOf('A', 'B', 'C'),
            listOf('A', 'C', 'B'),
            listOf('B', 'A', 'C'),
            listOf('B', 'C', 'A'),
            listOf('C', 'A', 'B'),
            listOf('C', 'B', 'A'),
        )

        val actual = "ABC".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 3 elements with 1 duplicate returns 3 permutations`() {
        val expected = listOf(
            listOf('A', 'A', 'B'),
            listOf('A', 'B', 'A'),
            listOf('B', 'A', 'A'),
        )

        val actual = "AAB".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 3 identical elements returns 1 permutation`() {
        val expected = listOf(listOf('A', 'A', 'A'))
        val actual = "AAA".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 4 elements with 2 pairs returns 6 permutations`() {
        val expected = listOf(
            listOf('A', 'A', 'B', 'B'),
            listOf('A', 'B', 'A', 'B'),
            listOf('A', 'B', 'B', 'A'),
            listOf('B', 'A', 'A', 'B'),
            listOf('B', 'A', 'B', 'A'),
            listOf('B', 'B', 'A', 'A'),
        )

        val actual = "AABB".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of unsorted input returns sorted permutations`() {
        val expected = listOf(
            listOf('A', 'A', 'B'),
            listOf('A', 'B', 'A'),
            listOf('B', 'A', 'A'),
        )

        val actual = "BAA".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 4 elements with 1 triple returns 4 permutations`() {
        val expected = listOf(
            listOf('A', 'A', 'A', 'B'),
            listOf('A', 'A', 'B', 'A'),
            listOf('A', 'B', 'A', 'A'),
            listOf('B', 'A', 'A', 'A'),
        )

        val actual = "AAAB".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `distinct permutations of 4 distinct elements returns 24 permutations`() {
        val expected = listOf(
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

        val actual = "ABCD".toList().distinctPermutations().toList()
        assertEquals(expected, actual)
    }
}
