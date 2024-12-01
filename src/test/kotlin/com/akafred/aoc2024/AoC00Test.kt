package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val INPUT = "input00.txt"

private const val example1_answer = -1
private const val puzzle1_answer = -1
private const val example2_answer = -1
private const val puzzle2_answer = -1

class AoC00Test {

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
        assertEquals(example1_answer, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(INPUT)
        val result = solve1(input)
        assertEquals(puzzle1_answer, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput)
        assertEquals(example2_answer, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(INPUT)
        val result = solve2(input)
        assertEquals(puzzle2_answer, result)
    }
}
