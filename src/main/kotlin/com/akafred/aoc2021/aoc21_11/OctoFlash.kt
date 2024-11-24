package com.akafred.aoc2021.aoc21_11

import java.lang.Integer.max
import java.lang.Integer.min

const val JUST_EXPLODED = -1
const val NEIGHBORS_HANDLED = -2

fun flashCount(input: String, steps: Int): Int {
    val grid = readGrid(input)
    var flashes = 0
    (0 until steps).forEach { step ->
        increment(grid)
        flashes += flashEmOut(grid)
        setFlashedToZero(grid)
    }
    return flashes
}

fun syncFlashSteps(input: String): Int {
    val grid = readGrid(input)
    var steps = 0
    do {
        steps++
        increment(grid)
        val stepFlashes = flashEmOut(grid)
        setFlashedToZero(grid)
    } while (stepFlashes < 10*10)
    return steps
}


private fun flashEmOut(grid: Array<IntArray>): Int {
    var stepFlashes = 0
    do {
        val newFlashes = markedFlashed(grid)
        increaseNeigbours(grid)
        stepFlashes += newFlashes
    } while (newFlashes > 0)
    return stepFlashes
}

private fun setFlashedToZero(grid: Array<IntArray>) {
    (0..9).forEach { i ->
        (0..9).forEach { j ->
            if (grid[i][j] < 0) {
                grid[i][j] = 0
            }
        }
    }
}

private fun increaseNeigbours(grid: Array<IntArray>) {
    (0..9).forEach { i ->
        (0..9).forEach { j ->
            if (grid[i][j] == JUST_EXPLODED) {
                grid[i][j] = NEIGHBORS_HANDLED
                (max(0, i - 1)..min(9, i + 1)).forEach { ni ->
                    (max(0, j - 1)..min(9, j + 1)).forEach { nj ->
                        if (grid[ni][nj] >= 0)
                            grid[ni][nj]++
                    }
                }
            }
        }
    }
}

private fun markedFlashed(grid: Array<IntArray>): Int {
    var newFlashes = 0
    (0..9).forEach { i ->
        (0..9).forEach { j ->
            if (grid[i][j] > 9) {
                newFlashes++
                grid[i][j] = JUST_EXPLODED
            }
        }
    }
    return newFlashes
}

private fun increment(grid: Array<IntArray>) {
    (0..9).forEach { i ->
        (0..9).forEach { j ->
            grid[i][j]++
        }
    }
}

private fun readGrid(input: String): Array<IntArray> =
    input.lines().map { line ->
        line.toCharArray()
            .map { it.digitToInt() }
            .toIntArray()
    }.toTypedArray()
