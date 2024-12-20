package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.collections.mapOf

typealias Page = String
typealias Print = List<Page>
typealias Prints = List<Print>


class AoC05Test {

    private val inputFile = "input05.txt"
    private val example1Answer = 143
    private val puzzle1Answer = 5955
    private val example2Answer = 123
    private val puzzle2Answer = 4030

    private val exampleInput1 = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
            """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun parse(input: String): Pair<Map<Page, Set<Page>>, Prints> {
        val lines = input.lines()
        val mustNotBeAfterRules =
            lines
                .takeWhile { it.isNotEmpty() }
                .map { line -> line.split("|") }
                .map { strings -> Pair(strings[0], strings[1]) }
                .fold(mapOf<Page, Set<Page>>()) { acc, pair ->
                    acc + (pair.second to acc.getOrDefault(pair.second, setOf<Page>()) + pair.first)
                }
        val prints: Prints =
            lines
                .dropWhile { it.isNotEmpty() }
                .drop(1)
                .map { line -> line.split(",") }
        return Pair(mustNotBeAfterRules, prints)
    }

    private fun legal(print: Print, mustNotBeAfterRules: Map<Page, Set<Page>>): Boolean =
        print.fold(Pair(true, setOf<Page>())) { (legal, illegalPages), page ->
            when(legal) {
                false -> Pair(false, illegalPages)
                true -> {
                    val newPageSet = illegalPages + mustNotBeAfterRules.getOrDefault(page, emptySet<Page>())
                    Pair(page !in newPageSet, newPageSet)
                }
            }
        }.first

    private fun solve1(input: String): Int {
        val (mustNotBeAfterRules, prints) = parse(input)
        return prints.filter { print ->
            legal(print, mustNotBeAfterRules)
        }.sumOf { pages -> pages[pages.size / 2].toInt() }
    }

    private fun solve2(input: String): Int {
        val (mustNotBeAfterRules, prints) = parse(input)
        val pageComparator = Comparator<Page> { p1, p2 ->
            if (p1 in mustNotBeAfterRules.keys) {
                if (p2 in mustNotBeAfterRules[p1]!!) -1 else 0
            } else if (p2 in mustNotBeAfterRules.keys) {
                if (p1 in mustNotBeAfterRules[p2]!!) 1 else 0
            } else 0
        }
        return prints
            .filter { !legal(it, mustNotBeAfterRules) }
            .map { it.sortedWith(pageComparator) }
            .sumOf { pages -> pages[pages.size / 2].toInt() }
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput1)
        assertEquals(example1Answer, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(inputFile)
        val result = solve1(input)
        assertEquals(puzzle1Answer, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput2)
        assertEquals(example2Answer, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(inputFile)
        val result = solve2(input)
        assertEquals(puzzle2Answer, result)
    }
}
