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
 * - Clojure [clojure.core/cycle](https://clojuredocs.org/clojure.core/cycle)
 * - Elixir [Stream.cycle](https://hexdocs.pm/elixir/Stream.html#cycle/1)
 * - Haskell [Prelude.cycle](https://hackage.haskell.org/package/base/docs/Prelude.html#v:cycle)
 * - Python [itertools.cycle](https://docs.python.org/3/library/itertools.html#itertools.cycle)
 * - Ruby [Array#cycle](https://ruby-doc.org/3.3.0/Array.html#method-i-cycle)
 * - Rust [Iterator::cycle](https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.cycle)
 * - Swift [cycled()](https://github.com/apple/swift-algorithms/blob/main/Guides/Cycle.md)
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
