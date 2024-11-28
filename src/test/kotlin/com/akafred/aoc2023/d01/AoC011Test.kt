package com.akafred.aoc2023.d01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class AoC011Test {
    @Test
    fun `sum of calibration values`() {
        val input = "1abc2\n" +
                "pqr3stu8vwx\n" +
                "a1b2c3d4e5f\n" +
                "treb7uchet"
        val result = calibration(input)
        assertEquals(142, result)
    }

    private fun calibration(input: String): Int = input
        .lines()
        .map { line -> line.filter { it.isDigit() } }
        .map { digits -> digits.first().toString() + digits.last().toString() }
        .map { text -> text.toInt() }
        .sum()

    @Test
    fun `AoC 2023-01-1`() {
        val input = readFile("input.txt")
        val result = calibration(input)
        assertEquals(54708, result)
    }

    private fun readFile(filename: String): String {
        val resource = javaClass.getResource(filename)
        println(resource)
        return File(resource!!.file).readText()
    }
}