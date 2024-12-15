package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC14Test {

    private val inputFile = "input14.txt"
    private val example1Answer = 12
    private val puzzle1Answer = 221142636
    private val example2Answer = -1
    private val puzzle2Answer = 221142636

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

    private fun solve2(input: String, width: Int, height: Int): Int {
        val startPos =
            input.lines()
                .map { line -> """(-?\d+)""".toRegex().findAll(line).map { it.groupValues[1].toInt() }.toList() }
                .map { robotValues -> Pair(Pos(robotValues[0], robotValues[1]), Vec(robotValues[2], robotValues[3])) }
        print(height, width, startPos)
        var pos = startPos
        (1..100).forEach { i ->
            pos = pos.map { (pos, vec) -> Pair(Pos((pos.first + vec.first + width) % width, (pos.second + vec.second + height) % height), vec)}
            if (true) { // (0 ..height - 1).count { i -> pos.count { (pos, _) -> pos.second == i } > 15 } > 10) {
                println("**** AFTER $i seconds ****")
                print(height, width, pos)
                println()
            }
        }

        val countPerQuadrant: List<Int> =
            pos.map { it.first }.fold(mutableListOf<Int>(0,0,0,0)) { acc, pos ->
                if (pos.first < width / 2 && pos.second < height / 2) acc[0] += 1
                if (pos.first > width / 2 && pos.second < height / 2) acc[1] += 1
                if (pos.first < width / 2 && pos.second > height / 2) acc[2] += 1
                if (pos.first > width / 2 && pos.second > height / 2) acc[3] += 1
                acc
            }
        return countPerQuadrant.fold(1) { acc, pos -> acc * pos }
    }

    private fun print(
        height: Int,
        width: Int,
        startPos: List<Pair<Pos, Vec>>
    ) {
        val mutableBoard = (1..height).map { arrayOfNulls<Int>(width).toMutableList().toTypedArray() }.toTypedArray()
        startPos.forEach { (pos: Pos, vec: Vec) ->
            mutableBoard[pos.second][pos.first] =
                if (mutableBoard[pos.second][pos.first] == null) 1 else mutableBoard[pos.second][pos.first]!! + 1
        }
        mutableBoard.forEach { line ->
            println(line.map {
                when (it) {
                    null -> '.'; else -> it.toString(); }
            }.joinToString(""))
        }
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
    fun `puzzle 2`() {
        val input = Util.readFile(inputFile)
        val result = solve2(input,101,103)
        assertEquals(puzzle2Answer, result)
    }
}
