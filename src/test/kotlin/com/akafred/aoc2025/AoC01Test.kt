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

    companion object {
        const val DIAL_SIZE = 100
        const val START_POSITION = 50
    }

    data class Rotation(val delta: Int, val clicks: Int)

    private fun parseRotations(input: String) = input.lines().map { line ->
        val delta = if (line[0] == 'L') -1 else 1
        Rotation(delta, line.substring(1).toInt())
    }

    private fun solve1(input: String): Int {
        var dialPosition = START_POSITION
        return parseRotations(input).count { rotation ->
            dialPosition = (dialPosition + rotation.delta * rotation.clicks).mod(DIAL_SIZE)
            dialPosition == 0
        }
    }

    private fun solve2(input: String): Int {
        var dialPosition = START_POSITION
        var timesAtZero = 0
        for (rotation in parseRotations(input)) {
            repeat(rotation.clicks) {
                dialPosition = (dialPosition + rotation.delta).mod(DIAL_SIZE)
                if (dialPosition == 0) timesAtZero++
            }
        }
        return timesAtZero
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