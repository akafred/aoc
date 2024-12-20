package com.akafred.aoc2021.aoc21_07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CrabMarineTest {
    @Test
    fun `align crab submarines`() {
        val input = "16,1,2,0,4,2,7,1,2,14"
        assertEquals(37, minFuel(input))
    }

    @Test
    fun `align crab submarines for realz`() {
        val input = "16,1,2,0,4,2,7,1,2,14"
        assertEquals(168, realMinFuel(input))
    }
}