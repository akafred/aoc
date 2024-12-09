package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC08Test {

    private val inputFile = "input08.txt"
    private val example1Answer = 14
    private val puzzle1Answer = 249
    private val example2Answer = 34
    private val puzzle2Answer = 905

    private val exampleInput1 = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int =
        solver(input) { ant1, ant2, height, width -> calculateSimpleAntinodes(ant1, ant2, height, width) }

    private fun solver(input: String, antiNodeCalculator: (Pos, Pos, Int, Int) -> List<Pos>): Int {
        val board: Board = input.lines().map { line -> line.toList() }
        val height = board.size
        val width = board[0].size
        val antennae = board.antennae()
        val antinodes: List<Pos> =
            antennae.values
                .fold(listOf<Pos>()) { acc, ants: List<Pos> ->
                    var antis: List<Pos> = listOf()
                    ants.forEachIndexed { i, ant1: Pos ->
                        ants.forEachIndexed { j, ant2: Pos ->
                            if (j > i) {
                                val newAntis = antiNodeCalculator(ant1, ant2, height, width)
                                //println(ant1.toString() + "&" + ant2 + ":" + newAntis)
                                //draw(board, newAntis)
                                antis = antis + newAntis
                            }
                        }
                    }
                    acc + antis
                }
        return antinodes.toSet().size
    }

    private fun calculateSimpleAntinodes(ant1: Pos, ant2: Pos, height: Int, width: Int): List<Pos> =
        if(ant1 == ant2)
            emptyList()
        else {
            val diffRow = ant2.first - ant1.first
            val diffCol = ant2.second - ant1.second
            listOf(
                Pos(ant1.first - diffRow, ant1.second - diffCol),
                Pos(ant2.first + diffRow, ant2.second + diffCol)
            ).filter { pos: Pos ->
                0 <= pos.first && pos.first < height && 0 <= pos.second && pos.second < width
            }
        }

    private fun Board.antennae(): Map<Char, List<Pos>> =
        foldIndexed(mapOf()) { r, acc, row ->
            row.foldIndexed(acc) { c, acc, ch ->
                if (ch == '.') acc else acc + Pair(ch, acc.getOrDefault(ch, mutableListOf()) + Pos(r, c))
            }
        }

    private fun draw(board: Board, antinodes: List<Pos>) {
        val mutableBoard = board.map { line -> line.toMutableList() }
        antinodes.forEach { pos ->
            mutableBoard[pos.first].set(pos.second, '#')
        }
        mutableBoard.forEach { line ->
            line.forEach { ch -> print(ch) }
            println()
        }
        println()
    }

    private fun solve2(input: String): Int =
        solver(input) { ant1, ant2, height, width -> calculateResonantAntiNodes(ant1, ant2, height, width) }

    private fun calculateResonantAntiNodes(ant1: Pos, ant2: Pos, height: Int, width: Int): List<Pos> =
        if(ant1 == ant2)
            emptyList()
        else {
            val diffRow = ant2.first - ant1.first
            val diffCol = ant2.second - ant1.second
            // one dir
            var antis = listOf<Pos>()
            var i = 0
            var inside = true
            while(inside){
                val row = ant1.first - diffRow * i
                val col = ant1.second - diffCol * i
                if (0 <= row && row < height && 0 <= col && col < width) {
                    antis += Pos(row, col)
                    i += 1
                } else inside = false
            }
            i = 0
            inside = true
            while(inside){
                val row = ant2.first + diffRow * i
                val col = ant2.second + diffCol * i
                if (0 <= row && row < height && 0 <= col && col < width) {
                    antis += Pos(row, col)
                    i += 1
                } else inside = false
            }
            antis
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
