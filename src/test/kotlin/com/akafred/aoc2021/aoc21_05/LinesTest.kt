package com.akafred.aoc2021.aoc21_05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LinesTest {
    @Test
    fun `lines crossing`() {
        assertEquals(5, overlappingPoints(input, withDiagonal = false))
    }

    @Test
    fun `lines crossing including diagonal`() {
        assertEquals(12, overlappingPoints(input))
    }

    private val input = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()
}