package com.akafred.aoc.aoc21_18


fun magnitudeOfSum(s: String) = magnitude(addAndReduce(s))

fun magnitude(s: String): Int {
    var rest = s
    while (!rest.matches("\\d+".toRegex())) {
        val (before, first, second, after) = """^(.*)\[(\d+),(\d+)](.*)$""".toRegex().find(rest)!!.destructured
        rest = before + (3 * first.toInt() + 2 * second.toInt()) + after
    }
    return rest.toInt()
}

fun split(s: String): String {
    val splitable = """\d{2,}""".toRegex().find(s)
    return if (splitable == null) {
        s
    } else {
        val value = s.slice(splitable.groups[0]!!.range)
        val left = s.slice(0 until splitable.range.start)
        val right = s.substring(splitable.range.endInclusive + 1)
        val newValue = "[" + (value.toInt() / 2) + "," + ((value.toInt() + 1) / 2) + "]"
        left + newValue + right
    }
}

fun explode(s: String): String {
    val match = regularNumberOnLevel4(s)
    if (match == null) {
        return s
    }
    val (leftValue, rightValue) = extractLeftRightValue(match.value)

    val left = s.slice(0 until match.range.start)
    val firstValueLeft = """^(.*[^\d])(\d+)([^\d+]+)$""".toRegex()
    val newLeft = addToLiteral(left, firstValueLeft, leftValue.toInt())

    val right = s.substring(match.range.endInclusive + 1)
    val firstValueRight = """^([^\d]+)(\d+)([^\d].*)${'$'}""".toRegex()
    val newRight = addToLiteral(right, firstValueRight, rightValue.toInt())

    return newLeft + "0" + newRight
}

private fun extractLeftRightValue(s: String) = """^\[(\d+),(\d+)]$""".toRegex().find(s)!!.destructured

private fun addToLiteral(s: String, regexToFindLiteral: Regex, valueToAdd: Int): String {
    val match = regexToFindLiteral.find(s)
    return if (match != null && s != "0") {
        val (before, value, after) = match.destructured
        before + (value.toInt() + valueToAdd).toString() + after
    } else {
        s
    }
}

private fun regularNumberOnLevel4(s: String): MatchResult? {
    var bracketsLevel = 0
    var i = 0
    var found = false
    var regularNumber: MatchResult? = null
    do {
        if (bracketsLevel == 4) {
            regularNumber = """\[\d+,\d+]""".toRegex().find(s, i)
            if (regularNumber != null && regularNumber.range.start == i) {
                found = true
            }
        }
        if (!found) {
            when (s[i]) {
                '[' -> bracketsLevel++
                ']' -> bracketsLevel--
            }
            i++
        }
    } while (!found && i < s.length - "[d,d]".length)

    return if (found) regularNumber else null
}

tailrec fun reduce(s: String): String {
    val result = split(explodeUntilDone(s))
    return if (result == s) s else reduce(result)
}

tailrec fun explodeUntilDone(s: String): String {
    val result = explode(s)
    return if (result == s) s else explodeUntilDone(result)
}

fun addAndReduce(input: String) = input.lines().reduce { s1, s2 -> reduce(internalAdd(s1, s2)) }

fun internalAdd(s1: String, s2: String) = "[$s1,$s2]"
