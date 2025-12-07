package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC06Test {

    private val inputFile = "input06.txt"
    private val example1Answer = 4277556L
    private val puzzle1Answer = 5552221122013L
    private val example2Answer = 3263827L
    private val puzzle2Answer = 11371597126232L

    private val exampleInput1 = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
        """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Long {
        val lines = input.lines()
        val numberRows = lines.dropLast(1).map { it.trim().split(Regex("\\s+")).map { n -> n.toLong() } }
        val operators = lines.last().trim().split(Regex("\\s+"))

        return numberRows[0].indices.sumOf { i ->
            val numbers = numberRows.map { it[i] }
            when (operators[i]) {
                "*" -> numbers.reduce { acc, n -> acc * n }
                "+" -> numbers.sum()
                else -> error("Unknown operator")
            }
        }
    }

    private fun solve2(input: String): Long {
        val lines = input.lines()
        val dataRows = lines.dropLast(1)
        val operatorRow = lines.last()

        val width = dataRows.maxOf { it.length }
        val paddedRows = dataRows.map { it.padEnd(width) }
        val paddedOpRow = operatorRow.padEnd(width)

        // Find separator columns (all spaces in data rows)
        val separatorCols = (0 until width).filter { col ->
            paddedRows.all { row -> col >= row.length || row[col] == ' ' }
        }.toSet()

        // Group consecutive non-separator columns into problems
        val problems = mutableListOf<List<Int>>()
        var currentProblem = mutableListOf<Int>()

        for (col in 0 until width) {
            if (col in separatorCols) {
                if (currentProblem.isNotEmpty()) {
                    problems.add(currentProblem)
                    currentProblem = mutableListOf()
                }
            } else {
                currentProblem.add(col)
            }
        }
        if (currentProblem.isNotEmpty()) {
            problems.add(currentProblem)
        }

        return problems.sumOf { problemCols ->
            // Find operator for this problem
            val operator = problemCols.map { paddedOpRow.getOrElse(it) { ' ' } }
                .first { it == '*' || it == '+' }

            // Read columns right-to-left, each column becomes a number (top=MSD)
            val numbers = problemCols.reversed().map { col ->
                paddedRows.map { it[col] }
                    .filter { it.isDigit() }
                    .joinToString("")
                    .toLongOrNull() ?: 0L
            }.filter { it > 0 }

            when (operator) {
                '*' -> numbers.reduce { acc, n -> acc * n }
                '+' -> numbers.sum()
                else -> error("Unknown operator: $operator")
            }
        }
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