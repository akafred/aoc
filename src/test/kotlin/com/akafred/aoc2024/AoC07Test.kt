package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

typealias No = BigInteger
typealias Equation = Pair<No, List<No>>
enum class Operator(val repr: String, val operation: (No, No) -> No) {
    Add("+", No::plus),
    Mul("*", No::times),
    Concat("||",  { a, b -> (a.toString() + b.toString()).toBigInteger() })
}


class AoC07Test {

    private var calls = 0
    private val inputFile = "input07.txt"
    private val example1Answer = 3749.toBigInteger()
    private val puzzle1Answer = 6392012777720.toBigInteger()
    private val example2Answer = 11387.toBigInteger()
    private val puzzle2Answer = "61561126043536".toBigInteger()

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
        input.also { calls = 0 }
            .parse()
            .also { pairs -> println("Total Lines: " + pairs.size) }
            .filter { equation -> equation.hasSolution() }
            .also { pairs -> println("Lines with solutions: " + pairs.size) }
            .also { println("Checked candidates: " + calls) }
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
        calls += 1
        return this.second.reduceIndexed { index, acc, i ->
            operators(index - 1).operation.invoke(acc, i)
        }
    }

    private fun Equation.hasSolution(): Boolean {
        val (solution, numbers) = this
        for(i in 0 .. (1 shl (numbers.size - 1))) {
            val result = this.using(i.operators())
            if(result == solution) return true
        }
        return false
    }


    private fun Equation.hasSolutionWithThreeOperators(): Boolean {
        val (solution, numbers) = this
        var i = ZERO
        while (i < powerOfThree(numbers.size - 1)) {
            val result = this.using(i.threeOperators())
            if(result == solution) return true
            i += ONE
        }
        return false
    }

    private fun BigInteger.threeOperators(): (Int) -> Operator {
        return { position ->
            when(getNthTrit(this, position)) {
                0 -> Operator.Add
                1 -> Operator.Mul
                2 -> Operator.Concat
                else -> throw IllegalArgumentException()
            }
        }
    }

    fun getNthTrit(number: BigInteger, n: Int): Int {
        val base = BigInteger.valueOf(3)
        return number.divide(base.pow(n)).mod(base).toInt()
    }

    fun powerOfThree(n: Int): BigInteger {
        return BigInteger("3").pow(n)
    }

    private fun solve2(input: String) =
        input.also { calls = 0 }
            .parse()
            .also { pairs -> println("Total Lines: " + pairs.size) }
            .filter { equation -> equation.hasSolutionWithThreeOperators() }
            .also { pairs -> println("Lines with solutions: " + pairs.size) }
            .also { println("Checked candidates: " + calls) }
            .sumOf { equation -> equation.first }

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


