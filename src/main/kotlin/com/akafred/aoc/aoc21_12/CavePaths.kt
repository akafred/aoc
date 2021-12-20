package com.akafred.aoc.aoc21_12

typealias Stack<T> = MutableList<T>
fun <T> Stack<T>.push(item: T) = add(item)
fun <T> Stack<T>.pop(): T? = if (isNotEmpty()) removeAt(lastIndex) else null

fun pathCount(input: String, smallCaveRevisitsAllowed: Int = 0): Int {
    val segments = input.lines()
        .flatMap { line ->
            line.split("-")
                .let { listOf(Pair(it[0], it[1]), Pair(it[1], it[0])) } }
        .filter { (from, to) -> from != "end" && to != "start" }

    val conns = segments.groupBy { it.first }.entries.map { entry -> Pair(entry.key, entry.value.map { it.second }) }.toMap()

    val paths = mutableListOf<List<String>>()
    val stack = mutableListOf<List<String>>()

    stack.push(listOf("start"))

    while (stack.isNotEmpty()) {
        val toHere = stack.pop()!!
        conns[toHere.last()]!!.forEach { conn ->
            when (conn) {
                "end" -> paths.add(toHere + conn)
                !in toHere -> stack.add(toHere + conn)
                conn.uppercase() -> stack.add(toHere + conn)
                else -> { // conn lowercase && in toHere && !"end"
                    val smallCaveVisits = toHere.drop(1).filter { it != it.uppercase() }
                    val smallCaveDistinctVisits = smallCaveVisits.distinct()
                    if (smallCaveVisits.count() - smallCaveDistinctVisits.count() < smallCaveRevisitsAllowed) {
                        stack.add(toHere + conn)
                    } // else dead end
                }
            }
        }
    }
    return paths.size
}