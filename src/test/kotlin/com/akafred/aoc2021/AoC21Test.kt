package com.akafred.aoc2021

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull

class AoC21Test {
    @Test fun appHasAGreeting() {
        val classUnderTest = AoC21()
        assertNotNull(classUnderTest.greeting, "app should have a greeting")
    }
}
