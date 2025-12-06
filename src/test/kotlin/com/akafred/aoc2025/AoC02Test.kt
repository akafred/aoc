package com.akafred.aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC02Test {

    private val inputFile = "input02.txt"
    private val example1Answer = 1227775554L
    private val puzzle1Answer = 18595663903L
    private val example2Answer = 4174379265L
    private val puzzle2Answer = 19058204438L

    private val exampleInput1 = """
    11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124        
    """.trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Long {
        return input.lines()
            .first()
            .split(",")
            .map { it.trim() }
            .fold(0) { sum: Long, range ->
                val (start: Long, end: Long) = range.split("-").map { it.toLong()}
                start.rangeTo(end).sumOf { id ->
                    val pId = id.toString()
                    return@sumOf if (pId.length.odd()) {
                        0
                    } else if (pId.take(pId.length / 2) == pId.substring(pId.length / 2)) {
                        id
                    } else {
                        0
                    }
                } + sum
            }
    }

    private fun solve2(input: String): Long {
        return input.lines()
            .first()
            .split(",")
            .map { it.trim() }
            .fold(0) { sum: Long, range ->
                val (start: Long, end: Long) = range.split("-").map { it.toLong()}
                start.rangeTo(end).sumOf { id ->
                    val pId = id.toString()
                    return@sumOf invalid(pId).let { if (it) id else 0 }
                } + sum
            }
    }

    private fun invalid(pId: String): Boolean {
        return 1.rangeTo(pId.length / 2)
            .map { isRecurringAtLength(pId, it) }
            .fold(false) { match, rec -> match || rec }
    }

    private fun isRecurringAtLength(pId: String, seqLen: Int): Boolean {
        return if (pId.length % seqLen != 0) {
            false
        } else {
            val recurs = pId.length / seqLen
            val seq = pId.take(seqLen)
            (1.rangeUntil(recurs))
                .fold(true) { match, i ->
                    match && seq == pId.substring(i * seqLen, (i + 1) * seqLen)
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

private fun Int.odd(): Boolean {
    return this % 2 != 0
}
