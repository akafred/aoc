package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.text.RegexOption.MULTILINE

class AoC03Test {

    private val inputFile = "input03.txt"
    private val example1Answer = 161
    private val puzzle1Answer = 173529487
    private val example2Answer = 48
    private val puzzle2Answer = 99532691

    private val exampleInput =
        "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))\n"

    private val exampleInput2 =
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    private val mulRegex = """mul\(\d{1,3},\d{1,3}\)""".toRegex(MULTILINE)
    private val doMulRegex = """mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)""".toRegex(MULTILINE)

    private val factorRegex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    private fun solve1(input: String): Int =
        mulRegex
            .findAll(input)
            .map { result -> result.value }
            .map { val (x1, x2) = factorRegex.find(it)!!.destructured; Pair(x1.toInt(),x2.toInt()) }
            .sumOf { it.first * it.second }

    private fun solve2(input: String): Int =
        doMulRegex
            .findAll(input)
            .map { result -> result.value }
            .fold(Pair(0, true)) { (sum: Int, enabled: Boolean), match: String ->
                when {
                    match == "do()" -> Pair(sum, true)
                    match == "don't()" -> Pair(sum, false)
                    else -> {
                        if (enabled) {
                            val (x1, x2) = factorRegex.find(match)!!.destructured
                            Pair(sum + x1.toInt() * x2.toInt(), enabled)
                        } else {
                            Pair(sum, enabled)
                        }
                    }
                }
            }.first


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
