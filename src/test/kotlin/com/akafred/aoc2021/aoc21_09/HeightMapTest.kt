package com.akafred.aoc2021.aoc21_09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeightMapTest {
    @Test
    fun `low point risk level`() {
        assertEquals(15, lowPointRiskLevel(input))
    }

    @Test
    fun `basins product`() {
        assertEquals(1134, basinsProduct(input))
    }

    private val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()
}
