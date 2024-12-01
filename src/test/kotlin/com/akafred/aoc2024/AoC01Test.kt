package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

private const val INPUT = "input01.txt"

class AoC01Test {

    private val exampleInput =
                    "3   4\n" +
                    "4   3\n" +
                    "2   5\n" +
                    "1   3\n" +
                    "3   9\n" +
                    "3   3"

    private fun solve1(input: String): Int {
        val lists = parse(input)
        lists.first().sort()
        lists.last().sort()
        var sum = 0
        (0 until lists.first().size).forEach { i ->
            sum += abs(lists.first()[i] - lists.last()[i])
        }
        return sum
    }

    private fun parse(input: String): List<MutableList<Int>> {
        val lists =
            input.lines()
                .fold(listOf(mutableListOf<Int>(), mutableListOf<Int>())) { acc, line: String ->
                    val values = line.split(" ")
                    acc.first().add(values.first().toInt())
                    acc.last().add(values.last().toInt())
                    acc
                }
        return lists
    }

    private fun solve2(input: String): Int {
        val lists = parse(input)
        val countMap = lists.last().groupingBy { it }.eachCount()
        val sumProduct = lists.first()
            .sumOf {
                it * countMap.getOrDefault(it, 0)
            }
        return sumProduct
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput)
        assertEquals(11 , result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(INPUT)
        val result = solve1(input)
        assertEquals(2970687, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput)
        assertEquals(31, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(INPUT)
        val result = solve2(input)
        assertEquals(23963899, result)
    }
}
