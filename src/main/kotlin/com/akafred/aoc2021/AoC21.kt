package com.akafred.aoc2021

import com.akafred.aoc2021.aoc21_01.*
import com.akafred.aoc2021.aoc21_02.calculatePosition
import com.akafred.aoc2021.aoc21_02.calculatePositionByAim
import com.akafred.aoc2021.aoc21_03.powerConsumption
import com.akafred.aoc2021.aoc21_03.calculateLifeSupportRating
import com.akafred.aoc2021.aoc21_04.bingoScore
import com.akafred.aoc2021.aoc21_04.lastBingoScore
import com.akafred.aoc2021.aoc21_05.overlappingPoints
import com.akafred.aoc2021.aoc21_06.lanternfishCount
import com.akafred.aoc2021.aoc21_07.minFuel
import com.akafred.aoc2021.aoc21_07.realMinFuel
import com.akafred.aoc2021.aoc21_08.simpleDigitCount
import com.akafred.aoc2021.aoc21_08.sumOfOutputs
import com.akafred.aoc2021.aoc21_09.basinsProduct
import com.akafred.aoc2021.aoc21_09.lowPointRiskLevel
import com.akafred.aoc2021.aoc21_10.syntaxScore
import com.akafred.aoc2021.aoc21_10.syntaxScore2
import com.akafred.aoc2021.aoc21_11.flashCount
import com.akafred.aoc2021.aoc21_11.syncFlashSteps
import com.akafred.aoc2021.aoc21_12.pathCount
import com.akafred.aoc2021.aoc21_16.calculate
import com.akafred.aoc2021.aoc21_16.decodeAndSumVersions
import com.akafred.aoc2021.aoc21_17.launchOptionsCount
import com.akafred.aoc2021.aoc21_17.trajectoryPeak
import com.akafred.aoc2021.aoc21_18.magnitudeOfSum
import com.akafred.aoc2021.aoc21_18.maxMagnitude
import com.akafred.aoc2021.aoc21_22.cubeInitOnCount
import java.io.File

class AoC21 {
    val greeting: String
        get() {
            return "Hello Advent of Code!"
        }
}

fun main() {
    println(AoC21().greeting)

    println("AoC 1-1 depth increases: ${increases(aoc1Input)}")

    println("AoC 1-2 sliding average depth increases: ${slidingAverageIncreases(aoc1Input)}")

    println("AoC 2-1 position product: ${calculatePosition(aoc2Input).product()}")

    println("AoC 2-2 position by aim product: ${calculatePositionByAim(aoc2Input).product()}")

    println("AoC 3-1 power consumption: ${powerConsumption(aoc3Input)}")

    println("AoC 3-2 life support rating: ${calculateLifeSupportRating(aoc3Input)}")

    println("AoC 4-1 bingo score: ${bingoScore(aoc4Input)}")

    println("AoC 4-2 last bingo score: ${lastBingoScore(aoc4Input)}")

    println("AoC 5-1 overlapping points: ${overlappingPoints(aoc5Input, false)}")

    println("AoC 5-2 overlapping points with diagonal: ${overlappingPoints(aoc5Input)}")

    println("AoC 6-1 lanternfish growth (80 days): ${lanternfishCount(aoc6Input, 80)}")

    println("AoC 6-2 lanternfish growth (256 days): ${lanternfishCount(aoc6Input, 256)}")

    println("AoC 7-1 minimal fuel: ${minFuel(aoc7Input)}")

    println("AoC 7-2 real minimal fuel: ${realMinFuel(aoc7Input)}")

    println("AoC 8-1 simple digits count: ${simpleDigitCount(aoc8Input)}")

    println("AoC 8-2 sum of outputs: ${sumOfOutputs(aoc8Input)}")

    println("AoC 9-1 low points risk level: ${lowPointRiskLevel(aoc9Input)}")

    println("AoC 9-2 basins product: ${basinsProduct(aoc9Input)}")

    println("AoC 10-1 syntax score : ${syntaxScore(aoc10Input)}")

    println("AoC 10-2 syntax score 2 : ${syntaxScore2(aoc10Input)}")

    println("AoC 11-1 flash count : ${flashCount(aoc11Input, 100)}")

    println("AoC 11-1 sync flash steps : ${syncFlashSteps(aoc11Input)}")

    println("AoC 12-1 cave paths count : ${pathCount(aoc12Input)}")

    println("AoC 12-1 cave paths count with 1 revisit : ${pathCount(aoc12Input, 1)}")

    println("AoC 16-1 sum of packet versions: ${decodeAndSumVersions(aoc16Input)}")

    println("AoC 16-2 packet calculation: ${calculate(aoc16Input)}")

    println("AoC 17-1 max trajectory height: ${trajectoryPeak(aoc17Input)}")

    println("AoC 17-2 valid trajectory count: ${launchOptionsCount(aoc17Input)}")

    println("AoC 18-1 snailfish number magnitude: ${magnitudeOfSum(aoc18Input)}")

    println("AoC 18-2 max magnitude: ${maxMagnitude(aoc18Input)}")

    println("AoC 22-1 cube int on-count: ${cubeInitOnCount(aoc22Input)}")

    println("AoC 22-2 cube int unlimited on-count: ${cubeInitOnCount(aoc22Input, limited = false)}")

}


val aoc1Input = readFile("com/akafred/aoc2021/aoc1.dat").lines().map(String::toInt)
val aoc2Input = readFile("com/akafred/aoc2021/aoc2.dat")
val aoc3Input = readFile("com/akafred/aoc2021/aoc3.dat")
val aoc4Input = readFile("com/akafred/aoc2021/aoc4.dat")
val aoc5Input = readFile("com/akafred/aoc2021/aoc5.dat")
val aoc6Input = readFile("com/akafred/aoc2021/aoc6.dat")
val aoc7Input = readFile("com/akafred/aoc2021/aoc7.dat")
val aoc8Input = readFile("com/akafred/aoc2021/aoc8.dat")
val aoc9Input = readFile("com/akafred/aoc2021/aoc9.dat")
val aoc10Input = readFile("com/akafred/aoc2021/aoc10.dat")
val aoc11Input = readFile("com/akafred/aoc2021/aoc11.dat")
val aoc12Input = readFile("com/akafred/aoc2021/aoc12.dat")
val aoc16Input = readFile("com/akafred/aoc2021/aoc16.dat")
val aoc17Input = readFile("com/akafred/aoc2021/aoc17.dat")
val aoc18Input = readFile("com/akafred/aoc2021/aoc18.dat")
val aoc22Input = readFile("com/akafred/aoc2021/aoc22.dat")

private fun readFile(filename: String) = File(ClassLoader.getSystemResource(filename).file).readText()