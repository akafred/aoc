package com.akafred.aoc.aoc21_17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LaunchingTest {
    @Test
    fun `hits the target area`() {
        val input = "target area: x=20..30, y=-10..-5"
        assertEquals(45, trajectoryPeak(input))
    }

    @Test
    fun `calculates all valid trajectories`() {
        val input = "target area: x=20..30, y=-10..-5"
        assertEquals(112, launchOptionsCount(input))
    }

    @Test
    fun `finds target box`() {
        val input = "target area: x=20..30, y=-10..-5"
        assertEquals(Box(Point(20, -5), Point(30, -10)), targetArea(input))
    }
}
