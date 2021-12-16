package com.akafred.aoc.aoc21_06

import java.math.BigInteger
import java.math.BigInteger.ZERO

fun lanternfishCount(input: String, days: Int): BigInteger {
    val ageCount = Array(9) { ZERO }

    input.split(",").map(String::toInt).forEach { i -> ageCount[i]++ }

    (1..days).forEach {
        val spawn = ageCount[0]
        ageCount[0] = ageCount[1]
        ageCount[1] = ageCount[2]
        ageCount[2] = ageCount[3]
        ageCount[3] = ageCount[4]
        ageCount[4] = ageCount[5]
        ageCount[5] = ageCount[6]
        ageCount[6] = ageCount[7] + spawn
        ageCount[7] = ageCount[8]
        ageCount[8] = spawn
    }

    return ageCount.reduce{i1, i2 -> i1 + i2}
}
