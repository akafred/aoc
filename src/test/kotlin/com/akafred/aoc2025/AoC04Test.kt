package com.akafred.aoc2025

import com.akafred.aoc2024.Row
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC04Test {

    private val inputFile = "input04.txt"
    private val example1Answer = 13
    private val puzzle1Answer = 1508
    private val example2Answer = 43
    private val puzzle2Answer = 8538

    private val exampleInput1 = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
        """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val grid:List<String> = input.lines()
        val roll = '@'
        return grid.mapIndexed { r, row ->
                row.mapIndexed { c, pos ->
                    if (pos == roll) {
                        if (countAdj(r, c, grid, roll) < 4) 1 else 0
                    } else 0
                }.sum()
            }.sum()
    }

    private fun countAdj(row: Int, col: Int, board: List<String>, char: Char):Int {
        val directions =
            listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, 1), Pair(0, -1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
        val width = board.first().length
        val height = board.size
        return directions.map { (deltaRow, deltaCol) ->
            val newCol = col + deltaCol
            val newRow = row + deltaRow
            if (0 <= newCol && newCol < width && 0 <= newRow  && newRow < height && board.get(newRow)[newCol] == char) 1 else 0
        }.sum()
    }

    private fun solve2(input: String): Int {
        val roll = '@'
        var grid:List<String> = input.lines()
        var removable = -1
        var removed = 0
        do {
            removable= countRemovable(grid, roll)
            if (removable > 0) {
                grid = removeThem(grid, roll)
                removed += removable
            }
        } while (removable > 0)
        return removed
    }

    private fun removeThem(grid: List<String>, roll: Char): List<String> {
        return grid.mapIndexed { r, row ->
            row.mapIndexed { c, pos ->
                if (pos == roll && countAdj(r, c, grid, roll) >= 4) roll else '.'
            }.joinToString("")
        }
    }

    private fun countRemovable(grid:List<String>, roll:Char):Int {
        return grid.mapIndexed { r, row ->
            row.mapIndexed { c, pos ->
                if (pos == roll) {
                    if (countAdj(r, c, grid, roll) < 4) 1 else 0
                } else 0
            }.sum()
        }.sum()
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