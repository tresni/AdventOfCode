package aoc2023

import utils.*

class Day1 : BaseDay<Int, Int>() {
    private val digits = (0..9).map { it.toString() }
    private val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    
    override fun solve1(): Int = findCalibrationValue(InputReader.inputAsList(2023, 1))
    override fun solve2(): Int = findUpdatedCalibrationValue(InputReader.inputAsList(2023, 1))

    fun findCalibrationValue(input: List<String>): Int = input.sumOf {
        it.findAnyOf(digits)!!.second.toInt() * 10 +
            it.findLastAnyOf(digits)!!.second.toInt()
    }

    fun findUpdatedCalibrationValue(input: List<String>): Int = input.sumOf {
        it.findAnyOf(digits + words)!!.second.fromDigit() * 10 +
            it.findLastAnyOf(digits + words)!!.second.fromDigit()
    }

    private fun String.fromDigit(): Int {
        val index = words.indexOf(this)
        return if (index == -1) {
            this.toInt()
        } else {
            index + 1
        }
    }
}

fun main() {
    println(Day1().solve1())
    println(Day1().solve2())
}