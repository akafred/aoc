package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private operator fun Pair<Int, Int>.plus(move: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + move.first, this.second + move.second)




class AoC15Test {

    private val inputFile = "input15.txt"
    private val example0Answer = 2028
    private val example1Answer = 10092
    private val puzzle1Answer = 1515788
    private val example2Answer = -1
    private val puzzle2Answer = -1

    private val exampleInput0 = """
        ########
        #..O.O.#
        ##@.O..#
        #...O..#
        #.#.O..#
        #...O..#
        #......#
        ########

        <^^>>>vv<v>>v<<
    """.trimIndent()

    private val exampleInput1 = """
        ##########
        #..O..O.O#
        #......O.#
        #.OO..O.O#
        #..O@..O.#
        #O#..O...#
        #O..O..O.#
        #.OO.O.OO#
        #....O...#
        ##########

        <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
        vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
        ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
        <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
        ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
        ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
        >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
        <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
        ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
        v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Int {
        val lines = input.lines()
        val immutableBoard: Board = lines.takeWhile { it.isNotEmpty() }.map { line -> line.toList() }
        val moves: List<Vec> =
            lines
                .dropWhile { it.isNotEmpty() }
                .drop(1)
                .joinToString("")
                .map {
                    when(it) {
                        '^' -> Vec(-1,0)
                        '>' -> Vec(0,1)
                        'v' -> Vec(1,0)
                        '<' -> Vec(0,-1)
                        else -> throw IllegalArgumentException("'$it' is an unknown movement direction")
                    }
                }
                .toList()
        //immutableBoard.forEach { println(it.joinToString("")) }
        var robot: Pos = robotStartPos(immutableBoard)
        val board: Array<Array<Char>> = immutableBoard.mutable()

        board.set(robot, '.') // don't need to track robot
        moves.forEach { move: Vec ->
            val candidatePos = robot + move
            when {
                board.get(candidatePos) == '.' -> robot = candidatePos
                board.get(candidatePos) == '#' -> robot = robot
                board.get(candidatePos) == 'O' -> if(moveBoxes(board, candidatePos, move)) { robot = candidatePos }
                else -> throw IllegalArgumentException("'${board.get(candidatePos)}' is an unknown map element")
            }
            //println("Move: $move")
            //board.addRobot(robot).forEach { println(it.joinToString("")) }
        }

        return board.mapIndexed { row, line ->
            line.mapIndexed { col, char ->
                if(board.get(Pos(row,col)) == 'O') row * 100 + col else 0
            }.sum()
        }.sum()
    }

    private fun Array<Array<Char>>.addRobot(robot: Pos): Board {
        return mapIndexed { row, line ->
            line.mapIndexed { col, char ->
                if(robot == Pair(row,col)) '@' else char
            }
        }
    }

    private fun moveBoxes(board: Array<Array<Char>>, pos: Pos, move: Vec): Boolean {
        val candidatePos = pos + move
        return when {
            board.get(candidatePos) == '.' -> { board.set(pos, '.'); board.set(candidatePos, 'O'); true }
            board.get(candidatePos) == '#' -> false
            board.get(candidatePos) == 'O' -> if (moveBoxes(board, candidatePos, move)) { board.set(pos, '.'); board.set(candidatePos, 'O'); true } else false
            else -> throw IllegalArgumentException("'${board.get(candidatePos)}' is an unknown map element")
        }
    }

    private fun Array<Array<Char>>.set(pos: Pos, c: Char) {
        this[pos.first][pos.second] = c
    }

    private fun Board.mutable() = map { it.toCharArray().toTypedArray() }.toTypedArray()

    private fun Array<Array<Char>>.get(pos: Pos): Char = this[pos.first][pos.second]

    private fun robotStartPos(immutableBoard: Board): Pos {
        val robotRow = immutableBoard.indexOfFirst { row -> row.contains('@') }
        val robotCol = immutableBoard[robotRow].indexOf('@')
        return Pos(robotRow, robotCol)
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
