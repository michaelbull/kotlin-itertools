package com.github.michaelbull.itertools

/**
 * Returns a [List] that contains the elements of this iterable with [separator] inserted between each pair of adjacent
 * elements.
 *
 * ```
 * "ABC".toList()
 *     .intersperse('-')
 *     // [A, -, B, -, C]
 *
 * emptyList<Char>()
 *     .intersperse('-')
 *     // []
 *
 * listOf('A')
 *     .intersperse('-')
 *     // [A]
 * ```
 *
 * - Clojure [clojure.core/interpose](https://clojuredocs.org/clojure.core/interpose)
 * - Elixir [Enum.intersperse](https://hexdocs.pm/elixir/Enum.html#intersperse/2)
 * - Haskell [Data.List.intersperse](https://hackage.haskell.org/package/base/docs/Data-List.html#v:intersperse)
 * - Python [more_itertools.intersperse](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.intersperse)
 * - Rust [itertools::intersperse](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.intersperse)
 * - Swift [interspersed(with:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Intersperse.md)
 */
public fun <T> Iterable<T>.intersperse(separator: T): List<T> {
    return intersperseTo(ArrayList(), separator)
}

/**
 * Appends the elements of this iterable with [separator] inserted between each pair of adjacent elements to the given
 * [destination].
 *
 * ```
 * val destination = mutableListOf('X')
 *
 * "ABC".toList()
 *     .intersperseTo(destination, '-')
 *     // [X, A, -, B, -, C]
 * ```
 *
 * - Clojure [clojure.core/interpose](https://clojuredocs.org/clojure.core/interpose)
 * - Elixir [Enum.intersperse](https://hexdocs.pm/elixir/Enum.html#intersperse/2)
 * - Haskell [Data.List.intersperse](https://hackage.haskell.org/package/base/docs/Data-List.html#v:intersperse)
 * - Python [more_itertools.intersperse](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.intersperse)
 * - Rust [itertools::intersperse](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.intersperse)
 * - Swift [interspersed(with:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Intersperse.md)
 */
public fun <T, C : MutableCollection<in T>> Iterable<T>.intersperseTo(destination: C, separator: T): C {
    val iterator = iterator()

    if (iterator.hasNext()) {
        destination.add(iterator.next())

        while (iterator.hasNext()) {
            destination.add(separator)
            destination.add(iterator.next())
        }
    }

    return destination
}

/**
 * Returns a [Sequence] that yields the elements of this sequence with [separator] inserted between each pair of
 * adjacent elements.
 *
 * ```
 * "ABC".toList()
 *     .asSequence()
 *     .intersperse('-')
 *     .toList()
 *     // [A, -, B, -, C]
 * ```
 *
 * - Clojure [clojure.core/interpose](https://clojuredocs.org/clojure.core/interpose)
 * - Elixir [Enum.intersperse](https://hexdocs.pm/elixir/Enum.html#intersperse/2)
 * - Haskell [Data.List.intersperse](https://hackage.haskell.org/package/base/docs/Data-List.html#v:intersperse)
 * - Python [more_itertools.intersperse](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.intersperse)
 * - Rust [itertools::intersperse](https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.intersperse)
 * - Swift [interspersed(with:)](https://github.com/apple/swift-algorithms/blob/main/Guides/Intersperse.md)
 */
public fun <T> Sequence<T>.intersperse(separator: T): Sequence<T> {
    return sequence {
        val iterator = this@intersperse.iterator()

        if (iterator.hasNext()) {
            yield(iterator.next())

            while (iterator.hasNext()) {
                yield(separator)
                yield(iterator.next())
            }
        }
    }
}
