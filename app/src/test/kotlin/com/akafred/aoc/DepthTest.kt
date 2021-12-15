package com.akafred.aoc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DepthTest {

    @Test // AoC 1-1
    fun `count depth increases in normal case`() {
        assertEquals(7, Depth.increases(depthMeasurements))
    }

    @Test
    fun `count sliding average depth increases`() {
        assertEquals(5, Depth.slidingAverageIncreases(depthMeasurements))
    }

    private val depthMeasurements = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent().split("\n").map { it.toInt() }

}
