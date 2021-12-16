package com.akafred.aoc.aoc21_07

import kotlin.math.abs
import kotlin.math.roundToInt

fun minFuel(input: String): Int {
    val positions = input.split(",").map(String::toInt).sorted()
    return fuelUse(positions, medianPosition(positions))
}

fun realMinFuel(input: String): Int {
    val positions = input.split(",").map(String::toInt).sorted()

    var guess = averagePosition(positions)
    var lastFuelUse = Int.MAX_VALUE

    while (realFuelUse(positions, guess) < lastFuelUse) {
        lastFuelUse = realFuelUse(positions, guess)
        if (realFuelUse(positions, guess + 1) < lastFuelUse) {
            guess++
        } else if (realFuelUse(positions, guess - 1) < lastFuelUse) {
            guess--
        }
    }

    return realFuelUse(positions, guess)
}

private fun fuelUse(positions: List<Int>, position: Int) = positions.sumOf { abs(it - position) }

private fun realFuelUse(positions: List<Int>, position: Int) = positions.sumOf { (0..abs(it - position)).sum() }

private fun medianPosition(positions: List<Int>) =
    if (positions.size % 2 == 0) {
        listOf(positions[positions.size / 2], positions[(positions.size + 1)/ 2]).average().roundToInt()
    } else {
        positions[(positions.size + 1) / 2]
    }

private fun averagePosition(positions: List<Int>) = positions.average().roundToInt()
