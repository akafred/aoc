package com.akafred.aoc2021.aoc21_03

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

fun powerConsumption(diagnosticData: String): BigInteger {
    val lines = diagnosticData.lines()
    val bits = lines[0].length
    val data = lines.map { it.toBigInteger(2) }

    val counts = (bits downTo 1).map { bit -> data.count { it.hasBitSet(bit) } }

    val gamma = counts
        .mapIndexed { bit, count -> if (count * 2 >= lines.size) ((bits - bit).bitValue()) else ZERO }
        .sumOf { it }

    val epsilon = gamma.inv() and BigInteger("1".repeat(bits), 2)

    return gamma * epsilon
}

private fun Int.bitValue() = ONE.shl(this - 1)

fun calculateLifeSupportRating(diagnosticData: String): BigInteger {
    val lines = diagnosticData.lines()
    val bits = lines[0].length
    val data = lines.map { it.toBigInteger(2) }

    val oxygenGeneratorRating = rating(data, bits, true)
    val co2ScrubberRating = rating(data, bits, false)
    return oxygenGeneratorRating * co2ScrubberRating
}

private fun rating(data: List<BigInteger>, bits: Int, most: Boolean): BigInteger {
    var keepers = data
    var bit = bits
    while (keepers.size > 1) {
        val candidates = keepers.partition { it.hasBitSet(bit) }
        keepers =
            if (most) {
                if (candidates.first.size >= candidates.second.size) candidates.first else candidates.second
            } else {
                if (candidates.first.size < candidates.second.size) candidates.first else candidates.second
            }
        bit--
    }
    return keepers[0]
}

private fun BigInteger.hasBitSet(bitFromLeft: Int) = (this and (ONE.shl(bitFromLeft - 1))) > ZERO
