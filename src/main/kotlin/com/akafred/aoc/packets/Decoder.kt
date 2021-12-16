package com.akafred.aoc.packets

import java.math.BigInteger

typealias Pos = Int

fun decodeAndSumVersions(input: String): Int {
    val message = hexToBinary(input)
    val (_, pkgs) = parsePackages(0, message, message.length)
    return pkgs.sumOf(Pkg::versionSum)
}

private fun parsePackages(startPos: Int, message: String, maxBits: Int): Pair<Pos, List<Pkg>> {
    var pos = startPos
    var pkgs = emptyList<Pkg>()

    while (pos < startPos + maxBits - 7) {
        val (newPos, pkg) = parsePackage(pos, message)
        pos = newPos
        pkgs += pkg
    }
    return Pair(pos, pkgs)
}

private fun parseNPackages(startPos: Int, message: String, noPkgs: Int): Pair<Pos,List<Pkg>> {
    var pos = startPos
    var pkgs = emptyList<Pkg>()

    for (i in 0 until noPkgs) {
        val (newPos, pkg) = parsePackage(pos, message)
        pos = newPos
        pkgs += pkg
    }
    return Pair(pos, pkgs)
}

abstract class Pkg(val version: Int, val typeId: Int) {
    abstract fun versionSum(): Int
}

class LiteralPkg(version: Int, typeId: Int, val literal: BigInteger): Pkg(version, typeId) {
    override fun versionSum() = version
}

class OperatorPkg(version: Int, typeId: Int, val pkgs: List<Pkg>) : Pkg(version, typeId) {
    override fun versionSum(): Int = version + pkgs.sumOf(Pkg::versionSum)
}

private fun parsePackage(startPos: Pos, message: String): Pair<Pos,Pkg> {
    val (typeIdPos, version) = parseVersion(startPos, message)
    val (contentPos, typeId) = parseTypeId(typeIdPos, message)
    val (endPos, pkg) =
        when (typeId) {
            4 -> parseLiteral(contentPos, version, typeId, message)
            else -> parseOperator(contentPos, version, typeId, message)
        }
    return Pair(endPos, pkg)
}

private fun parseOperator(startPos: Pos, version: Int, typeId: Int, message: String): Pair<Pos, Pkg> {
    val (valuePos, bitsControlled) = parseLengthControl(startPos, message)
    val (endPos, pkgs) =
        if (bitsControlled) {
            val bits = message.substring(valuePos, valuePos + 15).toInt(2);
            val subPkgPos = valuePos + 15
            parsePackages(subPkgPos, message, bits)
        } else {// no of packages
            val noPkgs = message.substring(valuePos, valuePos + 11).toInt(2);
            val subPkgPos = valuePos + 11
            parseNPackages(subPkgPos, message, noPkgs)
        }
    return Pair(endPos, OperatorPkg(version, typeId, pkgs))

}

private fun parseLengthControl(startPos: Pos, message: String) =
    Pair(startPos + 1, message.substring(startPos, startPos + 1) == "0")

private fun parseLiteral(startPos: Pos, version: Int, typeId: Int, message: String): Pair<Pos, Pkg> {
    var more = true
    var pos = startPos
    var binary = ""
    while (more) {
        val group = message.substring(pos, pos + 5); pos += 5
        more = group[0] == '1'
        binary += group.subSequence(1, 5)
    }
    val literalValue = binary.toBigInteger(2)
    return Pair(pos, LiteralPkg(version, typeId, literalValue))
}

private fun parseVersion(pos: Int, message: String) = Pair(pos + 3, message.substring(pos, pos + 3).toInt(2))

private fun parseTypeId(pos: Int, message: String) = Pair(pos + 3, message.substring(pos, pos + 3).toInt(2))

fun hexToBinary(input: String) =
    input.fold("") { acc, c -> acc + c.digitToInt(16).toString(2).padStart(4, '0') }
