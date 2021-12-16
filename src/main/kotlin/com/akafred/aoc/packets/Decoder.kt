package com.akafred.aoc.packets

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

typealias Pos = Int

fun decodeAndSumVersions(input: String): Int {
    val message = hexToBinary(input)
    val (_, pkg) = parsePackage(0, message)
    return pkg.versionSum()
}

fun calculate(input: String): BigInteger {
    val message = hexToBinary(input)
    val (_, pkg) = parsePackage(0, message)
    return pkg.eval()
}

fun printIt(input: String) {
    val message = hexToBinary(input)
    val (_, pkg) = parsePackage(0, message)
    println(pkg.print())
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
    abstract fun eval(): BigInteger
    abstract fun print(): String
}

class LiteralPkg(version: Int, typeId: Int, val literal: BigInteger): Pkg(version, typeId) {
    override fun versionSum() = version
    override fun eval(): BigInteger = literal
    override fun print(): String = literal.toString()
}

abstract class OperatorPkg(version: Int, typeId: Int, val pkgs: List<Pkg>) : Pkg(version, typeId) {
    override fun versionSum(): Int = version + pkgs.sumOf(Pkg::versionSum)
}

class SumPackage(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = pkgs.sumOf(Pkg::eval)
    override fun print() = "(" + pkgs.joinToString("+", transform = Pkg::print) + ")"
}

class ProductPackage(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = pkgs.fold(ONE) { acc, pkg -> acc.multiply(pkg.eval()) }
    override fun print() = "(" + pkgs.joinToString("*", transform = Pkg::print) + ")"
}

class MinimumPackage(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = pkgs.fold(BigInteger.valueOf(Long.MAX_VALUE)) { acc, pkg -> acc.min(pkg.eval()) }
    override fun print() = "min(" + pkgs.joinToString(";", transform = Pkg::print) + ")"
}

class MaximumPackage(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = pkgs.fold(ZERO) { acc, pkg -> acc.max(pkg.eval()) }
    override fun print() = "max(" + pkgs.joinToString(";", transform = Pkg::print) + ")"
}

class GreaterThanPacket(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = if (pkgs[0].eval() > pkgs[1].eval()) ONE else ZERO
    override fun print() = "if(" + pkgs[0].print() + ">" + pkgs[1].print() +";1;0)"
}

class LessThanPacket(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = if (pkgs[0].eval() < pkgs[1].eval()) ONE else ZERO
    override fun print() = "if(" + pkgs[0].print() + "<" + pkgs[1].print() +";1;0)"
}

class EqualToPacket(version: Int, typeId: Int, pkgs: List<Pkg>): OperatorPkg(version, typeId, pkgs) {
    override fun eval() = if (pkgs[0].eval() == pkgs[1].eval()) ONE else ZERO
    override fun print() = "if(" + pkgs[0].print() + "=" + pkgs[1].print() +";1;0)"
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
    val pkg = when(typeId) {
        0 -> SumPackage(version, typeId, pkgs)
        1 -> ProductPackage(version, typeId, pkgs)
        2 -> MinimumPackage(version, typeId, pkgs)
        3 -> MaximumPackage(version, typeId, pkgs)
        5 -> GreaterThanPacket(version, typeId, pkgs)
        6 -> LessThanPacket(version, typeId, pkgs)
        7 -> EqualToPacket(version, typeId, pkgs)
        else -> throw UnsupportedOperationException("Package with typeId ${typeId} is unknown.")
    }

    return Pair(endPos, pkg)

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
