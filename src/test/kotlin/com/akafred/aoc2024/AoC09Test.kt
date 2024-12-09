package com.akafred.aoc2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.SortedMap

class AoC09Test {

    private val inputFile = "input09.txt"
    private val example1Answer = 1928L
    private val puzzle1Answer = 6432869891895L
    private val example2Answer = -1L
    private val puzzle2Answer = -1L

    private val exampleInput1 = """2333133121414131402""".trimIndent()

    private val exampleInput2 = exampleInput1

    private fun solve1(input: String): Long {
        val files: SortedMap<Int, FileSegment> = sortedMapOf<Int, FileSegment>()
        val free: SortedMap<Int, FreeSpace> = sortedMapOf<Int, FreeSpace>()
        var pos = 0
        input.forEachIndexed { index, ch ->
            val size = ch.digitToInt()
            if (size > 0) {
                if (index.even()) {
                    files.put(pos, FileSegment(index / 2, size))
                } else {
                    free.put(pos, FreeSpace(size))
                }
                pos += size
            }
        }
        //draw(files, free)
        while(free.keys.first() < files.keys.last()) {
            val (filePos, fileToFragment) = files.entries.last()
            var remainingFileSize = fileToFragment.size
            while(remainingFileSize > 0 && free.keys.first() < files.keys.last()) {
                val (freePos, freeSpace) = free.entries.first()
                if(freeSpace.size == remainingFileSize) {
                    val removedFile = files.remove(filePos)
                    files.put(freePos, removedFile)
                    val removedFree = free.remove(freePos)
                    free.put(filePos, removedFree)
                    remainingFileSize = 0
                } else if (freeSpace.size > remainingFileSize ){
                    val removedFile = files.remove(filePos)
                    files.put(freePos, removedFile)
                    free.remove(freePos)
                    free.put(freePos + remainingFileSize, FreeSpace(freeSpace.size - remainingFileSize))
                    free.put(filePos, FreeSpace(removedFile!!.size))
                    remainingFileSize = 0
                } else if (freeSpace.size < remainingFileSize) {
                    free.remove(freePos)
                    files.put(freePos, FileSegment(fileToFragment.fileId, freeSpace.size))
                    remainingFileSize = remainingFileSize - freeSpace.size
                    files.put(filePos, FileSegment(fileToFragment.fileId, remainingFileSize))
                    free.put(filePos + remainingFileSize, FreeSpace(freeSpace.size))
                }
                //draw(files, free)
            }
        }
        return files.entries.sumOf { (pos, fileSegment) ->
            (pos..(pos + fileSegment.size -1)).sumOf { pos ->
                //println(pos.toString() + " * " + fileSegment.fileId + " = " +pos * fileSegment.fileId )
                pos * fileSegment.fileId.toLong()
            }
        }
    }

    private fun draw(files: SortedMap<Int, FileSegment>, free: SortedMap<Int, FreeSpace>) {
        val disk = sortedMapOf<Int, DiskSegment>()
        disk.putAll(files)
        disk.putAll(free)
        disk.forEach { pos, segment ->
            when {
                segment is FreeSpace -> print("_".repeat(segment.size))
                segment is FileSegment -> print(segment.fileId.toString().last().toString().repeat(segment.size))
            }
        }
        println()
    }

    sealed interface DiskSegment
    class FileSegment(val fileId: Int, val size: Int): DiskSegment
    class FreeSpace(val size: Int): DiskSegment

    private fun Int.even(): Boolean = this % 2 == 0

    private fun solve2(input: String): Int {
        TODO("Not yet implemented")
    }

    @Test
    fun `example 1`() {
        val result = solve1(exampleInput1)
        assertEquals(example1Answer, result)
    }

    @Test
    fun `puzzle 1`() {
        val input = Util.readFile(inputFile)
        val result = solve1(input)
        assertEquals(puzzle1Answer, result)
    }

    @Test
    fun `example 2`() {
        val result = solve2(exampleInput2)
        assertEquals(example2Answer, result)
    }

    @Test
    fun `puzzle 2`() {
        val input = Util.readFile(inputFile)
        val result = solve2(input)
        assertEquals(puzzle2Answer, result)
    }
}
