package com.akafred.aoc.power

import java.math.BigInteger

fun powerConsumption(diagnosticData: String): BigInteger {
    val lines = diagnosticData.lines()
    val bits = lines[0].length
    val data = lines.map { it.toBigInteger(2) }

    val counts = (1..bits)
        .map { bit -> data.count { value -> (value and (BigInteger.ONE.shl(bits - bit))) > BigInteger.ZERO }}

    val gamma = counts
        .mapIndexed { bit, count -> if (count * 2 >= lines.size) (BigInteger.ONE.shl(bits - bit - 1)) else BigInteger.ZERO }
        .sumOf { it }

    val epsilon = gamma.inv() and BigInteger("1".repeat(bits), 2)

    return gamma * epsilon
}