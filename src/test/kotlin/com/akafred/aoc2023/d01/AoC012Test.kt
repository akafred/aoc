package com.akafred.aoc2023.d01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class AoC012Test {
    @Test
    fun `sum of calibration values`() {
        val input = "two1nine\n" +
                "eightwothree\n" +
                "abcone2threexyz\n" +
                "xtwone3four\n" +
                "4nineeightseven2\n" +
                "zoneight234\n" +
                "7pqrstsixteen"
        val result = calibrationAlfaNum(input)
        assertEquals(281, result)
    }

    private fun calibrationAlfaNum(input: String): Int = input
        .lines()
        .map { line -> digitConverter(line) }
        .map { digits -> digits.first().toString() + digits.last().toString() }
        .map { text -> text.toInt() }
        .sum()

    private fun digitConverter(line: String): String {
        val digits = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        return line.mapIndexedNotNull { i, c ->
            if (c.isDigit()) c.toString()
            else {
                digits.firstOrNull { digit ->
                    line.substring(i).startsWith(digit)
                }.let { if(it == null) null else (digits.indexOf(it) + 1).toString() }
            }
        }.joinToString()
    }

    @Test
    fun `test digitConverter`() {
        assertEquals("1", digitConverter("one"))
    }

    @Test
    fun `AoC 2023-01-2`() {
        val input = readFile("input.txt")
        val result = calibrationAlfaNum(input)
        assertEquals(54087, result)
    }

    private fun readFile(filename: String): String {
        val resource = javaClass.getResource(filename)
        println(resource)
        return File(resource!!.file).readText()
    }
}