package com.github.michaelbull.itertools

/**
 * Returns a [List] that contains elements from this iterable and the [other] iterable alternating, starting with this
 * iterable.
 *
 * Like [interleave], elements are drawn alternately from each iterable.
 *
 * Unlike [interleave], this continues until both iterables are exhausted.
 *
 * ```
 * listOf(1, 3, 5)
 *     .interleaveLongest(listOf(2, 4))
 *     // [1, 2, 3, 4, 5]
 *
 * listOf(1)
 *     .interleaveLongest(listOf(2, 4, 6))
 *     // [1, 2, 4, 6]
 *
 * emptyList<Int>()
 *     .interleaveLongest(listOf(1, 2))
 *     // [1, 2]
 * ```
 *
 * - Python [more_itertools.interleave_longest](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.interleave_longest)
 * - Rust [itertools::interleave](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.interleave) (default behaviour)
 */
public fun <T> Iterable<T>.interleaveLongest(other: Iterable<T>): List<T> {
    return interleaveLongestTo(ArrayList(), other)
}

/**
 * Appends elements from this iterable and the [other] iterable alternating, starting with this iterable, to the given
 * [destination]. Continues until both iterables are exhausted.
 *
 * Like [interleaveTo], elements are drawn alternately from each iterable.
 *
 * Unlike [interleaveTo], this continues until both iterables are exhausted.
 *
 * ```
 * val destination = mutableListOf(0)
 *
 * listOf(1, 3, 5)
 *     .interleaveLongestTo(destination, listOf(2, 4))
 *     // [0, 1, 2, 3, 4, 5]
 * ```
 *
 * - Python [more_itertools.interleave_longest](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.interleave_longest)
 * - Rust [itertools::interleave](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.interleave) (default behaviour)
 */
public fun <T, C : MutableCollection<in T>> Iterable<T>.interleaveLongestTo(destination: C, other: Iterable<T>): C {
    val a = this.iterator()
    val b = other.iterator()

    while (a.hasNext() || b.hasNext()) {
        if (a.hasNext()) {
            destination.add(a.next())
        }

        if (b.hasNext()) {
            destination.add(b.next())
        }
    }

    return destination
}

/**
 * Returns a [Sequence] that yields elements from this sequence and the [other] sequence alternating, starting with
 * this sequence.
 *
 * Like [Sequence.interleave], elements are drawn alternately from each sequence.
 *
 * Unlike [Sequence.interleave], this continues until both sequences are exhausted.
 *
 * ```
 * sequenceOf(1, 3, 5)
 *     .interleaveLongest(sequenceOf(2, 4))
 *     .toList()
 *     // [1, 2, 3, 4, 5]
 * ```
 *
 * - Python [more_itertools.interleave_longest](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.interleave_longest)
 * - Rust [itertools::interleave](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.interleave) (default behaviour)
 */
public fun <T> Sequence<T>.interleaveLongest(other: Sequence<T>): Sequence<T> {
    return sequence {
        val a = this@interleaveLongest.iterator()
        val b = other.iterator()

        while (a.hasNext() || b.hasNext()) {
            if (a.hasNext()) {
                yield(a.next())
            }

            if (b.hasNext()) {
                yield(b.next())
            }
        }
    }
}
