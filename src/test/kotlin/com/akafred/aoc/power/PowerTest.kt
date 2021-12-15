package com.akafred.aoc.power

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

class PowerTest {

    @Test
    fun `power consumption with epsilon gamma`() {
        val diagnosticData = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent()

        assertEquals(BigInteger.valueOf(198), powerConsumption(diagnosticData))
    }
}

