package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

const val INPUT = "input01.txt"

class AoC01Test {

    val exampleInput =
            ""

    fun solve1(input: String): Int {
        TODO("Not yet implemented")
    }

    fun solve2(input: String): Int {
        TODO("Not yet implemented")
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput)
        assertEquals(-1, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(INPUT)
        val result = solve1(input)
        assertEquals(-1, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput)
        assertEquals(-1, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(INPUT)
        val result = solve2(input)
        assertEquals(-1, result)
    }
}
