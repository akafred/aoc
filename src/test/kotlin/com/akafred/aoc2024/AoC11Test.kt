package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.Math.log
import kotlin.math.floor

typealias Stone = String

class AoC11Test {

    private val inputFile = "input11.txt"
    private val example1Answer = 55312
    private val puzzle1Answer = 194782
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 = """125 17""".trimIndent()

    private val exampleInput2 = exampleInput1

    private fun Int.even(): Boolean = this % 2 == 0

    private fun solve1(input: String): Int {
        var stones: List<Stone> = input.split(" ")
        (1..25).forEach { i ->
           stones = stones.flatMap { stone ->
               val longStone = stone.toLong()
               val stones: List<Stone> = if (longStone == 0L)
                   listOf("1")
               else if (stone.length.even())
                   listOf(
                       stone.substring(0, stone.length / 2).toLong().toString(),
                       stone.substring(stone.length / 2).toLong().toString()
                   )
               else listOf((longStone * 2024).toString())
               stones
           }
        }
        return stones.size
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
