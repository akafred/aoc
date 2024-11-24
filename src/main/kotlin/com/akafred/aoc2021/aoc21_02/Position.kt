package com.akafred.aoc2021.aoc21_02

data class Position(val forward: Int = 0, val depth: Int = 0) {
    fun next(cmd: Command): Position {
        return when (cmd.direction) {
            "forward" -> Position(this.forward + cmd.value, this.depth)
            "down" -> Position(this.forward, this.depth + cmd.value)
            "up" -> Position(this.forward, this.depth - cmd.value)
            else -> this
        }
    }

    fun product() = this.forward * this.depth
}

data class PositionWithAim(val forward: Int = 0, val depth: Int = 0, val aim: Int = 0) {
    fun next(cmd: Command): PositionWithAim {
        return when (cmd.direction) {
            "forward" -> PositionWithAim(this.forward + cmd.value, this.depth + cmd.value * this.aim, this.aim)
            "down" -> PositionWithAim(this.forward, this.depth, this.aim + cmd.value)
            "up" -> PositionWithAim(this.forward, this.depth, this.aim - cmd.value)
            else -> this
        }
    }

    fun product() = this.forward.toLong() * this.depth.toLong()
}

data class Command(val direction: String, val value: Int)

fun calculatePosition(plannedCourse: String) =
    plannedCourse
        .lines()
        .map { it.split(" ") }
        .map { Command(it[0], it[1].toInt()) }
        .fold(Position()) { pos, cmd -> pos.next(cmd) }

fun calculatePositionByAim(plannedCourse: String) =
    plannedCourse
        .lines()
        .map { it.split(" ") }
        .map { Command(it[0], it[1].toInt()) }
        .fold(PositionWithAim()) { pos, cmd -> pos.next(cmd) }
