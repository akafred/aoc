package com.akafred.aoc.bingo

typealias Board = List<List<Int>>
typealias Ball = Int

fun bingoScore(input: String): Int {
    val lines = input.lines()
    val draws = extractDraws(lines)
    val boards = extractBoards(lines)

    var score = 0
    var round = 0
    while (score == 0) {
        round++
        val drawn = draws.subList(0, round)
        val winners = boards.filter { board -> hasBingo(board, drawn) }
        if (winners.isNotEmpty()) {
            score = boardScore(drawn, winners[0])
        }
    }
    return score
}

fun lastBingoScore(input:String): Int {
    val lines = input.lines()
    val draws = extractDraws(lines)
    var boards = extractBoards(lines)

    var score = 0
    var round = 0
    while (score == 0) {
        round++
        val drawn = draws.subList(0, round)
        if (boards.size > 1) {
            boards = boards.filter { board -> !hasBingo(board, drawn) }
        } else {
            if (hasBingo(boards[0], drawn)) {
                score = boardScore(drawn, boards[0])
            }
        }
    }
    return score
}

private fun extractBoards(lines: List<String>): List<Board> =
    (1 until (lines.size - 1) step 6).map { lineNo ->
        ((lineNo + 1)..(lineNo + 5)).map { lineNo2 -> lines[lineNo2].trim().split("\\s+".toRegex()).map(String::toInt) }
    }.toList()

private fun boardScore(drawn: List<Ball>, board: Board) =
    drawn.last() * board.flatten().filter { it !in drawn }.sum()

private fun extractDraws(lines: List<String>) = lines[0].split(",").map(String::toInt)

private fun hasBingo(board: Board, drawn: List<Ball>) =
    ((0 until 5).any { x -> (0 until 5).all { y -> board[x][y] in drawn } }
        || (0 until 5).any { x -> (0 until 5).all { y -> board[y][x] in drawn } })


