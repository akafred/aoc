package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

typealias Row = List<Char>
typealias Board = List<Row>
typealias Pos = Pair<Int,Int>
typealias Vec = Pair<Int,Int>
typealias Visit = Pair<Pos, Direction>
typealias Visits = List<Visit>
typealias Direction = Vec
typealias Directions = List<Vec>

val directions: Directions = listOf<Direction>(Vec(-1,0), Vec(0,1), Vec(1,0), Vec(0,-1))

fun Pos.inside(board: Board): Boolean =
    0 <= this.first && this.first < board.size
            && 0 <= this.second && this.second < board[0].size

fun Pos.move(vec: Vec) =
    Pair(this.first + vec.first, this.second + vec.second)

fun Board.get(pos:Pos): Char =
    this[pos.first][pos.second]

fun Board.startPos(): Pos = Pair(
    this.indexOfFirst { row -> row.any { ch -> ch !in setOf('#', '.') } },
    this.first { row -> row.any { ch -> ch !in setOf('#', '.') } }.indexOfFirst { ch -> ch !in setOf('#', '.') }
)


fun Direction.nextFrom(directions: Directions): Direction =
    directions[(directions.indexOfFirst { it -> it == this } + 1) % directions.size]

class AoC06Test {

    private val inputFile = "input06.txt"
    private val example1Answer = 41
    private val puzzle1Answer = 4982
    private val example2Answer = 6
    private val puzzle2Answer = -1 // ikke 1753 ...

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
        val board: Board = input.lines().map { line -> line.toList() }
        var visited: Set<Pos> = setOf()
        var guardPos: Pos = Pair(
            board.indexOfFirst { row -> row.any { ch -> ch !in setOf('#', '.') } },
            board.first{ row -> row.any { ch -> ch !in setOf('#', '.') }}.indexOfFirst { ch -> ch !in setOf('#', '.') }
        )
        val directions = listOf<Vec>(Vec(-1,0), Vec(0,1), Vec(1,0), Vec(0,-1))
        var guardDirection = if (board.get(guardPos) == '^') Vec(-1,0) else throw IllegalArgumentException("I only handle '^' guards!")
        while(guardPos.inside(board)) {
            visited += guardPos
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
        val board: Board = input.lines().map { line -> line.toList() }
        var visits: Visits = listOf()
        var guardPos: Pos = board.startPos()
        var placedObstacles: List<Pos> = listOf()
        var guardDirection = if (board.get(guardPos) == '^') Vec(-1,0) else throw IllegalArgumentException("I only handle '^' guards!")
        while(guardPos.inside(board)) {
            visits += Visit(guardPos, guardDirection)
            var candidateMove = guardPos.move(guardDirection)
            while (candidateMove.inside(board) && board.get(candidateMove) == '#') {
                guardDirection = guardDirection.nextFrom(directions)
                candidateMove = guardPos.move(guardDirection)
            }
            val potentialObstacle = candidateMove
            if (potentialObstacle.inside(board) && potentialObstacle != board.startPos()) {
                var potentialGuardDirection = guardDirection.nextFrom(directions)
                var potentialGuardPos = guardPos.move(potentialGuardDirection)
                while(board.get(potentialGuardPos) == '#' || potentialGuardPos == potentialObstacle) {
                    potentialGuardDirection = potentialGuardDirection.nextFrom(directions)
                    potentialGuardPos = guardPos.move(potentialGuardDirection)
                }
                var walked: Visits = listOf()
                var candidatePotentialGuardPos = potentialGuardPos
                var looped = false
                while (candidatePotentialGuardPos.inside(board) && !looped) {
                    while(board.get(candidatePotentialGuardPos) == '#' || candidatePotentialGuardPos == potentialObstacle) {
                        potentialGuardDirection = potentialGuardDirection.nextFrom(directions)
                        candidatePotentialGuardPos = potentialGuardPos.move(potentialGuardDirection)
                    }
                    potentialGuardPos = candidatePotentialGuardPos
                    if (visits.contains(Visit(potentialGuardPos, potentialGuardDirection))
                        || walked.contains(Visit(potentialGuardPos, potentialGuardDirection))) {
                        looped = true
                    } else {
                        walked += Visit(potentialGuardPos, potentialGuardDirection)
                        candidatePotentialGuardPos = potentialGuardPos.move(potentialGuardDirection)
                    }
                }
                if (looped) {
                    placedObstacles += potentialObstacle
                    //draw(board, visits, walked, placedObstacles)
                }
            }
            guardPos = candidateMove
        }
        return placedObstacles.toSet().size
    }

    private fun draw(board: Board, visits: Visits, walked: Visits, obstacles: List<Pos>) {
        print("\u001b[H\u001b[2J")
        System.out.flush()
        val mutableBoard = board.map { line -> line.toMutableList() }
        visits.forEach { step ->
            val (pos, _) = step
            mutableBoard[pos.first].set(pos.second, '*')
        }
        walked.forEach { step ->
            val (pos, _) = step
            mutableBoard[pos.first].set(pos.second, '+')
        }
        obstacles.forEach { pos ->
            mutableBoard[pos.first].set(pos.second, 'O')
        }
        mutableBoard.forEach { line ->
            line.forEach { ch -> print(ch) }
            println()
        }
        println()
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
