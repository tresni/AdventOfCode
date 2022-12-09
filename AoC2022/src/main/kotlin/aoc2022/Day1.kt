package aoc2022

import utils.readInput

class Day1 {
    private fun getElvesCalories(supplies: String?) = supplies?.let { elves ->
        elves.split("\n\n").map { it.split("\n").map { v -> v.toInt() } }
    } ?: emptyList()

    fun mostCalories(supplies: String?): Int = getElvesCalories(supplies).maxOfOrNull { it.sum() } ?: 0

    fun topCalories(supplies: String?, top: Int = 3): Int = getElvesCalories(supplies)
        .map { it.sum() }.sortedDescending().subList(0, top).sum()
}

fun main() {
    val input = readInput(1)
    println(Day1().mostCalories(input))
    println(Day1().topCalories(input))
}
