package com.github.michaelbull.itertools

/**
 * Returns a sequence that yields the Cartesian product of this iterable with the [other] as a [Pair].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * "ABCD".toList()
 *     .product("xy".toList())
 *     .toList()
 *     // [(A, x), (A, y), (B, x), (B, y), (C, x), (C, y), (D, x), (D, y)]
 * ```
 */
public infix fun <A, B> Iterable<A>.product(other: Iterable<B>): Sequence<Pair<A, B>> {
    return sequence {
        for (a in this@product) {
            for (b in other) {
                yield(Pair(a, b))
            }
        }
    }
}

/**
 * Returns a sequence that yields the Cartesian product of the iterables in this [Pair].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * Pair("ABCD".toList(), "xy".toList())
 *     .product()
 *     .toList()
 *     // [(A, x), (A, y), (B, x), (B, y), (C, x), (C, y), (D, x), (D, y)]
 * ```
 */
public fun <A, B> Pair<Iterable<A>, Iterable<B>>.product(): Sequence<Pair<A, B>> {
    return sequence {
        for (a in first) {
            for (b in second) {
                yield(Pair(a, b))
            }
        }
    }
}

/**
 * Returns a sequence that yields the Cartesian product of this iterable with the [first] and [second] as a [Triple].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * "AB".toList()
 *     .product("CD".toList(), "EF".toList())
 *     .toList()
 *     // [(A, C, E), (A, C, F), (A, D, E), (A, D, F), (B, C, E), (B, C, F), (B, D, E), (B, D, F)]
 * ```
 */
public fun <A, B, C> Iterable<A>.product(first: Iterable<B>, second: Iterable<C>): Sequence<Triple<A, B, C>> {
    return sequence {
        for (a in this@product) {
            for (b in first) {
                for (c in second) {
                    yield(Triple(a, b, c))
                }
            }
        }
    }
}

/**
 * Returns a sequence that yields the Cartesian product of the iterables in this [Triple].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * Triple("AB".toList(), "CD".toList(), "EF".toList())
 *     .product()
 *     .toList()
 *     // [(A, C, E), (A, C, F), (A, D, E), (A, D, F), (B, C, E), (B, C, F), (B, D, E), (B, D, F)]
 * ```
 */
public fun <A, B, C> Triple<Iterable<A>, Iterable<B>, Iterable<C>>.product(): Sequence<Triple<A, B, C>> {
    return sequence {
        for (a in first) {
            for (b in second) {
                for (c in third) {
                    yield(Triple(a, b, c))
                }
            }
        }
    }
}

/**
 * Returns a sequence that yields the Cartesian product of the lists in this list.
 *
 * The product tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * listOf("ABCD".toList(), "xy".toList())
 *     .product()
 *     .toList()
 *     // [[A, x], [A, y], [B, x], [B, y], [C, x], [C, y], [D, x], [D, y]]
 * ```
 */
public fun <T> List<List<T>>.product(): Sequence<List<T>> {
    return if (isEmpty() || any(List<T>::isEmpty)) {
        emptySequence()
    } else {
        sequence {
            val indices = IntArray(size) { 0 }
            var searching = true

            yield(product(indices))

            while (searching) {
                var found = false
                var index = indices.size - 1

                while (index >= 0 && !found) {
                    indices[index]++

                    if (indices[index] >= get(index).size) {
                        indices[index] = 0
                        index--
                    } else {
                        yield(product(indices))
                        found = true
                    }
                }

                if (!found) {
                    searching = false
                }
            }
        }
    }
}

private fun <T> List<List<T>>.product(indices: IntArray): List<T> {
    return indices.mapIndexed { a, b ->
        this[a][b]
    }
}
