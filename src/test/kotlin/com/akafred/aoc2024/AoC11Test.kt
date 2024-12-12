package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.Math.log
import kotlin.math.floor
import kotlin.system.measureTimeMillis

typealias Stone = Long

class AoC11Test {

    private val inputFile = "input11.txt"
    private val example1Answer = 55312L
    private val puzzle1Answer = 194782L
    private val example2Answer = 65601038650482L
    private val puzzle2Answer = 233007586663131L

    private val exampleInput1 = """125 17""".trimIndent()

    private val exampleInput2 = exampleInput1

    private fun Int.even(): Boolean = this % 2 == 0

    val memo = mutableMapOf<Pair<Stone, Int>, Long>()

    private fun Stone.blinkCount(i: Int): Long {
        val key = this to i
        if (key in memo) return memo[key]!!
        val stone = this
        val stringStone = stone.toString()
        if (i == 0) return 1L
        val count = when {
            stone == 0L -> 1L.blinkCount(i - 1)
            stringStone.length.even() ->
                stringStone.substring(0, stringStone.length / 2).toLong().blinkCount(i - 1) +
                        stringStone.substring(stringStone.length / 2).toLong().blinkCount(i - 1)
            else -> (stone * 2024).blinkCount(i - 1)
        }
        memo[key] = count
        return count
    }

    private fun solve1(input: String): Long {
        var stones: List<Stone> = input.split(" ").map { it.toLong() }
        (1..25).forEach { i ->
            stones = stones.flatMap { stone ->
                val stringStone = stone.toString()
                val stones: List<Stone> =
                    if (stone == 0L)
                        listOf(1L)
                    else if (stringStone.length.even())
                    listOf(
                        stringStone.substring(0, stringStone.length / 2).toLong(),
                        stringStone.substring(stringStone.length / 2).toLong()
                    )
                else listOf(stone * 2024)
                stones
            }
        }
        return stones.size.toLong()
    }

    private fun solve1b(input: String): Long {
        var stones: List<Stone> = input.split(" ").map { it.toLong() }
        return stones.sumOf{ stone: Stone -> stone.blinkCount(25) } //.also { _ -> println("Memo-entries: " + memo.size)}
    }

    private fun solve2(input: String): Long {
        var stones: List<Stone> = input.split(" ").map { it.toLong() }
        return stones.sumOf{ stone: Stone -> stone.blinkCount(75) }//.also { _ -> println("Memo-entries: " + memo.size)}
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput1)
        assertEquals(example1Answer, result)
    }

    @Test
    fun `puzzle 1`() {
        var result: Long
        println(measureTimeMillis {
            val input = Util.readFile(inputFile)
            result = solve1b(input)
        })
        assertEquals(puzzle1Answer, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput2)
        assertEquals(example2Answer, result)
    }

    @Test
    fun `puzzle 2`() {
        var result: Long
        println(measureTimeMillis {
            val input = Util.readFile(inputFile)
            result = solve2(input)
        })
        assertEquals(puzzle2Answer, result)
    }
}

