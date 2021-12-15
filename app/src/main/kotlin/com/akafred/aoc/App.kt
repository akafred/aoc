package com.akafred.aoc

import com.akafred.aoc.depth.*
import com.akafred.aoc.position.calculatePosition
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
}


val aoc1Input = readFile("aoc1.txt").lines().map(String::toInt)
val aoc2Input = readFile("aoc2.txt")

private fun readFile(filename: String) = File(ClassLoader.getSystemResource(filename).file).readText()