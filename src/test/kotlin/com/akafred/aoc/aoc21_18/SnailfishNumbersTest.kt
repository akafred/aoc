package com.akafred.aoc.aoc21_18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnailfishNumbersTest {

    @Test
    fun `snail reduce`() {
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
            reduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"))
    }

    @Test
    fun `snail explode`() {
        assertEquals("[[[[0,9],2],3],4]", explode("[[[[0,9],2],3],4]")) // noop

        assertEquals("[[[[0,9],2],3],4]", explode("[[[[[9,8],1],2],3],4]"))
        assertEquals("[[[[0,9],2],3],4]", explode("[[[[0,9],2],3],4]"))

        assertEquals("[7,[6,[5,[7,0]]]]", explode("[7,[6,[5,[4,[3,2]]]]]"))
        assertEquals("[7,[6,[5,[7,0]]]]", explode("[7,[6,[5,[7,0]]]]"))

        assertEquals("[[6,[5,[7,0]]],3]", explode("[[6,[5,[4,[3,2]]]],1]"))
        assertEquals("[[6,[5,[7,0]]],3]", explode("[[6,[5,[7,0]]],3]"))

        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"))
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"))
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", explode("[[3,[2,[8,0]]],[9,[5,[7,0]]]]"))
    }

    @Test
    fun `snail story`() {
        val sn1 = "[[[[4,3],4],4],[7,[[8,4],9]]]"
        val sn2 = "[1,1]"
        val sn3 = internalAdd(sn1, sn2)
        val expected3 = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"
        assertEquals(expected3, sn3)

        val sn4 = explode(sn3)
        val expected4 = "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"
        assertEquals(expected4, sn4)

        val sn5 = explode(sn4)
        val expected5 = "[[[[0,7],4],[15,[0,13]]],[1,1]]"
        assertEquals(expected5, sn5)

        val sn6 = split(sn5)
        val expected6 = "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"
        assertEquals(expected6, sn6)

        val sn7 = split(sn6)
        val expected7 = "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]"
        assertEquals(expected7, sn7)

        val sn8 = explode(sn7)
        val expected8 = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        assertEquals(expected8, sn8)
    }

    @Test
    fun `snailfish addition`() {
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", addAndReduce("""
            [1,1]
            [2,2]
            [3,3]
            [4,4]
        """.trimIndent()))

        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", addAndReduce("""
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
        """.trimIndent()))

        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", addAndReduce("""
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
            [6,6]
        """.trimIndent()))

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", addAndReduce("[[[[4,3],4],4],[7,[[8,4],9]]]\n[1,1]"))

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", addAndReduce("""
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent()))
    }

    @Test
    fun `magnitude calc`() {
        assertEquals(29, magnitude("[9,1]"))
        assertEquals(1384, magnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"))
        assertEquals(445, magnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]"))
        assertEquals(791, magnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]"))
        assertEquals(1137, magnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]"))
        assertEquals(3488, magnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))
    }

    @Test
    fun `homework`() {
        val input = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent()

        val sum = addAndReduce(input)

        assertEquals("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]", sum)

        assertEquals(4140, magnitude(sum))
    }

    @Test
    fun `max magnitude`() {
        val input = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent()

        assertEquals(3993, maxMagnitude(input))
    }
}

