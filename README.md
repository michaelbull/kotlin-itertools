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

Multiplatform combinatoric sequences for Kotlin, inspired by [python-itertools][python-itertools].

Initially built as part of my solutions for [Advent of Code 2023][advent-2023].

## Installation

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.michael-bull.kotlin-itertools:kotlin-itertools:1.0.3")
}
```

## Usage

### Combinations

Returns a sequence that yields `length`-sized combinations from this list.

The combination tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.combinations(length: Int = size): Sequence<List<T>>

fun <T> List<T>.pairCombinations(): Sequence<Pair<T, T>>

fun <T> List<T>.tripleCombinations(): Sequence<Triple<T, T, T>>
```

<details>
<summary><strong>Examples</strong></summary>

```kotlin
import com.github.michaelbull.itertools.combinations
import com.github.michaelbull.itertools.pairCombinations
import com.github.michaelbull.itertools.tripleCombinations

// [[A, B], [A, C], [A, D], [B, C], [B, D], [C, D]]
fun example1(): List<List<Char>> {
    return "ABCD".toList()
        .combinations(length = 2)
        .toList()
}

// [(A, B), (A, D), (A, C), (B, D), (B, C), (D, C)]
fun example2(): List<Pair<Char, Char>> {
    return "ABDC".toList()
        .pairCombinations()
        .toList()
}

// [(0, 1, 2), (0, 1, 3), (0, 2, 3), (1, 2, 3)]
fun example3(): List<Triple<Int, Int, Int>> {
    return (0..3).toList()
        .tripleCombinations()
        .toList()
}
```

</details>

### Permutations

Returns a sequence that yields `length`-sized permutations from this list.

The permutation tuples are emitted in lexicographic order according to the order of this list.

```kotlin
fun <T> List<T>.permutations(length: Int = size): Sequence<List<T>>

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
        .permutations(length = 2)
        .toList()
}

// [(0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1)]
fun example2(): List<Pair<Int, Int>> {
    return (0..2).toList()
        .pairPermutations()
        .toList()
}

// [(0, 1, 2), (0, 2, 1), (1, 0, 2), (1, 2, 0), (2, 0, 1), (2, 1, 0)]
fun example3(): List<Triple<Int, Int, Int>> {
    return (0..2).toList()
        .triplePermutations()
        .toList()
}
```

</details>

### Cartesian Product

Returns a sequence that yields the Cartesian product of the input iterables/lists.

The product tuples are emitted in lexicographic order according to the order of this iterable/list.

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

[python-itertools]: https://docs.python.org/3/library/itertools.html
[advent-2023]: https://github.com/michaelbull/advent-2023
[github]: https://github.com/michaelbull/kotlin-itertools

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
