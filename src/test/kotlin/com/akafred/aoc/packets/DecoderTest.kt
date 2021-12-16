package com.akafred.aoc.packets

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class DecoderTest {

    @Test
    fun `decodes`() {
        assertEquals(6, decodeAndSumVersions("D2FE28"))
        assertEquals(16, decodeAndSumVersions("8A004A801A8002F478"))
        assertEquals(12, decodeAndSumVersions("620080001611562C8802118E34"))
        assertEquals(23, decodeAndSumVersions("C0015000016115A2E0802F182340"))
        assertEquals(31, decodeAndSumVersions("A0016C880162017C3686B18A3D4780"))
    }

    @Test
    fun `hexToBinary`() {
        assertEquals("100010100000", hexToBinary("8A0"))
    }

}
