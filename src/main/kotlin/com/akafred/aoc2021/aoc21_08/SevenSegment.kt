package com.akafred.aoc2021.aoc21_08

typealias Display = Pair<Patterns, Patterns>

typealias Pattern = Set<Char>
typealias Patterns = List<Pattern>

val Display.patterns: Patterns get() = first
val Display.output: Patterns get() = second

fun Patterns.ofLen(length: Int) = this.filter { it.size == length }.let { if(it.size == 1) it[0] else throw IllegalArgumentException("Ambiguous length.") }

val Patterns.occur: Map<Char,Int> get() = "abcdefg".toSet().map { c -> Pair(c, this.count { it.contains(c) }) }.toMap()

val pattern = mapOf("0" to "abcefg", "1" to "cf", "2" to "acdeg", "3" to "acdfg", "4" to "bcdf", "5" to "abdfg", "6" to "abdefg", "7" to "acf", "8" to "abcdefg", "9" to "abcdfg")
val revPattern = pattern.entries.associate { Pair(it.value, it.key) }

val uniqueSignalPatternLengths = listOf("1","4","7","8").map { pattern[it]!!.length }


fun simpleDigitCount(input: String) =
    parseDisplaysInput(input).sumOf { display ->
        display.output.count { signal ->
            signal.size in uniqueSignalPatternLengths
        }
    }

fun sumOfOutputs(input: String) =
    parseDisplaysInput(input).map { display ->
        val translator = translatorFor(display.patterns)
        display.output.map { signal -> revPattern[sortedTranslatedSignal(signal, translator)]!! }.joinToString("").toInt()
    }.sum()

private fun sortedTranslatedSignal(signal: Pattern, translator: Map<Char, Char>) =
    signal.map { segment -> translator[segment]!! }.toList().sorted().joinToString("")

private fun translatorFor(p: Patterns): Map<Char, Char> {
    val translator = mutableMapOf<Char, Char>()
    translator[p.ofLen(3).minus(p.ofLen(2)).first()] = 'a'
    translator[p.occur.filter { it.value == 9 }.entries.first().key] = 'f'
    translator[p.occur.filter { it.value == 6 }.entries.first().key] = 'b'
    translator[p.occur.filter { it.value == 4 }.entries.first().key] = 'e'
    translator[p.occur.filter { it.value == 8 }.keys.first { it !in translator.keys }] = 'c'
    translator[p
        .filter { it.size == 6 }
        .flatten()
        .filter { it !in translator.keys }
        .filter { it in p.ofLen(4) }
        .first()] = 'd'
    translator["abcdefg".toSet().filter { it !in translator.keys }.first()] = 'g'
    return translator.toMap()
}

private fun parseDisplaysInput(input: String) =
    input.lines()
        .map { inputLine ->
            inputLine.split(" | ")
                .let { patternsAndOutput ->
                    Display(
                        patternsAndOutput[0].split(" ").map { it.toSet() },
                        patternsAndOutput[1].split(" ").map { it.toSet() })
                }
        }
