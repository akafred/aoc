package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

typealias HeadingPos = Pair<Pos, Vec>

class AoC16Test {

    private val inputFile = "input16.txt"
    private val example0Answer = 7036
    private val example1Answer = 11048
    private val puzzle1Answer = 98484
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput0 = """
        ###############
        #.......#....E#
        #.#.###.#.###.#
        #.....#.#...#.#
        #.###.#####.#.#
        #.#.#.......#.#
        #.#.#####.###.#
        #...........#.#
        ###.#.#####.#.#
        #...#.....#.#.#
        #.#.#.###.#.#.#
        #.....#...#.#.#
        #.###.#.#.#.#.#
        #S..#.....#...#
        ###############
    """.trimIndent()

    private val exampleInput1 = """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val board:Board = input.lines().map { line -> line.toList() }
        val start: Pair<Pos,Vec> = HeadingPos(board.find('S'), Vec(0,1))
        val end = board.find('E')
        val graph = EdgeWeightedDigraph<Pair<Pos,Vec>>()
        graph.build(board, start)
//        graph.print()
        graph.runDijkstraSp(start)
//        graph.distTo.entries.forEach { entry ->
//            println(entry)
//        }
        val shortestPath = directions.minOf { dir -> graph.distTo(HeadingPos(end, dir)) }
//        val bestDir = directions.sortedBy { dir -> graph.distTo(HeadingPos(end, dir)) }.first()
//        val bestPath = graph.pathTo(HeadingPos(end, bestDir))
//        bestPath.forEach { println(it) }
//        println(bestPath.sumOf { edge -> edge.weight })
        return shortestPath
    }

    private fun EdgeWeightedDigraph<HeadingPos>.build(board: Board, start: HeadingPos, visited: MutableSet<DirectedEdge<HeadingPos>> = mutableSetOf()) {
        listOf(
            HeadingPos(start.first.move(start.second), start.second),
            HeadingPos(start.first, start.second.turnLeft()),
            HeadingPos(start.first, start.second.turnRight())
        ).forEach { candPos ->
            val edge = DirectedEdge(start, candPos, if (start.second == candPos.second) 1 else 1000)
            val shouldAdd = !visited.contains(edge) && board.get(candPos.first) != '#'
            visited.add(edge)
            if (shouldAdd) {
                addEdge(edge)
                build(board, candPos, visited)
            }
        }
    }


    private fun solve2(input: String): Int {
        TODO("Not yet implemented")
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
