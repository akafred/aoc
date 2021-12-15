package com.akafred.aoc.diagnostics

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

fun powerConsumption(diagnosticData: String): BigInteger {
    val lines = diagnosticData.lines()
    val bits = lines[0].length
    val data = lines.map { it.toBigInteger(2) }

    val counts = (1..bits)
        .map { bit -> data.count { value -> (value and (ONE.shl(bits - bit))) > ZERO }}

    val gamma = counts
        .mapIndexed { bit, count -> if (count * 2 >= lines.size) (ONE.shl(bits - bit - 1)) else ZERO }
        .sumOf { it }

    val epsilon = gamma.inv() and BigInteger("1".repeat(bits), 2)

    return gamma * epsilon
}