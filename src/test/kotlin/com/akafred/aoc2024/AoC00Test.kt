package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val INPUT = "input00.txt"

class AoC00Test {

    private val example1Answer = -1
    private val puzzle1Answer = -1
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput =
            ""

    private fun solve1(input: String): Int {
        TODO("Not yet implemented")
    }

    private fun solve2(input: String): Int {
        TODO("Not yet implemented")
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput)
        assertEquals(example1Answer, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(INPUT)
        val result = solve1(input)
        assertEquals(puzzle1Answer, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput)
        assertEquals(example2Answer, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(INPUT)
        val result = solve2(input)
        assertEquals(puzzle2Answer, result)
    }
}
