package com.akafred.aoc

import com.akafred.aoc.depth.*
import java.io.File

class App {
    val greeting: String
        get() {
            return "Hello Advent of Code!"
        }
}

fun main() {
    println(App().greeting)

    println("AoC 1-1 depth increases: ${increases(aoc1_1_input)}")

    println("AoC 1-2 sliding average depth increases: ${slidingAverageIncreases(aoc1_1_input)}")

}


val aoc1_1_input = readFile("aoc1.txt").lines().map(String::toInt)

private fun readFile(filename: String) = File(ClassLoader.getSystemResource(filename).file).readText()