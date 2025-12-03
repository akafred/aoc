package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC02Test {

    private val inputFile = "input02.txt"
    private val example1Answer = 1227775554L
    private val puzzle1Answer = 18595663903L

    private val exampleInput1 = """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """.trimIndent()

    private fun isInvalid(n: Long): Boolean {
        val s = n.toString()
        if (s.length % 2 != 0) return false
        val half = s.length / 2
        return s.substring(0, half) == s.substring(half)
    }

    private fun solve1(input: String): Long =
        input.split(",").sumOf { range ->
            val (start, end) = range.split("-").map { it.toLong() }
            (start..end).filter { isInvalid(it) }.sumOf { it }
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
}
