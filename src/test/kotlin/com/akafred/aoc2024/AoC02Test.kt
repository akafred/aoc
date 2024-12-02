package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

typealias Report = List<Int>

class AoC02Test {

    private val inputFile = "input02.txt"
    private val example1Answer = 2
    private val puzzle1Answer = 407
    private val example2Answer = 4
    private val puzzle2Answer = 459

    private val exampleInput =
                    "7 6 4 2 1\n" +
                    "1 2 7 8 9\n" +
                    "9 7 6 2 1\n" +
                    "1 3 2 4 5\n" +
                    "8 6 4 4 1\n" +
                    "1 3 6 7 9"

    sealed interface Result
    data class Safe(val last: Int, val increasing: Boolean? ) : Result
    class Unsafe() : Result

    private fun solve1(input: String): Int =
        input.lines()
            .map { line -> line.split(" ").map(String::toInt) }
            .count{ it.check() is Safe }

    private fun solve2(input: String): Int =
        input.lines()
            .map { line ->
                val report= line.split(" ").map(String::toInt)
                print(report)
                var result = report.check()
                println(result)
                var indexToDrop = 0
                while (indexToDrop < report.size && result is Unsafe) {
                    val reportWithOneDropped =
                        report.take(indexToDrop) + report.takeLast(report.size - indexToDrop - 1)
                    result = reportWithOneDropped.check()
                    indexToDrop++
                }
                result
            }
            .count{ it is Safe }

    private fun Report.check(): Result =
        fold(null) { result: Result?, n: Int ->
            when {
                result is Unsafe -> Unsafe()
                result == null -> Safe(n, null)
                result is Safe && result.increasing == null
                        && 1 <= abs(result.last - n) && abs(result.last - n) <= 3
                            -> Safe(n, n > result.last)
                result is Safe && result.increasing == true
                        && 1 <= n - result.last && n - result.last <= 3
                            -> Safe(n, true)
                result is Safe && result.increasing == false
                        && -3 <= n - result.last && n - result.last <= -1
                            -> Safe(n, false)
                else -> Unsafe()
            }
        }!!

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

