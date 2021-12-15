package com.akafred.aoc.position

data class Position(val forward: Int, val depth: Int) {
    fun next(cmd: Command): Position {
        return when (cmd.direction) {
            "forward" -> Position(this.forward + cmd.distance, this.depth)
            "down" -> Position(this.forward, this.depth + cmd.distance)
            "up" -> Position(this.forward, this.depth - cmd.distance)
            else -> this
        }
    }

    fun product() = this.forward * this.depth
}

data class Command(val direction: String, val distance: Int)

fun calculatePosition(plannedCourse: String) =
    plannedCourse
        .lines()
        .map { it.split(" ") }
        .map { Command(it[0], it[1].toInt()) }
        .fold(Position(0, 0)) { pos, cmd -> pos.next(cmd) }
