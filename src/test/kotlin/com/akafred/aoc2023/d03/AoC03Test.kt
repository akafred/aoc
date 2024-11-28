package com.akafred.aoc2023.d03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.max
import kotlin.math.min

data class Pos(val x: Int, val y: Int)
data class Num(val pos: Pos, val value: Int) {
    fun adjacent(pos: Pos): Boolean {
        return this.pos.x - 1 <= pos.x && pos.x <= this.pos.x + 1
                && this.pos.y - 1 <= pos.y && pos.y <= this.pos.y + this.len
    }
    val len: Int
        get(): Int = value.toString().length
}


private const val GEAR = '*'

class AoC03Test {
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

    private fun partNoSum(input: String): Int {
        fun shouldIncludeNumber(num: Num, lines: List<String>): Boolean {
            val (pos, _) = num
            val (i,j) = pos
            for(x in max(0,i-1)..min(i + 1, lines.size - 1)) {
                for(y in max(0,j-1)..min(j + num.len, lines[0].length - 1)) {
                    if (!lines[x][y].isDigit() && lines[x][y] != '.') return true
                }
            }
            return false
        }

        val lines = input.lines()

        var sum = 0
        lines.forEachIndexed { i, line ->
            var number = ""
            (line + ".").forEachIndexed { j, c ->
                when {
                    c.isDigit() -> number += c
                    !c.isDigit() && number != "" -> {
                        val num = Num(Pos(i, j - number.length), number.toInt())
                        if (shouldIncludeNumber(num, lines)) {
                            sum += num.value
                        }
                        number = ""
                    }
                }
            }
        }
        return sum
    }

    private fun sumGearProduct(input: String): Int {
        val lines = input.lines().toTypedArray()

        // find all numbers
        val numbers = mutableListOf<Num>()
        lines.forEachIndexed { i, line ->
            var number = ""
            (line + ".").forEachIndexed { j, c ->
                when {
                    c.isDigit() -> number += c
                    !c.isDigit() && number != "" -> {
                        numbers.add(Num(Pos(i, j - number.length), number.toInt()))
                        number = ""
                    }
                }
            }
        }

        var sumProduct = 0
        lines.forEachIndexed { i, line ->
           line.forEachIndexed { j, c ->
               if (c == GEAR) {
                   val adjacent = mutableListOf<Num>()
                   for (num in numbers) {
                       if (num.adjacent(Pos(i,j))) {
                           adjacent.add(num)
                       }
                   }
                   if (adjacent.size == 2) {
                       println(adjacent)
                       sumProduct += adjacent.fold(1){product, num -> product * num.value }
                   }
               }
           }
        }
        return sumProduct
    }


    @Test
    fun `example 1`() {
        val result = partNoSum(input)
        assertEquals(4361, result)
    }

    @Test
    fun `example 2`() {
        val result = sumGearProduct(input)
        assertEquals(467835, result)
    }


    @Test
    fun `AoC 2023-03-1`() {
        val input = readFile("input.txt")
        val result = partNoSum(input)
        assertEquals(540212, result)
    }

    @Test
    fun `AoC 2023-03-2`() {
        val input = readFile("input.txt")
        val result = sumGearProduct(input)
        assertEquals(87605697, result)
    }

    private fun readFile(filename: String): String {
        val resource = javaClass.getResource(filename)
        return File(resource!!.file).readText()
    }
}
