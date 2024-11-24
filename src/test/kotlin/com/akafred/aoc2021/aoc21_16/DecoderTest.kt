package com.akafred.aoc2021.aoc21_16

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
    fun `calculates`() {
        assertEquals(3, calculate("C200B40A82").toInt()) // finds the sum of 1 and 2, resulting in the value 3.
        assertEquals(54, calculate("04005AC33890").toInt()) // finds the product of 6 and 9, resulting in the value 54.
        assertEquals(7, calculate("880086C3E88112").toInt()) // finds the minimum of 7, 8, and 9, resulting in the value 7.
        assertEquals(9, calculate("CE00C43D881120").toInt()) // finds the maximum of 7, 8, and 9, resulting in the value 9.
        assertEquals(1, calculate("D8005AC2A8F0").toInt()) // produces 1, because 5 is less than 15.
        assertEquals(0, calculate("F600BC2D8F").toInt()) // produces 0, because 5 is not greater than 15.
        assertEquals(0, calculate("9C005AC2F8F0").toInt()) // produces 0, because 5 is not equal to 15.
        assertEquals(1, calculate("9C0141080250320F1802104A08").toInt()) // produces 1, because 1 + 3 = 2 * 2.
    }

    @Test
    fun `hexToBinary`() {
        assertEquals("100010100000", hexToBinary("8A0"))
    }

}
