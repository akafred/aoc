package com.akafred.aoc.aoc21_09

typealias Matrix<A> = List<List<A>>
typealias Cell = Pair<Int,Int>
typealias Dim = Pair<Int,Int>
typealias Basin = Set<Cell>

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
    val heights = heights(input)
    val lowPoints = lowPoints(heights)
    return lowPoints.sumOf { 1 + heights.at(it) }
}

fun basinsProduct(input: String): Int {
    val heights = heights(input)
    val lowPoints = lowPoints(heights)
    val basins = lowPoints.map { lowPoint -> traverse(heights, lowPoint) }
    return basins.sortedByDescending { it.size }.take(3).fold(1) { acc, next -> acc * next.size }
}

private fun traverse(heights: Matrix<Int>, lowPoint: Cell): Basin {
    var candidates = listOf(lowPoint)
    val basin = mutableSetOf(lowPoint)
    while(candidates.isNotEmpty()) {
        val current = candidates[0]
        basin += current
        val neighbors = heights.neighbors(current).minus(basin).filter { heights.at(it) < 9 }
        candidates = candidates.drop(1) + neighbors
    }
    return basin
}

private fun heights(input: String): Matrix<Int> =
    input.lines().map { inputLine -> inputLine.toCharArray().map { it.toString().toInt() } }

private fun lowPoints(heights: Matrix<Int>): List<Cell> {
    val lowPoints =
        heights.xs.flatMap { x ->
            heights.ys.flatMap { y ->
                val current = Cell(x, y)
                val lowPoint = heights.neighbors(current).all { neighbor -> heights.at(current) < heights.at(neighbor) }
                if (lowPoint) listOf(current) else emptyList()
            }
        }
    return lowPoints
}
