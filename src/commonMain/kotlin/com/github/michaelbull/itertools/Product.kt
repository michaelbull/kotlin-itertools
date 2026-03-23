package com.github.michaelbull.itertools

public val EmptyProduct: Sequence<List<Nothing>> = sequenceOf(emptyList())

/**
 * Returns a [Sequence] that yields the Cartesian product of this iterable with the [other] as a [Pair].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * "ABCD".toList()
 *     .product("xy".toList())
 *     .toList()
 *     // [(A, x), (A, y), (B, x), (B, y), (C, x), (C, y), (D, x), (D, y)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/cartesian-product](https://github.com/clojure/math.combinatorics#cartesian-product)
 * - Haskell [sequence](https://hackage.haskell.org/package/base/docs/Control-Monad.html#v:sequence) (via list monad)
 * - Julia [Base.Iterators.product](https://docs.julialang.org/en/v1/base/iterators/#Base.Iterators.product)
 * - Python [itertools.product](https://docs.python.org/3/library/itertools.html#itertools.product)
 * - Ruby [Array#product](https://ruby-doc.org/3.3.0/Array.html#method-i-product)
 * - Rust [Itertools::cartesian_product](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.cartesian_product)
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
 * Returns a [Sequence] that yields the Cartesian product of the iterables in this [Pair].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * Pair("ABCD".toList(), "xy".toList())
 *     .product()
 *     .toList()
 *     // [(A, x), (A, y), (B, x), (B, y), (C, x), (C, y), (D, x), (D, y)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/cartesian-product](https://github.com/clojure/math.combinatorics#cartesian-product)
 * - Haskell [sequence](https://hackage.haskell.org/package/base/docs/Control-Monad.html#v:sequence) (via list monad)
 * - Julia [Base.Iterators.product](https://docs.julialang.org/en/v1/base/iterators/#Base.Iterators.product)
 * - Python [itertools.product](https://docs.python.org/3/library/itertools.html#itertools.product)
 * - Ruby [Array#product](https://ruby-doc.org/3.3.0/Array.html#method-i-product)
 * - Rust [Itertools::cartesian_product](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.cartesian_product)
 */
public fun <A, B> Pair<Iterable<A>, Iterable<B>>.product(): Sequence<Pair<A, B>> {
    return first.product(second)
}

/**
 * Returns a [Sequence] that yields the Cartesian product of this iterable with the [first] and [second] as a [Triple].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * "AB".toList()
 *     .product("CD".toList(), "EF".toList())
 *     .toList()
 *     // [(A, C, E), (A, C, F), (A, D, E), (A, D, F), (B, C, E), (B, C, F), (B, D, E), (B, D, F)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/cartesian-product](https://github.com/clojure/math.combinatorics#cartesian-product)
 * - Haskell [sequence](https://hackage.haskell.org/package/base/docs/Control-Monad.html#v:sequence) (via list monad)
 * - Julia [Base.Iterators.product](https://docs.julialang.org/en/v1/base/iterators/#Base.Iterators.product)
 * - Python [itertools.product](https://docs.python.org/3/library/itertools.html#itertools.product)
 * - Ruby [Array#product](https://ruby-doc.org/3.3.0/Array.html#method-i-product)
 * - Rust [Itertools::cartesian_product](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.cartesian_product)
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
 * Returns a [Sequence] that yields the Cartesian product of the iterables in this [Triple].
 *
 * The product tuples are emitted in lexicographic order according to the order of this iterable.
 *
 * ```
 * Triple("AB".toList(), "CD".toList(), "EF".toList())
 *     .product()
 *     .toList()
 *     // [(A, C, E), (A, C, F), (A, D, E), (A, D, F), (B, C, E), (B, C, F), (B, D, E), (B, D, F)]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/cartesian-product](https://github.com/clojure/math.combinatorics#cartesian-product)
 * - Haskell [sequence](https://hackage.haskell.org/package/base/docs/Control-Monad.html#v:sequence) (via list monad)
 * - Julia [Base.Iterators.product](https://docs.julialang.org/en/v1/base/iterators/#Base.Iterators.product)
 * - Python [itertools.product](https://docs.python.org/3/library/itertools.html#itertools.product)
 * - Ruby [Array#product](https://ruby-doc.org/3.3.0/Array.html#method-i-product)
 * - Rust [Itertools::cartesian_product](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.cartesian_product)
 */
public fun <A, B, C> Triple<Iterable<A>, Iterable<B>, Iterable<C>>.product(): Sequence<Triple<A, B, C>> {
    return first.product(second, third)
}

/**
 * Returns a [Sequence] that yields the Cartesian product of the lists in this list.
 *
 * The product tuples are emitted in lexicographic order according to the order of this list.
 *
 * ```
 * listOf("AB".toList(), "CD".toList())
 *     .product()
 *     .toList()
 *     // [[A, C], [A, D], [B, C], [B, D]]
 *
 * listOf(listOf(1, 2), emptyList())
 *     .product()
 *     .toList()
 *     // []
 *
 * emptyList<List<Int>>()
 *     .product()
 *     .toList()
 *     // [[]]
 * ```
 *
 * - Clojure [clojure.math.combinatorics/cartesian-product](https://github.com/clojure/math.combinatorics#cartesian-product)
 * - Haskell [sequence](https://hackage.haskell.org/package/base/docs/Control-Monad.html#v:sequence) (via list monad)
 * - Julia [Base.Iterators.product](https://docs.julialang.org/en/v1/base/iterators/#Base.Iterators.product)
 * - Python [itertools.product](https://docs.python.org/3/library/itertools.html#itertools.product)
 * - Ruby [Array#product](https://ruby-doc.org/3.3.0/Array.html#method-i-product)
 * - Rust [Itertools::cartesian_product](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.cartesian_product)
 */
public fun <T> List<List<T>>.product(): Sequence<List<T>> {
    return if (isEmpty()) {
        EmptyProduct
    } else if (any(List<T>::isEmpty)) {
        emptySequence()
    } else {
        sequence {
            val indices = IntArray(size) { 0 }

            yield(product(indices))

            while (advance(indices, indices.lastIndex)) {
                yield(product(indices))
            }
        }
    }
}

private fun <T> List<List<T>>.product(indices: IntArray): List<T> {
    return indices.mapIndexed { a, b ->
        this[a][b]
    }
}

private tailrec fun <T> List<List<T>>.advance(indices: IntArray, index: Int): Boolean {
    if (index < 0) return false

    indices[index]++

    return if (exhausted(indices, index)) {
        indices[index] = 0
        advance(indices, index - 1)
    } else {
        true
    }
}

private fun <T> List<List<T>>.exhausted(indices: IntArray, index: Int): Boolean {
    return indices[index] >= get(index).size
}
