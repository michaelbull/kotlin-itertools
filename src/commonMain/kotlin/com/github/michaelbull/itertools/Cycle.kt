package com.github.michaelbull.itertools

/**
 * Returns a [Sequence] that yields elements from this iterable repeatedly, cycling back to the start after reaching
 * the end.
 *
 * The returned sequence is infinite. An empty iterable yields an empty sequence.
 *
 * ```
 * "ABC".toList()
 *     .cycle()
 *     .take(7)
 *     .toList()
 *     // [A, B, C, A, B, C, A]
 *
 * emptyList<Int>()
 *     .cycle()
 *     .toList()
 *     // []
 * ```
 *
 * - Python [itertools.cycle](https://docs.python.org/3/library/itertools.html#itertools.cycle)
 * - Rust [Iterator::cycle](https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.cycle)
 * - Ruby [Array#cycle](https://ruby-doc.org/3.3.0/Array.html#method-i-cycle)
 */
public fun <T> Iterable<T>.cycle(): Sequence<T> {
    val first = iterator()

    return if (!first.hasNext()) {
        emptySequence()
    } else {
        sequence {
            yieldAll(first)

            while (true) {
                yieldAll(this@cycle.iterator())
            }
        }
    }
}
