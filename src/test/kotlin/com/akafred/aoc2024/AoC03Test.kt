package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.text.RegexOption.MULTILINE

class AoC03Test {

    private val inputFile = "input03.txt"
    private val example1Answer = 161
    private val puzzle1Answer = -1
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput =
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))\n"

    private val mulRegex = """mul\(\d{1,3},\d{1,3}\)""".toRegex(MULTILINE)
    private val factorRegex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    private fun solve1(input: String): Int =
        mulRegex
            .findAll(input).toList()
            .map { result -> result.value }
            .map { val (x1, x2) = factorRegex.find(it)!!.destructured; Pair(x1,x2) }
            .sumOf { it.first.toInt() * it.second.toInt() }

    private fun solve2(input: String): Int {
        TODO("Not yet implemented")
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput)
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
        val result = solve2(exampleInput)
        assertEquals(example2Answer, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(inputFile)
        val result = solve2(input)
        assertEquals(puzzle2Answer, result)
    }
}
