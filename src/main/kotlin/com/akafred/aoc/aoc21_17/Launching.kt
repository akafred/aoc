package com.akafred.aoc.aoc21_17

import kotlin.math.roundToInt
import kotlin.math.sqrt

typealias Point = Pair<Int,Int>
typealias Box = Pair<Point, Point>
val Point.x: Int get() = first
val Point.y: Int get() = second
val Box.start: Point get() = first
val Box.end: Point get() = second

fun trajectoryPeak(input: String): Int {
    val target = targetArea(input)
    val vxmin = vxmin(target.start.x)
    val vxmax = target.end.x
    var maxHeight = Int.MIN_VALUE
    // var maxHeightVel: Pair<Int, Int>? = null
    for (vx in (vxmin..vxmax)) {
        for (vy in (0..1000)) {
            val height = trajectoryMaxHeight(target, vx, vy)
            if(height > maxHeight) {
                maxHeight = height
                // maxHeightVel = Pair(vx, vy)
                // println(" ($vx,$vy) -> $height")
            }
        }
    }
    return maxHeight
}

fun trajectoryMaxHeight(target: Box, vx: Int, vy: Int): Int {
    var vel = Pair(vx,vy)
    var pos = Point(0,0)
    var peak = pos.y
    while(pos !in target && pos.y >= Integer.min(0, target.end.y) && pos.x <= target.end.x) {
        pos = Point(pos.x + vel.first, pos.y + vel.second)
        vel = Pair(Integer.max(0, vel.first - 1), vel.second - 1)
        peak = Integer.max(peak, pos.y)
        //print(pos)
    }
    return if (pos in target) peak else Int.MIN_VALUE
}

private operator fun Box.contains(pos: Point) =
    (this.start.x <= pos.x && pos.x <= this.end.x) && this.start.y >= pos.y && pos.y >= this.end.y

private fun vxmin(minDist: Int) = sqrt(2.0 * minDist + 1.0 / 4).roundToInt() // based on quadratic equation - min speed to reach target

fun targetArea(input: String) =
    "target area: x=([0-9]+)\\.\\.([0-9]+), y=([-0-9]+)\\.\\.([-0-9]+)"
        .toRegex()
        .find(input)!!
        .groupValues
        .drop(1)
        .map { it.toInt() }
        .let { values -> Box(Point(values[0], values[3]), Point(values[1], values[2])) }

