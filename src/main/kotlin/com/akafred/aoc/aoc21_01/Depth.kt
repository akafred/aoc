package com.akafred.aoc.aoc21_01

fun increases(values: List<Int>) = slidingAverageIncreases(values, 1)

private const val defaultSlidingWindowSize = 3

fun slidingAverageIncreases(values: List<Int>, slidingWindowSize: Int = defaultSlidingWindowSize) =
    (0 until values.size - slidingWindowSize).count { values[it + slidingWindowSize] > values[it] }
