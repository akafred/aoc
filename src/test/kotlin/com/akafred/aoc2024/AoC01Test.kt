package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

class AoC01Test {

    private val inputFile = "input01.txt"
    private val example1Answer = 11
    private val puzzle1Answer = 2970687
    private val example2Answer = 31
    private val puzzle2Answer = 23963899

    private val exampleInput =
                    "3   4\n" +
                    "4   3\n" +
                    "2   5\n" +
                    "1   3\n" +
                    "3   9\n" +
                    "3   3"

    private fun solve1(input: String): Int =
        input.parseToLists()
            .let { (leftList, rightList) -> Pair(leftList.sorted(), rightList.sorted()) }
            .let { (leftList, rightList) -> leftList.zip(rightList) }
            .sumOf { (left, right) -> abs(left - right) }


    private fun solve2(input: String): Int =
        input.parseToLists()
            .let { (leftList, rightList) ->
                val rightCount = rightList.groupingBy { it }.eachCount()
                leftList.sumOf { it * rightCount.getOrDefault(it, 0) }
            }

    private fun String.parseToLists(): Pair<List<Int>, List<Int>> =
        this.lines()
            .map { it.split("   ").map(String::toInt) }
            .unzip()

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput)
        assertEquals(example1Answer , result)
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
