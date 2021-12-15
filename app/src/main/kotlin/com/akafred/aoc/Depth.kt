package com.akafred.aoc

class Depth {
    companion object {
        fun increases(values: List<Int>) =
            (listOf(values[0]) + values).zip(values).count { (v0, v1) -> v1 > v0  }

        private const val slidingWindowSize = 3

        fun slidingAverageIncreases(values: List<Int>) =
            (0 until values.size - slidingWindowSize).count { values[it + slidingWindowSize] > values[it] }
    }
}