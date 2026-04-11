package com.github.michaelbull.itertools

import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductTest {

    @Test
    fun `empty outer list returns one empty product`() = runTest {
        assertEquals(
            expected = listOf(emptyList()),
            actual = emptyList<List<Int>>().product().toList(),
        )
    }

    @Test
    fun `any inner empty list returns empty sequence`() = runTest {
        checkAll(200, Arb.list(Arb.list(Arb.int(), 1..3), 0..3), Arb.int(0..3)) { nonEmpty, insertAt ->
            val lists = nonEmpty.toMutableList()
            val index = insertAt.coerceIn(0, lists.size)
            lists.add(index, emptyList())

            assertEquals(
                expected = emptyList(),
                actual = lists.product().toList(),
            )
        }
    }

    @Test
    fun `count equals product of inner list sizes`() = runTest {
        checkAll(200, Arb.list(Arb.list(Arb.int(), 1..4), 1..4)) { lists ->
            val expected = lists.fold(1L) { acc, list -> acc * list.size }

            assertEquals(
                expected = expected,
                actual = lists.product().count().toLong(),
            )
        }
    }

    @Test
    fun `each tuple has length equal to number of inner lists`() = runTest {
        checkAll(200, Arb.list(Arb.list(Arb.int(), 1..4), 1..4)) { lists ->
            lists.product().forEach { tuple ->
                assertEquals(
                    expected = lists.size,
                    actual = tuple.size,
                )
            }
        }
    }

    @Test
    fun `element at position i comes from list i`() = runTest {
        checkAll(200, Arb.list(Arb.list(Arb.int(), 1..4), 1..4)) { lists ->
            lists.product().forEach { tuple ->
                tuple.forEachIndexed { i, element ->
                    assertTrue(
                        actual = element in lists[i],
                        message = "expected element $element at position $i to be in ${lists[i]}",
                    )
                }
            }
        }
    }

    @Test
    fun `products are in lexicographic order`() = runTest {
        checkAll(200, Arb.int(1..3), Arb.int(1..3)) { numLists, listSize ->
            val lists = List(numLists) { (0..<listSize).toList() }
            val results = lists.product().toList()

            results.assertLexicographicallyOrdered()
        }
    }

    @Test
    fun `pair product count equals product of sizes`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..5), Arb.list(Arb.int(), 0..5)) { a, b ->
            assertEquals(
                expected = a.size.toLong() * b.size,
                actual = a.product(b).count().toLong(),
            )
        }
    }

    @Test
    fun `pair product from Pair equals infix product`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..5), Arb.list(Arb.int(), 0..5)) { a, b ->
            assertEquals(
                expected = a.product(b).toList(),
                actual = Pair(a, b).product().toList(),
            )
        }
    }

    @Test
    fun `triple product count equals product of three sizes`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..4), Arb.list(Arb.int(), 0..4), Arb.list(Arb.int(), 0..4)) { a, b, c ->
            assertEquals(
                expected = a.size.toLong() * b.size * c.size,
                actual = a.product(b, c).count().toLong(),
            )
        }
    }

    @Test
    fun `triple product from Triple equals function product`() = runTest {
        checkAll(200, Arb.list(Arb.int(), 0..4), Arb.list(Arb.int(), 0..4), Arb.list(Arb.int(), 0..4)) { a, b, c ->
            assertEquals(
                expected = a.product(b, c).toList(),
                actual = Triple(a, b, c).product().toList(),
            )
        }
    }
}
