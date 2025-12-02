package com.akafred.aoc2025

import java.io.File

object Util {
    fun readFile(filename: String): String {
        val resource = javaClass.getResource(filename)
        return File(resource!!.file).readText()
    }
}

fun <A> List<List<A>>.unzip(): Pair<List<A>, List<A>> {
    val listA = mutableListOf<A>()
    val listB = mutableListOf<A>()
    for ((a, b) in this) {
        listA.add(a)
        listB.add(b)
    }
    return Pair(listA, listB)
}