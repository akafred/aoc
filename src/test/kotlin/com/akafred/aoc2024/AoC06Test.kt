package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

typealias Row = String
typealias Board = List<Row>
typealias Vec = Pair<Int, Int>
typealias Pos = Pair<Int,Int>
typealias Direction = Vec
typealias Directions = List<Vec>

fun Pos.inside(board: Board): Boolean =
    0 <= this.first && this.first < board.size
            && 0 <= this.second && this.second < board[0].length

fun Pos.move(vec: Vec) =
    Pair(this.first + vec.first, this.second + vec.second)

fun Board.get(pos:Pos): Char =
    this[pos.first][pos.second]

fun Direction.nextFrom(directions: Directions): Direction =
    directions[(directions.indexOfFirst { it -> it == this } + 1) % directions.size]

class AoC06Test {

    private val inputFile = "input06.txt"
    private val example1Answer = 41
    private val puzzle1Answer = 4982
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput1 = """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
                """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val board: Board = input.lines()
        var visited: Set<Pos> = setOf()
        var guardPos: Pos = Pair(
            board.indexOfFirst { row -> row.any { ch -> ch !in setOf('#', '.') } },
            board.first{ row -> row.any { ch -> ch !in setOf('#', '.') }}.indexOfFirst { ch -> ch !in setOf('#', '.') }
        )
        val directions = listOf<Vec>(Vec(-1,0), Vec(0,1), Vec(1,0), Vec(0,-1))
        var guardDirection = if (board.get(guardPos) == '^') Vec(-1,0) else throw IllegalArgumentException("I only handle '^' guards!")
        while(guardPos.inside(board)) {
            visited = visited + guardPos
            var candidateMove = guardPos.move(guardDirection)
            if (candidateMove.inside(board)) {
                while (board.get(candidateMove) == '#') {
                    guardDirection = guardDirection.nextFrom(directions)
                    candidateMove = guardPos.move(guardDirection)
                }
            }
            guardPos = candidateMove
        }
        return visited.size
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
