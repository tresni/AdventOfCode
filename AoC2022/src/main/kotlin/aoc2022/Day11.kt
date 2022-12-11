package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.lcm

class Day11(input: String, val worryReduction: Int = 3) : BaseDay<Int, Long>() {

    val troop: MutableList<Monkey> = mutableListOf()

    init {
        input.lines().chunked(7).forEach {
            troop.add(
                Monkey(
                    troop,
                    worryReduction,
                    it[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList(),
                    it[2].substringAfter("= ").split(" "),
                    it[3].split(" ").last().toInt(),
                    it[4].split(" ").last().toInt(),
                    it[5].split(" ").last().toInt()
                )
            )
        }
    }

    fun round() {
        troop.forEach { monkey -> monkey.keepAway() }
    }

    fun reduce() {
        val reducer =
            lcm(troop[0].testCondition, troop[1].testCondition, *troop.drop(2).map { it.testCondition }.toIntArray())
        troop.map {
            it.items.replaceAll { worry ->
                worry % reducer
            }
        }
    }

    class Monkey(
        val troop: MutableList<Monkey>,
        val worryReduction: Int,
        val items: MutableList<Long>,
        val operation: List<String>,
        val testCondition: Int,
        val onTrue: Int,
        val onFalse: Int,
        private var inspections: Int = 0
    ) {
        fun keepAway() {
            items.forEach { item ->
                val left = if (operation[0] == "old") item else operation[0].toLong()
                val right = if (operation[2] == "old") item else operation[2].toLong()
                val newWorry = when (operation[1]) {
                    "+" -> left + right
                    "*" -> left * right
                    else -> 0
                }.floorDiv(worryReduction)

                troop[if (newWorry % testCondition == 0L) onTrue else onFalse].items.add(newWorry)
                inspections += 1
            }
            items.clear()
        }

        fun inspectionCount() = inspections
    }

    override fun solve1(): Int {
        repeat(20) {
            round()
        }
        return troop
            .sortedByDescending { it.inspectionCount() }
            .take(2)
            .fold(1) { acc, monkey -> acc * monkey.inspectionCount() }
    }

    override fun solve2(): Long {
        repeat(10000) {
            round()
            reduce()
        }
        return troop
            .sortedByDescending { it.inspectionCount() }
            .take(2)
            .fold(1) { acc, monkey -> acc * monkey.inspectionCount() }
    }

}

fun main() {
    val input = InputReader.inputAsString(2022, 11)
    println(Day11(input).solve1())
    println(Day11(input, 1).solve2())
}