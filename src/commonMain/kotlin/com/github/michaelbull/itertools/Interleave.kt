package com.github.michaelbull.itertools

/**
 * Returns a [List] that contains elements from this iterable and the [other] iterable alternating, starting with this
 * iterable.
 *
 * Like [interleaveLongest], elements are drawn alternately from each iterable.
 *
 * Unlike [interleaveLongest], this stops when the shorter iterable is exhausted.
 *
 * ```
 * listOf(1, 3, 5)
 *     .interleave(listOf(2, 4, 6))
 *     // [1, 2, 3, 4, 5, 6]
 *
 * listOf(1, 3, 5)
 *     .interleave(listOf(2, 4))
 *     // [1, 2, 3, 4]
 *
 * emptyList<Int>()
 *     .interleave(listOf(1, 2))
 *     // []
 * ```
 *
 * - Clojure [clojure.core/interleave](https://clojuredocs.org/clojure.core/interleave)
 * - Julia [IterTools.interleave](https://juliamath.github.io/Combinatorics.jl/stable/)
 * - Python [more_itertools.interleave](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.interleave)
 * - Rust [itertools::interleave](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.interleave)
 * - Swift [interleave](https://github.com/apple/swift-algorithms/blob/main/Guides/Interleave.md)
 */
public fun <T> Iterable<T>.interleave(other: Iterable<T>): List<T> {
    return interleaveTo(ArrayList(), other)
}

/**
 * Appends elements from this iterable and the [other] iterable alternating, starting with this iterable, to the given
 * [destination]. Stops when the shorter iterable is exhausted.
 *
 * Like [interleaveLongestTo], elements are drawn alternately from each iterable.
 *
 * Unlike [interleaveLongestTo], this stops when the shorter iterable is exhausted.
 *
 * ```
 * val destination = mutableListOf(0)
 *
 * listOf(1, 3)
 *     .interleaveTo(destination, listOf(2, 4))
 *     // [0, 1, 2, 3, 4]
 * ```
 *
 * - Clojure [clojure.core/interleave](https://clojuredocs.org/clojure.core/interleave)
 * - Julia [IterTools.interleave](https://juliamath.github.io/Combinatorics.jl/stable/)
 * - Python [more_itertools.interleave](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.interleave)
 * - Rust [itertools::interleave](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.interleave)
 * - Swift [interleave](https://github.com/apple/swift-algorithms/blob/main/Guides/Interleave.md)
 */
public fun <T, C : MutableCollection<in T>> Iterable<T>.interleaveTo(destination: C, other: Iterable<T>): C {
    val a = this.iterator()
    val b = other.iterator()

    while (a.hasNext() && b.hasNext()) {
        destination.add(a.next())
        destination.add(b.next())
    }

    return destination
}

/**
 * Returns a [Sequence] that yields elements from this sequence and the [other] sequence alternating, starting with
 * this sequence.
 *
 * Like [Sequence.interleaveLongest], elements are drawn alternately from each sequence.
 *
 * Unlike [Sequence.interleaveLongest], this stops when the shorter sequence is exhausted.
 *
 * ```
 * sequenceOf(1, 3, 5)
 *     .interleave(sequenceOf(2, 4, 6))
 *     .toList()
 *     // [1, 2, 3, 4, 5, 6]
 * ```
 *
 * - Clojure [clojure.core/interleave](https://clojuredocs.org/clojure.core/interleave)
 * - Julia [IterTools.interleave](https://juliamath.github.io/Combinatorics.jl/stable/)
 * - Python [more_itertools.interleave](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.interleave)
 * - Rust [itertools::interleave](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.interleave)
 * - Swift [interleave](https://github.com/apple/swift-algorithms/blob/main/Guides/Interleave.md)
 */
public fun <T> Sequence<T>.interleave(other: Sequence<T>): Sequence<T> {
    return sequence {
        val a = this@interleave.iterator()
        val b = other.iterator()

        while (a.hasNext() && b.hasNext()) {
            yield(a.next())
            yield(b.next())
        }
    }
}
