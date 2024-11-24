package com.akafred.aoc2021.aoc21_11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OctoFlashTest {

    @Test
    fun `simple flash count`() {
        assertEquals(0, flashCount(input, 1))
        assertEquals(35, flashCount(input, 2))
        assertEquals(204, flashCount(input, 10))
        assertEquals(1656, flashCount(input, 100))
    }

    @Test
    fun `sync flash steps`() {
        assertEquals(195, syncFlashSteps(input))
    }
}

val input = """
    5483143223
    2745854711
    5264556173
    6141336146
    6357385478
    4167524645
    2176841721
    6882881134
    4846848554
    5283751526
""".trimIndent()