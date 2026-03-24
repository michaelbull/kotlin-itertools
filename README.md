# kotlin-itertools

[![Maven Central](https://img.shields.io/maven-central/v/com.michael-bull.kotlin-itertools/kotlin-itertools.svg)](https://search.maven.org/search?q=g:com.michael-bull.kotlin-itertools)
[![CI](https://github.com/michaelbull/kotlin-itertools/actions/workflows/ci.yaml/badge.svg)](https://github.com/michaelbull/kotlin-itertools/actions/workflows/ci.yaml)
[![License](https://img.shields.io/github/license/michaelbull/kotlin-itertools.svg)](https://github.com/michaelbull/kotlin-itertools/blob/master/LICENSE)

![badge][badge-android]
![badge][badge-jvm]
![badge][badge-js]
![badge][badge-nodejs]
![badge][badge-linux]
![badge][badge-windows]
![badge][badge-wasm]
![badge][badge-ios]
![badge][badge-mac]
![badge][badge-tvos]
![badge][badge-watchos]
![badge][badge-js-ir]
![badge][badge-android-native]
![badge][badge-apple-silicon]

Multiplatform iterator tools and combinatoric sequences for Kotlin.

Inspired by:

- [Clojure math.combinatorics][clojure-combinatorics]
- [Elixir Enum][elixir-enum]
- [Haskell Math.Combinat][haskell-combinat]
- [Julia Combinatorics.jl][julia-combinatorics]
- [Python itertools][python-itertools]
- [Ruby Array][ruby-array]
- [Rust itertools][rust-itertools]
- [Scala SeqOps][scala-seqops]
- [Swift Algorithms][swift-algorithms]

Initially built as part of my solutions for [Advent of Code 2023][advent-2023].

## Installation

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-itertools:kotlin-itertools:1.0.4")
}
```

## Iterator Tools

### Cycle

Returns an infinite sequence that yields elements from this iterable repeatedly, cycling back to the start after
reaching the end.

```kotlin
fun <T> Iterable<T>.cycle(): Sequence<T>

fun <T> Iterable<T>.cycle(times: Int): Sequence<T>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.cycle

// [A, B, C, A, B, C, A]
fun example1(): List<Char> {
    return "ABC".toList()
        .cycle()
        .take(7)
        .toList()
}

// [A, B, C, A, B, C, A, B, C]
fun example2(): List<Char> {
    return "ABC".toList()
        .cycle(times = 3)
        .toList()
}
```

</details>

### Interleave

Returns a list (or sequence) that alternates elements from two iterables. See also
[Interleave Longest](#interleave-longest) to continue until both are exhausted.

```kotlin
fun <T> Iterable<T>.interleave(other: Iterable<T>): List<T>
fun <T, C : MutableCollection<in T>> Iterable<T>.interleaveTo(destination: C, other: Iterable<T>): C
fun <T> Sequence<T>.interleave(other: Sequence<T>): Sequence<T>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.interleave

// [1, 2, 3, 4, 5, 6]
fun example1(): List<Int> {
    return listOf(1, 3, 5)
        .interleave(listOf(2, 4, 6))
}

// [1, 2, 3, 4]
fun example2(): List<Int> {
    return listOf(1, 3, 5)
        .interleave(listOf(2, 4))
}
```

</details>

### Interleave Longest

Returns a list (or sequence) that alternates elements from two iterables, continuing until both are exhausted. See also
[Interleave](#interleave) to stop at the shorter.

```kotlin
fun <T> Iterable<T>.interleaveLongest(other: Iterable<T>): List<T>
fun <T, C : MutableCollection<in T>> Iterable<T>.interleaveLongestTo(destination: C, other: Iterable<T>): C
fun <T> Sequence<T>.interleaveLongest(other: Sequence<T>): Sequence<T>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.interleaveLongest

// [1, 2, 3, 4, 5]
fun example1(): List<Int> {
    return listOf(1, 3, 5)
        .interleaveLongest(listOf(2, 4))
}

// [1, 2, 4, 6]
fun example2(): List<Int> {
    return listOf(1)
        .interleaveLongest(listOf(2, 4, 6))
}
```

</details>

### Intersperse

Returns a list (or sequence) with a separator inserted between each pair of adjacent elements.

```kotlin
fun <T> Iterable<T>.intersperse(separator: T): List<T>
fun <T, C : MutableCollection<in T>> Iterable<T>.intersperseTo(destination: C, separator: T): C
fun <T> Sequence<T>.intersperse(separator: T): Sequence<T>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.intersperse

// [A, -, B, -, C]
fun example1(): List<Char> {
    return "ABC".toList()
        .intersperse('-')
}
```

</details>

## Combinatoric Sequences

### Combinations

Returns a sequence that yields `k`-sized combinations of elements from this list. See also
[Combinations with Replacement](#combinations-with-replacement) for allowing repeated elements, and
[Powerset](#powerset) for all subsets across all sizes.

The combination tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.combinations(k: Int = size): Sequence<List<T>>

fun <T> List<T>.pairCombinations(): Sequence<Pair<T, T>>

fun <T> List<T>.tripleCombinations(): Sequence<Triple<T, T, T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.combinations
import com.github.michaelbull.itertools.pairCombinations
import com.github.michaelbull.itertools.tripleCombinations

// [[A, B], [A, C], [B, C]]
fun example1(): List<List<Char>> {
    return "ABC".toList()
        .combinations(k = 2)
        .toList()
}

// [(A, B), (A, C), (B, C)]
fun example2(): List<Pair<Char, Char>> {
    return "ABC".toList()
        .pairCombinations()
        .toList()
}

// [(A, B, C)]
fun example3(): List<Triple<Char, Char, Char>> {
    return "ABC".toList()
        .tripleCombinations()
        .toList()
}
```

</details>

### Combinations with Replacement

Returns a sequence that yields `k`-sized combinations of elements from this list, allowing individual elements to be
repeated. See also [Combinations](#combinations) for without replacement.

The combination tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.combinationsWithReplacement(k: Int = size): Sequence<List<T>>

fun <T> List<T>.pairCombinationsWithReplacement(): Sequence<Pair<T, T>>

fun <T> List<T>.tripleCombinationsWithReplacement(): Sequence<Triple<T, T, T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.combinationsWithReplacement
import com.github.michaelbull.itertools.pairCombinationsWithReplacement
import com.github.michaelbull.itertools.tripleCombinationsWithReplacement

// [[A, A], [A, B], [A, C], [B, B], [B, C], [C, C]]
fun example1(): List<List<Char>> {
    return "ABC".toList()
        .combinationsWithReplacement(k = 2)
        .toList()
}

// [(A, A), (A, B), (A, C), (B, B), (B, C), (C, C)]
fun example2(): List<Pair<Char, Char>> {
    return "ABC".toList()
        .pairCombinationsWithReplacement()
        .toList()
}

// [(A, A, A), (A, A, B), (A, A, C), (A, B, B), (A, B, C), (A, C, C), (B, B, B), (B, B, C), (B, C, C), (C, C, C)]
fun example3(): List<Triple<Char, Char, Char>> {
    return "ABC".toList()
        .tripleCombinationsWithReplacement()
        .toList()
}
```

</details>

### Powerset

Returns a sequence that yields all subsets of this list, from the empty set to the full set. Equivalent to
[combinations](#combinations) for every `k` from `0` to `size`.

Subsets are emitted in order of increasing size. Within each size, subsets are emitted in lexicographic order according
to the order of this list.

```kotlin
fun <T> List<T>.powerset(): Sequence<List<T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.powerset

// [[], [A], [B], [C], [A, B], [A, C], [B, C], [A, B, C]]
fun example1(): List<List<Char>> {
    return "ABC".toList()
        .powerset()
        .toList()
}
```

</details>

### Permutations

Returns a sequence that yields `k`-sized permutations of elements from this list.

Elements are treated as unique based on their position, not their value. See also
[Distinct Permutations](#distinct-permutations) for value-based uniqueness, and
[Permutations with Replacement](#permutations-with-replacement) for allowing repeated elements.

The permutation tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.permutations(k: Int = size): Sequence<List<T>>

fun <T> List<T>.pairPermutations(): Sequence<Pair<T, T>>

fun <T> List<T>.triplePermutations(): Sequence<Triple<T, T, T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.permutations
import com.github.michaelbull.itertools.pairPermutations
import com.github.michaelbull.itertools.triplePermutations

// [[A, B], [A, C], [A, D], [B, A], [B, C], [B, D], [C, A], [C, B], [C, D], [D, A], [D, B], [D, C]]
fun example1(): List<List<Char>> {
    return "ABCD".toList()
        .permutations(k = 2)
        .toList()
}

// [(A, B), (A, C), (A, D), (B, A), (B, C), (B, D), (C, A), (C, B), (C, D), (D, A), (D, B), (D, C)]
fun example2(): List<Pair<Char, Char>> {
    return "ABCD".toList()
        .pairPermutations()
        .toList()
}

// [(A, B, C), (A, B, D), (A, C, B), (A, C, D), (A, D, B), (A, D, C), (B, A, C), (B, A, D), (B, C, A), (B, C, D), (B, D, A), (B, D, C), (C, A, B), (C, A, D), (C, B, A), (C, B, D), (C, D, A), (C, D, B), (D, A, B), (D, A, C), (D, B, A), (D, B, C), (D, C, A), (D, C, B)]
fun example3(): List<Triple<Char, Char, Char>> {
    return "ABCD".toList()
        .triplePermutations()
        .toList()
}
```

</details>

### Distinct Permutations

Returns a sequence that yields distinct permutations of elements from this list. Unlike
[Permutations](#permutations), elements are treated as unique based on their value, not their position — equal elements
are interchangeable and the output contains no duplicates. See also
[Permutations with Replacement](#permutations-with-replacement) for allowing repeated elements.

```kotlin
fun <T : Comparable<T>> List<T>.distinctPermutations(): Sequence<List<T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.distinctPermutations

// [[A, A, B], [A, B, A], [B, A, A]]
fun example1(): List<List<Char>> {
    return "AAB".toList()
        .distinctPermutations()
        .toList()
}

// [[A, B, C], [A, C, B], [B, A, C], [B, C, A], [C, A, B], [C, B, A]]
fun example2(): List<List<Char>> {
    return "ABC".toList()
        .distinctPermutations()
        .toList()
}
```

</details>

### Permutations with Replacement

Returns a sequence that yields `k`-sized permutations of elements from this list, allowing individual elements to be
repeated. See also [Permutations](#permutations) for without replacement, and
[Distinct Permutations](#distinct-permutations) for value-based uniqueness.

The permutation tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.permutationsWithReplacement(k: Int = size): Sequence<List<T>>

fun <T> List<T>.pairPermutationsWithReplacement(): Sequence<Pair<T, T>>

fun <T> List<T>.triplePermutationsWithReplacement(): Sequence<Triple<T, T, T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.permutationsWithReplacement
import com.github.michaelbull.itertools.pairPermutationsWithReplacement
import com.github.michaelbull.itertools.triplePermutationsWithReplacement

// [[A, A], [A, B], [A, C], [A, D], [B, A], [B, B], [B, C], [B, D], [C, A], [C, B], [C, C], [C, D], [D, A], [D, B], [D, C], [D, D]]
fun example1(): List<List<Char>> {
    return "ABCD".toList()
        .permutationsWithReplacement(k = 2)
        .toList()
}

// [(A, A), (A, B), (A, C), (B, A), (B, B), (B, C), (C, A), (C, B), (C, C)]
fun example2(): List<Pair<Char, Char>> {
    return "ABC".toList()
        .pairPermutationsWithReplacement()
        .toList()
}

// [(A, A, A), (A, A, B), (A, A, C), (A, B, A), (A, B, B), (A, B, C), (A, C, A), (A, C, B), (A, C, C), (B, A, A), (B, A, B), (B, A, C), (B, B, A), (B, B, B), (B, B, C), (B, C, A), (B, C, B), (B, C, C), (C, A, A), (C, A, B), (C, A, C), (C, B, A), (C, B, B), (C, B, C), (C, C, A), (C, C, B), (C, C, C)]
fun example3(): List<Triple<Char, Char, Char>> {
    return "ABC".toList()
        .triplePermutationsWithReplacement()
        .toList()
}
```

</details>

### Derangements

Returns a sequence that yields `k`-sized derangements of elements from this list. A derangement is a permutation where
no element appears in its original position.

The derangement tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.derangements(k: Int = size): Sequence<List<T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.derangements

// [[B, C, A], [C, A, B]]
fun example1(): List<List<Char>> {
    return "ABC".toList()
        .derangements()
        .toList()
}

// [[B, A], [B, C], [B, D], [C, A], [C, D], [D, A], [D, C]]
fun example2(): List<List<Char>> {
    return "ABCD".toList()
        .derangements(k = 2)
        .toList()
}
```

</details>

### Cartesian Product

Returns a sequence that yields the Cartesian product of the input iterables/lists.

The product tuples are emitted in lexicographic order according to the order of this iterable/list.

Unlike Python's `itertools.product(iterable, repeat=n)`, this function does not accept a `repeat` parameter.
The Cartesian product of a list with itself is equivalent to
[`permutationsWithReplacement`](#permutations-with-replacement).

```kotlin
fun <A, B> Iterable<A>.product(other: Iterable<B>): Sequence<Pair<A, B>>
fun <A, B, C> Iterable<A>.product(first: Iterable<B>, second: Iterable<C>): Sequence<Triple<A, B, C>>

fun <A, B> Pair<Iterable<A>, Iterable<B>>.product(): Sequence<Pair<A, B>>
fun <A, B, C> Triple<Iterable<A>, Iterable<B>, Iterable<C>>.product(): Sequence<Triple<A, B, C>>

fun <T> List<List<T>>.product(): Sequence<List<T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.product

// [(A, x), (A, y), (B, x), (B, y), (C, x), (C, y), (D, x), (D, y)]
fun example1(): List<Pair<Char, Char>> {
    val a = "ABCD".toList()
    val b = "xy".toList()
    return a.product(b).toList()
}

// [(A, C, E), (A, C, F), (A, D, E), (A, D, F), (B, C, E), (B, C, F), (B, D, E), (B, D, F)]
fun example2(): List<Triple<Char, Char, Char>> {
    val a = "AB".toList()
    val b = "CD".toList()
    val c = "EF".toList()

    return Triple(a, b, c)
        .product()
        .toList()
}

// [[A, x], [A, y], [B, x], [B, y], [C, x], [C, y], [D, x], [D, y]]
fun example3(): List<List<Char>> {
    val a = "ABCD".toList()
    val b = "xy".toList()

    return listOf(a, b)
        .product()
        .toList()
}
```

</details>

## Contributing

Bug reports and pull requests are welcome on [GitHub][github].

## License

This project is available under the terms of the ISC license. See the
[`LICENSE`](LICENSE) file for the copyright information and licensing terms.

[//]: # (@formatter:off)
[badge-android]: http://img.shields.io/badge/-android-6EDB8D.svg?style=flat
[badge-android-native]: http://img.shields.io/badge/support-[AndroidNative]-6EDB8D.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg?style=flat
[badge-js]: http://img.shields.io/badge/-js-F8DB5D.svg?style=flat
[badge-js-ir]: https://img.shields.io/badge/support-[IR]-AAC4E0.svg?style=flat
[badge-nodejs]: https://img.shields.io/badge/-nodejs-68a063.svg?style=flat
[badge-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat
[badge-windows]: http://img.shields.io/badge/-windows-4D76CD.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg?style=flat
[badge-apple-silicon]: http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat
[badge-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat
[badge-mac]: http://img.shields.io/badge/-macos-111111.svg?style=flat
[badge-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg?style=flat
[badge-tvos]: http://img.shields.io/badge/-tvos-808080.svg?style=flat

[clojure-combinatorics]: https://github.com/clojure/math.combinatorics
[elixir-enum]: https://hexdocs.pm/elixir/Enum.html
[haskell-combinat]: https://hackage.haskell.org/package/combinat
[julia-combinatorics]: https://juliamath.github.io/Combinatorics.jl/stable/
[python-itertools]: https://docs.python.org/3/library/itertools.html
[ruby-array]: https://ruby-doc.org/3.3.0/Array.html
[rust-itertools]: https://docs.rs/itertools/latest/itertools/trait.Itertools.html
[scala-seqops]: https://www.scala-lang.org/api/current/scala/collection/SeqOps.html
[swift-algorithms]: https://github.com/apple/swift-algorithms
[advent-2023]: https://github.com/michaelbull/advent-2023
[github]: https://github.com/michaelbull/kotlin-itertools
[//]: # (@formatter:on)
