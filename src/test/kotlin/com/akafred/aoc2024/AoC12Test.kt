package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.collections.plus

class AoC12Test {

    private val inputFile = "input12.txt"
    private val example1Answer = 1930
    private val puzzle1Answer = 1461806
    private val example2Answer = 1206
    private val puzzle2Answer = -1

    private val exampleInput1 = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent()

    private val exampleInput2 = exampleInput2

    val directions: Directions = listOf<Direction>(Vec(-1,0), Vec(0,1), Vec(1,0), Vec(0,-1))

    private fun Board.regions(): List<Set<Pos>> {
        var regions: List<Set<Pos>> = listOf()
        val unmapped: MutableSet<Pos> = this.mapIndexed { row, line -> line.mapIndexed { col, _ -> Pos(row, col) } }.flatten().toMutableSet()
        while (!unmapped.isEmpty()) {
            val start: Pos = unmapped.first()
            val region: Set<Pos> = walkRegion(start, unmapped, this)
            regions = regions + listOf(region)
            //println("Region: $region")
        }
        return regions
    }


    private fun solve1(input: String): Int {
        val board = input.lines().map { line -> line.toList() }
        return board
            .regions()
            .sumOf { region: Set<Pos> ->
                region.fold(0) { acc: Int, pos: Pos ->
                    val fence: Int = directions.count { vec ->
                        val nextPos = pos.move(vec)
                        !nextPos.inside(board) || board.get(nextPos) != board.get(pos)
                    }
                    acc + fence
                } * region.size
            }
    }

    private fun walkRegion(pos: Pos, unmapped:MutableSet<Pos>, board: Board): Set<Pos> {
        val regionChar = board.get(pos)
        var region: Set<Pos> = setOf(pos)
        unmapped.remove(pos)
        directions.forEach { vec: Vec ->
            val nextPos = pos.move(vec)
            if(nextPos in unmapped && board.get(nextPos) == regionChar)  {
                region = region + walkRegion(nextPos, unmapped, board)
            }
        }
        return region
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
