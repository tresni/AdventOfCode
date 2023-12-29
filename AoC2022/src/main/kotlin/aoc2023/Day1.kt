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
        return findCalibrationValue(
            input.map { line ->
                line.mapIndexedNotNull { index, s ->
                    try {
                        when (s) {
                            in '0'..'9' -> s
                            'o' -> if (line.substring(index..index + 2) == "one") '1' else null
                            't' -> if (line.substring(index..index + 2) == "two") '2' else if (line.substring(index..index + 4) == "three") '3' else null
                            'f' -> when (line.substring(index..index + 3)) {
                                "four" -> '4'
                                "five" -> '5'
                                else -> null
                            }

                            's' -> if (line.substring(index..index + 2) == "six") '6' else if (line.substring(index..index + 4) == "seven") '7' else null
                            'e' -> if (line.substring(index..index + 4) == "eight") '8' else null
                            'n' -> if (line.substring(index..index + 3) == "nine") '9' else null
                            else -> null
                        }
                    }
                    catch (_: IndexOutOfBoundsException) {
                        null
                    }
                }.joinToString("")
            }
        )
    }
}

fun main() {
    println(Day1().solve1())
    println(Day1().solve2())
}