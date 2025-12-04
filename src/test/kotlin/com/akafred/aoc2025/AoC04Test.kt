package com.akafred.aoc2025

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

    private fun solve1(input: String): Int {
        val grid = input.lines().map { it.toList() }
        val rows = grid.size
        val cols = grid[0].size

        fun countNeighborRolls(r: Int, c: Int): Int {
            val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
            return directions.count { (dr, dc) ->
                val nr = r + dr
                val nc = c + dc
                nr in 0 until rows && nc in 0 until cols && grid[nr][nc] == '@'
            }
        }

        var accessibleCount = 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == '@' && countNeighborRolls(r, c) < 4) {
                    accessibleCount++
                }
            }
        }
        return accessibleCount
    }

    private fun solve2(input: String): Int {
        val grid = input.lines().map { it.toMutableList() }.toMutableList()
        val rows = grid.size
        val cols = grid[0].size
        val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

        fun countNeighborRolls(r: Int, c: Int): Int {
            return directions.count { (dr, dc) ->
                val nr = r + dr
                val nc = c + dc
                nr in 0 until rows && nc in 0 until cols && grid[nr][nc] == '@'
            }
        }

        var totalRemoved = 0
        while (true) {
            val toRemove = mutableListOf<Pair<Int, Int>>()
            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (grid[r][c] == '@' && countNeighborRolls(r, c) < 4) {
                        toRemove.add(r to c)
                    }
                }
            }
            if (toRemove.isEmpty()) break
            for ((r, c) in toRemove) {
                grid[r][c] = '.'
            }
            totalRemoved += toRemove.size
        }
        return totalRemoved
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
        val result = solve2(exampleInput1)
        assertEquals(example2Answer, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(inputFile)
        val result = solve2(input)
        assertEquals(puzzle2Answer, result)
    }
}
