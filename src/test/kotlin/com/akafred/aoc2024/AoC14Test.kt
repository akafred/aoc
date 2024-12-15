package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC14Test {

    private val inputFile = "input14.txt"
    private val example1Answer = 12
    private val puzzle1Answer = 221142636
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String, width: Int, height: Int): Int {
        val finalPos =
            input.lines()
                .map { line -> """(-?\d+)""".toRegex().findAll(line).map { it.groupValues[1].toInt() }.toList() }
                .map { robotValues -> Pair(Pos(robotValues[0], robotValues[1]), Vec(robotValues[2], robotValues[3])) }
                .map { (pos, vec) -> Pair((pos.first + 100 * vec.first) % width, (pos.second + 100 * vec.second) % height) }
                .map { pos -> Pair(pos.first + if(pos.first < 0) width else 0, pos.second + if(pos.second < 0) height else 0) }
        val countPerQuadrant: List<Int> =
            finalPos.fold(mutableListOf<Int>(0,0,0,0)) { acc, pos ->
                if (pos.first < width / 2 && pos.second < height / 2) acc[0] += 1
                if (pos.first > width / 2 && pos.second < height / 2) acc[1] += 1
                if (pos.first < width / 2 && pos.second > height / 2) acc[2] += 1
                if (pos.first > width / 2 && pos.second > height / 2) acc[3] += 1
                acc
            }
        return countPerQuadrant.fold(1) { acc, pos -> acc * pos }
    }

    private fun solve2(input: String): Int {
        TODO("Not yet implemented")
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput1,11,7)
        assertEquals(example1Answer, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(inputFile)
        val result = solve1(input,101,103)
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
