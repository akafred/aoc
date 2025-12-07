package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC07Test {

    private val inputFile = "input07.txt"
    private val example1Answer = 21
    private val puzzle1Answer = 1594
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 = """
.......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
...............
""".trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val grid = input.lines()
        val height = grid.size
        val width = grid.maxOf { it.length }

        // Find starting position (S)
        val startCol = grid[0].indexOf('S')

        // Track active beam columns as a set (beams merge when at same position)
        var beamCols = setOf(startCol)
        var splits = 0

        for (row in 1 until height) {
            val line = grid[row]
            val newBeamCols = mutableSetOf<Int>()

            for (col in beamCols) {
                val cell = line.getOrElse(col) { '.' }
                if (cell == '^') {
                    // Beam hits splitter: count split, emit left and right
                    splits++
                    if (col - 1 >= 0) newBeamCols.add(col - 1)
                    if (col + 1 < width) newBeamCols.add(col + 1)
                } else {
                    // Beam continues downward
                    newBeamCols.add(col)
                }
            }

            beamCols = newBeamCols
        }

        return splits
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