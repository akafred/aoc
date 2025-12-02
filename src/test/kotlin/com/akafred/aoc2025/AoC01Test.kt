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
        var result = 0
        var dial = 50
        input.lines().forEach { line ->
            val dir = if (line.startsWith("R")) 1 else -1
            val steps = line.substring(1).toInt()
            dial = (dial + dir * steps) % 100
            if (dial == 0) result++
        }
        return result

    }

    private fun solve2(input: String): Int {
        var result = 0
        var dial = 50
        input.lines().forEach { line ->
            val dir = if (line.startsWith("R")) 1 else -1
            val steps = line.substring(1).toInt() * dir
            var newDial: Int = dial
            var remainingSteps = steps
            while (remainingSteps != 0) {
                if (remainingSteps > 100) {
                    remainingSteps -= 100
                    result++
                } else if (remainingSteps > 0) {
                    if (dial + remainingSteps == 100) {
                        newDial = 0
                        remainingSteps = 0
                        result++
                    } else if (dial + remainingSteps > 100) {
                        newDial = (dial + remainingSteps) % 100
                        remainingSteps = 0
                        result += 1 - if (dial == 0) 1 else 0
                    } else {
                        newDial = dial + remainingSteps
                        remainingSteps = 0
                    }
                } else if (remainingSteps < -100) {
                    remainingSteps += 100
                    result++
                } else if (dial + remainingSteps == 0) {
                    newDial = 0
                    remainingSteps = 0
                    result++
                } else if (dial + remainingSteps < 0) {
                    newDial = dial + remainingSteps + 100
                    remainingSteps = 0
                    result += 1 - if (dial == 0) 1 else 0
                } else {
                    newDial = dial + remainingSteps
                    remainingSteps = 0
                }
            }

            println("$line moved $dial $steps to $newDial - now $result")
            dial = newDial
        }
        return result
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