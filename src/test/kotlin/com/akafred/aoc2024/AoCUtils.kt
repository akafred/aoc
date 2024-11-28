package com.akafred.aoc2024

import java.io.File

object Util {
    fun readFile(filename: String): String {
        val resource = javaClass.getResource(filename)
        return File(resource!!.file).readText()

    }
}
