package com.akafred.aoc.position

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PositionTest {

    // AoC 2-1
    @Test
    fun `position calculator calculates position`() {
        val plannedCourse = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent()

        val position = calculatePosition(plannedCourse)

        Assertions.assertEquals(150, position.product())
    }
}