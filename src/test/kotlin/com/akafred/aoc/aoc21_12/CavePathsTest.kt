package com.akafred.aoc.aoc21_12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CavePathsTest {
    @Test
    fun `path count`() {
        assertEquals(10, pathCount(input1))
        assertEquals(19, pathCount(input2))
        assertEquals(226, pathCount(input3))
    }

    @Test
    fun `path count with one small cave revisit`() {
        assertEquals(36, pathCount(input1, 1))
        assertEquals(103, pathCount(input2, 1))
        assertEquals(3509, pathCount(input3, 1))
    }
}

val input1 = """
    start-A
    start-b
    A-c
    A-b
    b-d
    A-end
    b-end
""".trimIndent()

val input2 = """
    dc-end
    HN-start
    start-kj
    dc-start
    dc-HN
    LN-dc
    HN-end
    kj-sa
    kj-HN
    kj-dc
""".trimIndent()

val input3 = """
    fs-end
    he-DX
    fs-he
    start-DX
    pj-DX
    end-zg
    zg-sl
    zg-pj
    pj-he
    RW-he
    fs-DX
    pj-RW
    zg-RW
    start-pj
    he-WI
    zg-he
    pj-fs
    start-RW
""".trimIndent()