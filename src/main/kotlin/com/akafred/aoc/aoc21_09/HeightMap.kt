package com.akafred.aoc.aoc21_09

typealias Matrix<A> = List<List<A>>
typealias Cell = Pair<Int,Int>
typealias Dim = Pair<Int,Int>

val Cell.x: Int get() = this.first
val Cell.y: Int get() = this.second
fun Cell.within(dim: Dim): Boolean = (0 <= this.x && this.x < dim.x) && (0 <= this.y && this.y < dim.y)

val <A> Matrix<A>.dim: Dim get() = Pair(this[0].size, this.size)
val <A> Matrix<A>.xs: IntRange get() = (0 until this.dim.x)
val <A> Matrix<A>.ys: IntRange get() = (0 until this.dim.y)

fun <A> Matrix<A>.at(cell: Cell) = this[cell.y][cell.x]

fun <A> Matrix<A>.neighbors(c: Cell): List<Cell> =
    listOf(-1, 1).map { dx -> Cell(c.x + dx, c.y) }.filter { it.within(this.dim) } +
            listOf(-1, 1).map { dy -> Cell(c.x, c.y + dy) }.filter { it.within(this.dim) }


fun lowPointRiskLevel(input: String): Int {
    val heights = input.lines().map { inputLine -> inputLine.toCharArray().map { it.toString().toInt() } }
    val lowPoints =
        heights.xs.flatMap { x ->
            heights.ys.flatMap { y ->
                val current = Cell(x, y)
                val lowPoint = heights.neighbors(current).all { neighbor -> heights.at(current) < heights.at(neighbor) }
                if (lowPoint) listOf(current) else emptyList()
            }
        }
    return lowPoints.sumOf { 1 + heights.at(it) }
}
