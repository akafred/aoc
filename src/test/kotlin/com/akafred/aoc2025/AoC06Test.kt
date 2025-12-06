package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

class AoC06Test {

    private val inputFile = "input06.txt"
    private val example1Answer = BigInteger("4277556")
    private val puzzle1Answer = BigInteger("5552221122013")
    private val example2Answer = BigInteger("-1")
    private val puzzle2Answer = BigInteger("-1")

    private val exampleInput1 = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
        """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): BigInteger {
        val operators = input.lines()
            .last()
            .trim()
            .split(Regex("\\s+"))

        val matrix = input.lines()
            .dropLast(1)
            .map { it.trim()
                .split(Regex("\\s+"))
                .map(String::toBigInteger)
            }

        return operators.mapIndexed { col, op ->
            matrix
                .map { it[col]}
                .reduce { v1, v2 -> if (op == "*") v1 * v2 else v1 + v2 }
        }.sumOf { it }
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