package com.akafred.aoc.aoc21_8

typealias Display = Pair<List<String>,List<String>>

val Display.patterns: List<String> get() = first
val Display.output: List<String> get() = second

val pattern = mapOf(0 to "abcefg", 1 to "cf", 2 to "acdeg", 3 to "acdfg", 4 to "bcdf", 5 to "abdfg", 6 to "abdefg", 7 to "acf", 8 to "abcdefg", 9 to "abcdfg")

val uniqueSignalPatternLengths = listOf(1,4,7,8).map { pattern[it]!!.length }


fun simpleDigitCount(input: String): Int {
    val displaysData = input.lines()
        .map { inputLine ->
            inputLine.split(" | ")
                .let { patternsAndOutput ->
                    Display(patternsAndOutput[0].split(" "), patternsAndOutput[1].split(" ")) } }

    return displaysData.sumOf { display ->
        display.output.count { signal ->
            signal.length in uniqueSignalPatternLengths
        }
    }
}
