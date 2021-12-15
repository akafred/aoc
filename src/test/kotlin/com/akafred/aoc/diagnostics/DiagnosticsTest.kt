package com.akafred.aoc.diagnostics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DiagnosticsTest {

    @Test
    fun `power consumption with epsilon gamma`() {
        assertEquals(BigInteger.valueOf(198), powerConsumption(diagnosticData))
    }

    @Test
    fun `calculate life support rating`() {
        assertEquals(BigInteger.valueOf(230), calculateLifeSupportRating(diagnosticData))
    }

    private val diagnosticData = """
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
}

