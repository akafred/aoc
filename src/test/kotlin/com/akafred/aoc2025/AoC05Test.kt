package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC05Test {

    private val inputFile = "input05.txt"
    private val example1Answer = 3
    private val puzzle1Answer = 601
    private val example2Answer = -1
    private val puzzle2Answer = -1

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
        val (freshList, availableList) = input.split("\n\n")
        val freshRanges = freshList.lines()
            .map { line ->
                line.split("-")
                    .map { it.toLong() }
                    .let { it.first().rangeTo(it.last()) }
            }
        val available = availableList.lines().map { it.toLong() }

        return available.count{ ingredient -> freshRanges.any { ingredient in it }}
    }

    private fun solve2(input: String): Int {
        TODO("Not yet implemented")
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