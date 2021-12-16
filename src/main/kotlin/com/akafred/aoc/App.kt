package com.akafred.aoc

import com.akafred.aoc.bingo.bingoScore
import com.akafred.aoc.bingo.lastBingoScore
import com.akafred.aoc.depth.*
import com.akafred.aoc.diagnostics.calculateLifeSupportRating
import com.akafred.aoc.position.calculatePosition
import com.akafred.aoc.position.calculatePositionByAim
import com.akafred.aoc.diagnostics.powerConsumption
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
}


val aoc1Input = readFile("aoc1.dat").lines().map(String::toInt)
val aoc2Input = readFile("aoc2.dat")
val aoc3Input = readFile("aoc3.dat")
val aoc4Input = readFile("aoc4.dat")

private fun readFile(filename: String) = File(ClassLoader.getSystemResource(filename).file).readText()