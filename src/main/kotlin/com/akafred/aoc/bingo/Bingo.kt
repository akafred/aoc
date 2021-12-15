package com.akafred.aoc.bingo

fun bingoScore(input: String): Int {
    val lines = input.lines()
    val draws = lines[0].split(",").map(String::toInt)

    val boards = (1 until (lines.size - 1) step 6)
        .map { lineNo ->
            ((lineNo + 1)..(lineNo + 5))
                .map { lineNo2 -> lines[lineNo2].trim().split("\\s+".toRegex()).map(String::toInt) } }
        .toList()

    var score = 0
    var round = 0
    while (score == 0) {
        round++
        val drawn = draws.subList(0, round)
        val winners =
            boards
                .filter { board ->
                    (0 until 5).any { x -> (0 until 5).all { y -> board[x][y] in drawn } }
                            || (0 until 5).any { x -> (0 until 5).all { y -> board[y][x] in drawn } }
                }
        if (winners.isNotEmpty()) {
            score = drawn.last() * winners[0].flatten().filter { it !in drawn }.sum()
        }
    }
    return score
}
