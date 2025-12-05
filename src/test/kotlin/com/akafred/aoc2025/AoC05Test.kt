package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max

data class Interval(val start: Long, val end: Long) {
    val size: Long get() = end - start + 1
    operator fun contains(value: Long) = value in start..end
    fun overlaps(other: Interval) = end >= other.start
    fun merge(other: Interval) = Interval(start, max(end, other.end))
}

class AoC05Test {

    private val inputFile = "input05.txt"
    private val example1Answer = 3
    private val puzzle1Answer = 601
    private val example2Answer = 14L
    private val puzzle2Answer = 367899984917516L

    private val exampleInput1 = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
        """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val (rangesSection, ingredientsSection) = input.split("\n\n")

        val intervals = rangesSection.lines().map { line ->
            val (start, end) = line.split("-").map { it.toLong() }
            Interval(start, end)
        }

        val ingredients = ingredientsSection.lines().map { it.toLong() }

        return ingredients.count { ingredient -> intervals.any { ingredient in it } }
    }

    private fun solve2(input: String): Long {
        val rangesSection = input.split("\n\n")[0]

        val intervals = rangesSection.lines()
            .map { line ->
                val (start, end) = line.split("-").map { it.toLong() }
                Interval(start, end)
            }
            .sortedBy { it.start }

        return intervals.drop(1).fold(intervals.first() to 0L) { (current, total), next ->
            if (current.overlaps(next)) {
                current.merge(next) to total
            } else {
                next to total + current.size
            }
        }.let { (last, total) -> total + last.size }
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