package com.akafred.aoc2021.aoc21_22

import java.lang.Integer.max
import java.lang.Integer.min
import java.math.BigInteger
import java.math.BigInteger.ZERO

fun cubeInitOnCount(input: String, limited: Boolean = true): BigInteger {
    return if (limited) naiveCubeInitOnCount(input).toBigInteger() else complexCubeInitOnCount(input)
}

typealias Cube = Triple<Int, Int, Int>

fun naiveCubeInitOnCount(input: String): Int {
    val stepRegex = """^(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)${'$'}""".toRegex()
    val onCubes = mutableSetOf<Cube>()
    input.lines()
        .forEach { inputLine ->
            val (onoff, x01, x02, y01, y02, z01, z02) = stepRegex.find(inputLine)!!.destructured

            val on = onoff == "on"
            val x1 = min(x01.toInt(), x02.toInt())
            val x2 = max(x01.toInt(), x02.toInt())
            val y1 = min(y01.toInt(), y02.toInt())
            val y2 = max(y01.toInt(), y02.toInt())
            val z1 = min(z01.toInt(), z02.toInt())
            val z2 = max(z01.toInt(), z02.toInt())
            if (x1 <= 50 && x2 >= -50 && y1 <= 50 && y2 >= -50 && z1 <= 50 && z2 >= -50) {
                (max(x1, -50)..min(x2, 50)).forEach { x ->
                    (max(y1, -50)..min(y2, 50)).forEach { y ->
                        (max(z1, -50)..min(z2, 50)).forEach { z ->
                            if (on) {
                                onCubes.add(Triple(x, y, z))
                            } else {
                                onCubes.remove(Triple(x, y, z))
                            }
                        }
                    }
                }
            }
        }
    return onCubes.size
}

typealias Axis = Pair<Int, Int>


val Axis.min: Int get() = this.first
val Axis.max: Int get() = this.second


typealias Cuboid = Triple<Axis, Axis, Axis>

val Cuboid.x: Axis get() = this.first
val Cuboid.y: Axis get() = this.second
val Cuboid.z: Axis get() = this.third
operator fun Cuboid.get(i: Int) = this.toList()[i]
val Cuboid.cubeCount: BigInteger
    get() = listOf(
        this.x,
        this.y,
        this.z
    ).map { BigInteger.ONE + it.max.toBigInteger() - it.min.toBigInteger() }.reduce(BigInteger::times)

data class Step(val on: Boolean, val c: Cuboid)

fun complexCubeInitOnCount(input: String): BigInteger {
    val stepRegex = """^(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)${'$'}""".toRegex()
    val steps =
        input.lines()
            .map { inputLine ->
                val (onoff, x01, x02, y01, y02, z01, z02) = stepRegex.find(inputLine)!!.destructured
                Step(
                    onoff == "on",
                    Cuboid(
                        Axis(min(x01.toInt(), x02.toInt()), max(x01.toInt(), x02.toInt())),
                        Axis(min(y01.toInt(), y02.toInt()), max(y01.toInt(), y02.toInt())),
                        Axis(min(z01.toInt(), z02.toInt()), max(z01.toInt(), z02.toInt()))
                    )
                )
            }

    val onCuboids: List<Cuboid> =
        steps.fold(listOf<Cuboid>(steps[0].c)) { cuboids, step ->
            cuboids.fold(Pair<List<Cuboid>,Step?>(listOf(), step)) { acc, nextCuboid ->
                    combine(acc.first, acc.second, nextCuboid)
                }.let {
                    if (it.second != null && it.second!!.on) it.first + (it.second as Step).c else it.first
                }
        }

    return if(onCuboids.isEmpty()) ZERO else onCuboids.map { it.cubeCount }.reduce(BigInteger::plus)
}

fun combine(cuboids: List<Cuboid>, step: Step?, nextCuboid: Cuboid): Pair<List<Cuboid>, Step?> {
    return if (step == null) {
        Pair(cuboids + nextCuboid, null)
    } else if (step.on) {
        when {
            disjunct(nextCuboid, step.c) -> Pair(cuboids + nextCuboid, step)
            encloses(nextCuboid, step.c) -> Pair(cuboids + nextCuboid, null)
            else -> Pair(cuboids + filterNewCuboids(split(nextCuboid, step.c), step), step)
        }
    } else {// subStep.off
        when {
            disjunct(nextCuboid, step.c) -> Pair(cuboids + nextCuboid, step)
            encloses(nextCuboid, step.c) -> Pair(cuboids + filterNewCuboids(split(nextCuboid, step.c), step), step)
            else -> Pair(cuboids + filterNewCuboids(split(nextCuboid, step.c), step), step)
        }
    }
}

fun filterNewCuboids(cuboids: List<Cuboid>, substep: Step): List<Cuboid> {
    val result =
        cuboids.filter { cuboid ->
            if (substep.on) {
                when {
                    disjunct(substep.c, cuboid) -> true
                    encloses(substep.c, cuboid) -> false
                    else -> TODO("Not yet implemented") // This shouldn't happen
                }
            } else { // off
                when {
                    disjunct(substep.c, cuboid) -> true
                    encloses(substep.c, cuboid) -> false
                    else -> TODO("Not yet implemented") // This shouldn't happen
                }
            }
        }
    return result
}

fun split(cuboidToSplit: Cuboid, splitter: Cuboid): List<Cuboid> {
    val result = (0..5) // planes
        .fold(listOf(cuboidToSplit)) { cuboidsToSplit, plane ->
            cuboidsToSplit.map { toSplit ->
                val axis = plane / 2
                val min = plane % 2 == 0
                if (min && splitter[axis].min > toSplit[axis].min && splitter[axis].min <= toSplit[axis].max) {
                        splitAlongAxis(toSplit, axis, splitter[axis].min - 1)
                } else if (!min && splitter[axis].max >= toSplit[axis].min && splitter[axis].max < toSplit[axis].max) {
                        splitAlongAxis(toSplit, axis, splitter[axis].max)
                } else listOf(toSplit)
            }.flatten()
        }
    return result
}

fun splitAlongAxis(toSplit: Cuboid, axis: Int, splitOn: Int): List<Cuboid> {
    return when (axis) {
        0 -> listOf(
            Cuboid(Axis(toSplit.x.min, splitOn), toSplit.y, toSplit.z),
            Cuboid(Axis(splitOn + 1, toSplit.x.max), toSplit.y, toSplit.z)
        )
        1 -> listOf(
            Cuboid(toSplit.x, Axis(toSplit.y.min, splitOn), toSplit.z),
            Cuboid(toSplit.x, Axis(splitOn + 1, toSplit.y.max), toSplit.z)
        )
        2 -> listOf(
            Cuboid(toSplit.x, toSplit.y, Axis(toSplit.z.min, splitOn)),
            Cuboid(toSplit.x, toSplit.y, Axis(splitOn + 1, toSplit.z.max))
        )
        else -> throw IllegalArgumentException("Unknown axis")
    }
}

fun disjunct(c1: Cuboid, c2: Cuboid) =
    c1.x.min > c2.x.max || c1.x.max < c2.x.min
            || c1.y.min > c2.y.max || c1.y.max < c2.y.min
            || c1.z.min > c2.z.max || c1.z.max < c2.z.min

fun encloses(c1: Cuboid, c2: Cuboid) =
    c1.x.min <= c2.x.min && c1.x.max >= c2.x.max
            && c1.y.min <= c2.y.min && c1.y.max >= c2.y.max
            && c1.z.min <= c2.z.min && c1.z.max >= c2.z.max



