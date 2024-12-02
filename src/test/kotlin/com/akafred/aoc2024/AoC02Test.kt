package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

class AoC02Test {

    private val inputFile = "input02.txt"
    private val example1Answer = 2
    private val puzzle1Answer = 407
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput =
                    "7 6 4 2 1\n" +
                    "1 2 7 8 9\n" +
                    "9 7 6 2 1\n" +
                    "1 3 2 4 5\n" +
                    "8 6 4 4 1\n" +
                    "1 3 6 7 9"

    data class State(val safe: Boolean?, val last: Int, val increasing: Boolean? )

    private fun solve1(input: String): Int =
        input.lines()
            .map { line ->
                line.split(" ")
                    .map(String::toInt)
                    .fold(State(null, -1, null)) { state, n ->
                        when {
                            state.safe == null -> State(true, n, null)
                            state.safe == false -> State(false, n, null)
                            state.safe == true && state.increasing == null && 1 <= abs(state.last - n) && abs(state.last - n) <= 3 -> State(true, n, n > state.last)
                            state.safe == true && state.increasing == true && 1 <= n - state.last && n - state.last <= 3 -> State(true, n, true)
                            state.safe == true && state.increasing == false && -3 <= n - state.last && n - state.last <= -1 -> State(true, n, false)
                            else -> State(false, n, null)
                        }
                    }
                }
            .filter{ it.safe == true }
            .count()

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
