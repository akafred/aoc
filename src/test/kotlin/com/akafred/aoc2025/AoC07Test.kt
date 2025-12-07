package com.akafred.aoc2025

import com.akafred.aoc2021.aoc21_05.LinesTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC07Test {

    private val inputFile = "input07.txt"
    private val example1Answer = 21
    private val puzzle1Answer = 1594
    private val example2Answer = 40
    private val puzzle2Answer = -1

    private val exampleInput1 = """
        .......S.......
        .......|.......
        ......|^|......
        ......|.|......
        .....|^|^|.....
        .....|.|.|.....
        ....|^|^|^|....
        ....|.|.|.|....
        ...|^|^|||^|...
        ...|.|.|||.|...
        ..|^|^|||^|^|..
        ..|.|.|||.|.|..
        .|^|||^||.||^|.
        .|.|||.||.||.|.
        |^|^|^|^|^|||^|
        |.|.|.|.|.|||.|
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        var beams = setOf(input.lines().first().indexOf("S"))
        var splits = 0
        input.lines().drop(1).forEach { line ->
            beams = beams.fold(setOf()) { acc, beam ->
                if (line[beam] == '^') {
                    splits++
                    acc.plusElement(beam - 1).plusElement((beam + 1))
                } else {
                    acc.plusElement(beam)
                }
            }
        }

        return splits
    }

    private fun solve2(input: String): Int {
        var beam = input.lines().first().indexOf("S")
        return countLines(beam, input.lines().drop(1))
    }

    private fun countLines(beam: Int, lines: List<String>): Int {
        if (lines.isEmpty()) { print('|'); return 1 }
        return if (lines.first()[beam] == '^') {
            countLines(beam - 1, lines.drop(1)) + countLines(beam + 1, lines.drop(1))
        } else {
            countLines(beam, lines.drop(1))
        }
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