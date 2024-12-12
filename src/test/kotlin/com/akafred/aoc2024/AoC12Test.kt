package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC12Test {

    private val inputFile = "input12.txt"
    private val example1Answer = 1930
    private val puzzle1Answer = -1
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        TODO("Not yet implemented")
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
