package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.collections.plus

typealias Trail = List<Pos>
typealias NoBoard = List<List<Int>>

fun Pos.inside(board: NoBoard): Boolean =
    0 <= this.first && this.first < board.size
            && 0 <= this.second && this.second < board[0].size

fun NoBoard.get(pos:Pos): Int =
    this[pos.first][pos.second]

class AoC10Test {

    private val inputFile = "input10.txt"
    private val example1Answer = 36
    private val example0Answer = 1
    private val puzzle1Answer = 737
    private val example2Answer = 81
    private val puzzle2Answer = 1619

    private val exampleInput1 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()

    private val exampleInput0 = """
        0123
        1234
        8765
        9876
    """.trimIndent()

    private val exampleInput2 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()

    private fun solve1(input: String): Int {
        val noBoard = input.lines().map { line -> line.toList().map { ch -> ch.digitToInt() }}
        val trailHeads = noBoard.foldIndexed(listOf<Pos>()) { row, acc, line -> line.foldIndexed(acc) { col, acc, height -> if (height ==0 ) acc + Pos(row,col) else acc } }
        // println(trailHeads)
        val trailheadTrails = trailHeads.fold(listOf<Pair<Pos, List<Trail>>>()) { acc, trailHead ->
            acc + Pair(trailHead, noBoard.walkUpFrom(listOf(trailHead)))
        }
        return trailheadTrails.sumOf {(trailhead, trails) -> trails.map { trail: List<Pos> -> trail.last() }.distinct().size}
    }

    val directions: Directions = listOf<Direction>(Vec(-1,0), Vec(0,1), Vec(1,0), Vec(0,-1))
    val eightDirections: Directions = listOf<Direction>(Vec(-1,0), Vec(-1,1), Vec(0,1), Vec(1,1), Vec(1,0),Vec(1,-1),Vec(0,-1),Vec(-1,-1))

    private fun NoBoard.walkUpFrom(trailSoFar: Trail): List<Trail> {
        return directions.fold(listOf<Trail>()) { acc, dir ->
            val pos = trailSoFar.last()
            val candidateMove = pos.move(dir)
            if(candidateMove.inside(this) && this.get(candidateMove) == this.get(pos) + 1) {
                if (this.get(candidateMove) == 9) {
                    acc + listOf(trailSoFar + candidateMove)
                } else {
                    acc + walkUpFrom(trailSoFar + candidateMove)
                }
            } else {
                acc
            }
        }
    }

    private fun solve2(input: String): Int {
        val noBoard = input.lines().map { line -> line.toList().map { ch -> ch.digitToInt() }}
        val trailHeads = noBoard.foldIndexed(listOf<Pos>()) { row, acc, line -> line.foldIndexed(acc) { col, acc, height -> if (height ==0 ) acc + Pos(row,col) else acc } }
        val trailheadTrails = trailHeads.fold(listOf<Pair<Pos, List<Trail>>>()) { acc, trailHead ->
            acc + Pair(trailHead, noBoard.walkUpFrom(listOf(trailHead)))
        }
        return trailheadTrails.sumOf {(trailhead, trails) -> trails.size }
    }

    @Test
    fun `example 0`() {
        val result = solve1(exampleInput0)
        assertEquals(example0Answer, result)
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
