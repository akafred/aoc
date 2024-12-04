package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC04Test {

    private val inputFile = "input04.txt"
    private val example1Answer = 18
    private val puzzle1Answer = 2551
    private val example2Answer = 9
    private val puzzle2Answer = 1985

    private val exampleInput1 =
            "MMMSXXMASM\n" +
            "MSAMXMSMSA\n" +
            "AMXSXMAAMM\n" +
            "MSAMASMSMX\n" +
            "XMASAMXAMM\n" +
            "XXAMMXXAMA\n" +
            "SMSMSASXSS\n" +
            "SAXAMASAAA\n" +
            "MAMMMXMMMM\n" +
            "MXMXAXMASX"

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val board:List<String> = input.lines()
        val height = board.size
        val width = board[0].length
        val XMAS = "XMAS"
        val length = XMAS.length
        var count = 0
        board.forEachIndexed { row, line ->
            line.forEachIndexed { col, ch ->
                if (ch == 'X') {
                    val directions = listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, 1), Pair(0, -1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
                    directions.forEach { (deltaRow, deltaCol) ->
                        if (0 <= col + length * deltaCol + 1 && col + length * deltaCol <= width
                            && 0 <= row + length * deltaRow + 1 && row + length * deltaRow <= height
                            && XMAS ==
                            board[row][col].toString() +
                            board[row + deltaRow][col + deltaCol].toString() +
                            board[row + deltaRow * 2][col + deltaCol * 2].toString() +
                            board[row + deltaRow * 3][col + deltaCol * 3].toString()
                        ) count++
                    }
                }
            }
        }
        return count
    }

    private fun solve2(input: String): Int {
        val board:List<String> = input.lines()
        val height = board.size
        val width = board[0].length
        var count = 0
        board.forEachIndexed { row, line ->
            line.forEachIndexed { col, ch ->
                if (col in 1 .. width - 2 && row in 1 .. height - 2) {
                    if (ch == 'A') {
                        if (
                            (
                                board[row - 1][col - 1] == 'M'
                                && board[row - 1][col + 1] == 'M'
                                && board[row + 1][col + 1] == 'S'
                                && board[row + 1][col - 1] == 'S'
                            ) || (
                                board[row - 1][col + 1] == 'M'
                                && board[row + 1][col + 1] == 'M'
                                && board[row + 1][col - 1] == 'S'
                                && board[row - 1][col - 1] == 'S'
                            ) || (
                                board[row + 1][col + 1] == 'M'
                                && board[row + 1][col - 1] == 'M'
                                && board[row - 1][col - 1] == 'S'
                                && board[row - 1][col + 1] == 'S'
                            )|| (
                                board[row + 1][col - 1] == 'M'
                                && board[row - 1][col - 1] == 'M'
                                && board[row - 1][col + 1] == 'S'
                                && board[row + 1][col + 1] == 'S'
                            )
                        ) {
                            count++
                        }
                    }
                }
            }
        }
        return count
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
