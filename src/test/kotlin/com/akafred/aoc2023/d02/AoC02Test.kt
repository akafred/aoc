package com.akafred.aoc2023.d02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

typealias Draw = Map<String, Int>
typealias Draws = List<Draw>

class Game(line: String) {
    val id: Int = line.split(":").first().split(" ").last().toInt()
    val draws: Draws =
        line
            .split(":")
            .last()
            .split(";")
            .map { draw ->
                draw.split(",")
                    .map { it.trim() }
                    .map { colorCount -> colorCount.split(" ") }
                    .associate { it.last() to it.first().toInt() }
            }

    fun isPossible(): Boolean =
        draws.all {
            it.getOrDefault("red", 0) <= 12
                    && it.getOrDefault("green", 0) <= 13
                    && it.getOrDefault("blue", 0) <= 14
        }

    fun fewest(): Draw =
        listOf("red", "green", "blue")
            .associateWith { color ->
                draws
                    .map { draw -> draw.getOrDefault(color, 0) }
                    .maxOf { it }
        }
}

fun power(draw: Draw): Int = draw.values.fold(1) { acc: Int, i: Int -> acc * i }


class AoC02Test {
    val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"

    @Test
    fun `example 1`() {
        val result = cubeCheck(input)
        assertEquals(8, result)
    }

    @Test
    fun `example 2`() {
        val result = powerSum(input)
        assertEquals(2286, result)
    }

    private fun powerSum(input: String): Int =
        input.lines()
            .map { line -> Game(line) }
            .map { power(it.fewest()) }
            .sum()

    private fun cubeCheck(input: String): Int =
        input.lines()
            .map { line -> Game(line) }
            .filter { it.isPossible() }
            .sumOf { it.id }

    @Test
    fun `AoC 2023-02-1`() {
        val input = readFile("input.txt")
        val result = cubeCheck(input)
        assertEquals(2810, result)
    }

    @Test
    fun `AoC 2023-02-2`() {
        val input = readFile("input.txt")
        val result = powerSum(input)
        assertEquals(69110, result)
    }

    private fun readFile(filename: String): String {
        val resource = javaClass.getResource(filename)
        println(resource)
        return File(resource!!.file).readText()
    }
}
