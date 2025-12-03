package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC03Test {

    private val inputFile = "input03.txt"
    private val example1Answer = 357
    private val puzzle1Answer = 17435
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
        """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int =
        input.lines()
            .sumOf { bank ->
                val batteries = bank.toList()
                val maxFirstDigit = batteries.dropLast(1).max()
                val posFirstDigit = batteries.indexOf(maxFirstDigit)
                val maxSecondDigit = batteries.drop(posFirstDigit + 1).max()
                val jolts = maxFirstDigit.digitToInt() * 10 + maxSecondDigit.digitToInt()
                jolts
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