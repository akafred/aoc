package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

typealias No = BigInteger
typealias Equation = Pair<No, List<No>>
enum class Operator(val repr: Char, val operation: (No, No) -> No) {
    Add('+', No::plus), Mul('*', No::times)
}


class AoC07Test {

    private val inputFile = "input07.txt"
    private val example1Answer = 3749.toBigInteger()
    private val puzzle1Answer = 6392012777720.toBigInteger()
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 =
                """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20""".trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String) =
        input.parse()
            .filter { equation -> equation.solutions() }
            .sumOf { equation -> equation.first }

    private fun String.parse(): List<Equation> = lines().map { line ->
        val parts = line.split(":")
        Equation(parts[0].trim().toBigInteger(), parts[1].trim().split(" ").map { number -> number.toBigInteger() })
    }

    private fun Int.operators(): (Int) -> Operator {
        return { position ->
            if (((this shr position) and 1) == 0) Operator.Add else Operator.Mul
        }
    }

    private fun Equation.using(operators: (Int) -> Operator): BigInteger {
        return this.second.reduceIndexed { index, acc, i ->
            operators(index - 1).operation.invoke(acc, i)
        }
    }

    private fun Equation.solutions(): Boolean {
        val (solution, numbers) = this
        for(i in 0 .. (1 shl (numbers.size - 1))) {
            val result = this.using(i.operators())
            if(result == solution) return true
        }
        return false
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

