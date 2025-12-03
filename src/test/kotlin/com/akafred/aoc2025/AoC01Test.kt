package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC01Test {

    private val inputFile = "input01.txt"
    private val example1Answer = 3
    private val puzzle1Answer = 984
    private val example2Answer = 6
    private val puzzle2Answer = 5657

    private val exampleInput1 = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        var position = 50
        var count = 0
        input.lines().forEach { line ->
            val direction = line[0]
            val distance = line.substring(1).toInt()
            position = when (direction) {
                'L' -> (position - distance).mod(100)
                'R' -> (position + distance).mod(100)
                else -> position
            }
            if (position == 0) count++
        }
        return count
    }

    private fun solve2(input: String): Int {
        var position = 50
        var count = 0
        input.lines().forEach { line ->
            val delta = if (line[0] == 'L') -1 else 1
            repeat(line.substring(1).toInt()) {
                position = (position + delta).mod(100)
                if (position == 0) count++
            }
        }
        return count
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