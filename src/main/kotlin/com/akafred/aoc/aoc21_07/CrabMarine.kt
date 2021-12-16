package com.akafred.aoc.aoc21_07

import kotlin.math.abs
import kotlin.math.roundToInt

fun minFuel(input: String): Int {
    val positions = input.split(",").map(String::toInt).sorted()
    return fuelUse(positions, medianPosition(positions))
}

private fun fuelUse(positions: List<Int>, position: Int) = positions.sumOf { abs(it - position) }

private fun medianPosition(positions: List<Int>) =
    if (positions.size % 2 == 0) {
        listOf(positions[positions.size / 2], positions[(positions.size + 1)/ 2]).average().roundToInt()
    } else {
        positions[(positions.size + 1) / 2]
    }
