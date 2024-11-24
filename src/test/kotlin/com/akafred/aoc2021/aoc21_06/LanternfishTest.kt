package com.akafred.aoc2021.aoc21_06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LanternfishTest {

    @Test
    fun `lanternfish spawning`() {
        val input = "3,4,3,1,2"
        assertEquals(5.toBigInteger(), lanternfishCount(input, 0))
        assertEquals(5.toBigInteger(), lanternfishCount(input, 1))
        assertEquals(26.toBigInteger(), lanternfishCount(input, 18))
        assertEquals(5934.toBigInteger(), lanternfishCount(input, 80))
        assertEquals("26984457539".toBigInteger(), lanternfishCount(input, 256))
    }
}