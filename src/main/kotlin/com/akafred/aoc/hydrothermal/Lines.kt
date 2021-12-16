package com.akafred.aoc.hydrothermal

import java.lang.Integer.max
import java.lang.Integer.min

typealias Point = Pair<Int,Int>
typealias Line = Pair<Point, Point>

fun overlappingPoints(input: String, withDiagonal: Boolean = true): Int {
    val endpointPairs = input.lines().map { inputLine -> endpointPairs(inputLine) }

    val points = endpointPairs
        .flatMap { (start, end) ->
            when {
                isRow(start, end) -> rowPoints(start, end)
                isCol(start, end) -> colPoints(start, end)
                else -> if (withDiagonal) diagonalPoints(start, end) else emptyList()
            }
        }

    return points.groupingBy { it }.eachCount().values.count { it > 1 }
}

private fun isCol(start: Point, end: Point) = start.second == end.second

private fun isRow(start: Point, end: Point) = start.first == end.first

private fun endpointPairs(inputLine: String): Line {
    val endPoints = inputLine
        .split(" -> ")
        .map { point ->
            val value = point.split(",").map { it.toInt() }
            Point(value[0], value[1])
        }
    return endPoints[0] to endPoints[1]
}

private fun colPoints(start: Point, end: Point) =
    (min(start.first, end.first)..max(start.first, end.first)).map { Point(it, start.second) }.toList()

private fun rowPoints(start: Point, end: Point) =
    (min(start.second, end.second)..max(start.second, end.second)).map { Point(start.first, it) }.toList()

private fun diagonalPoints(start: Point, end: Point) =
    (0 .. (max(start.first, end.first) - min(start.first, end.first)))
        .map{ i ->
            Point(
                start.first + i * if (end.first > start.first) 1 else -1,
                start.second + i * if (end.second > start.second) 1 else -1
            )
        }
