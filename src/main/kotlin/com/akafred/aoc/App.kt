package com.akafred.aoc

import com.akafred.aoc.bingo.bingoScore
import com.akafred.aoc.bingo.lastBingoScore
import com.akafred.aoc.depth.*
import com.akafred.aoc.diagnostics.calculateLifeSupportRating
import com.akafred.aoc.position.calculatePosition
import com.akafred.aoc.position.calculatePositionByAim
import com.akafred.aoc.diagnostics.powerConsumption
import com.akafred.aoc.hydrothermal.overlappingPoints
import com.akafred.aoc.packets.calculate
import com.akafred.aoc.packets.decodeAndSumVersions
import java.io.File

class App {
    val greeting: String
        get() {
            return "Hello Advent of Code!"
        }
}

fun main() {
    println(App().greeting)

    println("AoC 1-1 depth increases: ${increases(aoc1Input)}")

    println("AoC 1-2 sliding average depth increases: ${slidingAverageIncreases(aoc1Input)}")

    println("AoC 2-1 position product: ${calculatePosition(aoc2Input).product()}")

    println("AoC 2-2 position by aim product: ${calculatePositionByAim(aoc2Input).product()}")

    println("AoC 3-1 power consumption: ${powerConsumption(aoc3Input)}")

    println("AoC 3-2 life support rating: ${calculateLifeSupportRating(aoc3Input)}")

    println("AoC 4-1 bingo score: ${bingoScore(aoc4Input)}")

    println("AoC 4-2 last bingo score: ${lastBingoScore(aoc4Input)}")

    println("AoC 5-1 overlapping points: ${overlappingPoints(aoc5Input, false)}")

    println("AoC 5-2 overlapping points with diagonal: ${overlappingPoints(aoc5Input)}")

    println("AoC 16-1 sum of packet versions: ${decodeAndSumVersions(aoc16Input)}")

    println("AoC 16-2 packet calculation: ${calculate(aoc16Input)}")
}


val aoc1Input = readFile("aoc1.dat").lines().map(String::toInt)
val aoc2Input = readFile("aoc2.dat")
val aoc3Input = readFile("aoc3.dat")
val aoc4Input = readFile("aoc4.dat")
val aoc5Input = readFile("aoc5.dat")
val aoc16Input = readFile("aoc16.dat")

private fun readFile(filename: String) = File(ClassLoader.getSystemResource(filename).file).readText()