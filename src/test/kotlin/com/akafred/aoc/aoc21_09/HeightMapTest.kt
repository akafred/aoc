package com.akafred.aoc.aoc21_09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeightMapTest {
    @Test
    fun `low point risk level`() {
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()
        assertEquals(15, lowPointRiskLevel(input))
    }
}
