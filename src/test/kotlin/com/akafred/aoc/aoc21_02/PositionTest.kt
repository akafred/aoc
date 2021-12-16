package com.akafred.aoc.aoc21_02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PositionTest {

    // AoC 2-1
    @Test
    fun `position calculator calculates position`() {
        val position = calculatePosition(plannedCourse)

        assertEquals(150, position.product())
    }

    // AoC 2-2
    @Test
    fun `calculate position with aim`() {
        val position = calculatePositionByAim(plannedCourse)

        assertEquals(900, position.product())
    }

    private val plannedCourse = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent()

}