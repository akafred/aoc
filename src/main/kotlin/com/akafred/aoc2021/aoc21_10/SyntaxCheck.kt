package com.akafred.aoc2021.aoc21_10

import java.lang.RuntimeException

class ExpectationException(val expectedChar: Char, val foundChar: Char): Exception("Expected '$expectedChar' but found '$foundChar'} instead.") {}
class UnexpectedEndingException(val missing: Stack<Char>): Exception("Unexpected end, missing $missing") {}

fun syntaxScore(input: String): Int =
    input.lines()
        .fold(listOf<Char>()) { acc, line ->
            try {
                parseLine(line)
                throw RuntimeException("Expected exception")
            } catch (e: ExpectationException) {
                acc + e.foundChar
            } catch (e: Exception) {
                acc
            } }
        .map { foundChar ->
            when (foundChar) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw IllegalArgumentException("Unexpected char found $foundChar")
            } }
        .sum()

val score = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

fun syntaxScore2(input: String): Long =
    input.lines()
        .fold(mutableListOf<List<Char>>()) { acc, line ->
            try {
                parseLine(line)
                throw RuntimeException("Expected exception")
            } catch (e: ExpectationException) {
                acc
            } catch (e: UnexpectedEndingException) {
                acc.add(e.missing.reversed().toMutableList());acc
            }
        }
        .let { list ->
            list.map { it.fold(0L) { acc, next -> acc * 5 + score[next]!! } }.sorted()[list.size / 2]
        }


typealias Stack<T> = MutableList<T>
fun <T> Stack<T>.push(item: T) = add(item)
fun <T> Stack<T>.pop(): T? = if (isNotEmpty()) removeAt(lastIndex) else null

val match = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

fun parseLine(line: String): Int {
    val stack = mutableListOf<Char>()
    line.toCharArray().forEach { char ->
        when (char) {
            '(', '[', '{', '<' -> stack.push(char)
            else -> {
                if (stack.isEmpty()) throw IllegalStateException("Unexpected end of line")
                val top = stack.pop()!!
                if (char != match[top]) throw ExpectationException(match[top]!!, char)
            }
        }
    }
    if (stack.isEmpty()) {
        throw RuntimeException("Unexpected MEANINGFUL line.")
    }
    throw UnexpectedEndingException(stack)
}
