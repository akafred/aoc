package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AoC01Test {
    val input =
            "467..114..\n" +
            "...*......\n" +
            "..35..633.\n" +
            "......#...\n" +
            "617*......\n" +
            ".....+.58.\n" +
            "..592.....\n" +
            "......755.\n" +
            "...$.*....\n" +
            ".664.598.."

    fun solve1(input: String): Int {
        TODO("Not yet implemented")
    }

    fun solve2(input: String): Int {
        TODO("Not yet implemented")
    }

    @Test
    fun `example 1`() {
        val result = solve1(input)
        assertEquals(-1, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile("input.txt")
        val result = solve1(input)
        assertEquals(-1, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(input)
        assertEquals(-1, result)
    }



    @Test
    fun `puzzle 2`() {
        val input = Util.readFile("input01.txt")
        val result = solve2(input)
        assertEquals(-1, result)
    }
}
