package aoc2023

import utils.*

class Day1 : BaseDay<Int, Int>() {
    override fun solve1(): Int =findCalibrationValue(
            InputReader.inputAsString(2023, 1).lines()
        )

    override fun solve2(): Int = findUpdatedCalibrationValue(
        InputReader.inputAsList(2023, 1)
    )

    fun findCalibrationValue(input: List<String>): Int {
        val first = Regex("(\\d).*")
        val last = Regex(".*(\\d)")
        return input.mapNotNull {
            "${first.find(it)?.groups?.get(1)?.value}${last.find(it)?.groups?.get(1)?.value}".toInt()
        }.sum()
    }

    fun findUpdatedCalibrationValue(input: List<String>): Int {
        val words = Regex("one|two|three|four|five|six|seven|eight|nine")
        return findCalibrationValue(input.map {
            words.replace(it) { result ->
                when (result.value) {
                    "one" -> "1"
                    "two" -> "2"
                    "three" -> "3"
                    "four" -> "4"
                    "five" -> "5"
                    "six" -> "6"
                    "seven" -> "7"
                    "eight" -> "8"
                    "nine" -> "9"
                    else -> "?"
                }
            }
        })
    }
}

fun main() {
    println(Day1().solve1())
    println(Day1().solve2())
}