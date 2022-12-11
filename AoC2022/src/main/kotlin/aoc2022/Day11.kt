package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.lcm

class Day11(input: String, private val worryReduction: Int = 3) : BaseDay<Int, Long>() {

    val troop: MutableList<Monkey> = mutableListOf()
    private val reducer: Int

    init {
        input.lines().chunked(7).forEach {
            troop.add(
                Monkey(
                    troop,
                    it[1].substringAfter(": ").split(", ").map { i -> i.toLong() }.toMutableList(),
                    it[2].substringAfter("= ")
                        .split(" ")
                        .let { o ->
                            when (o[1]) {
                                "+" -> { item -> item + (o[2].toLongOrNull() ?: item) }
                                "*" -> { item -> item * (o[2].toLongOrNull() ?: item) }
                                else -> { _ -> 0L }
                            }
                        },
                    it[3].split(" ").last().toInt(),
                    it[4].split(" ").last().toInt(),
                    it[5].split(" ").last().toInt()
                )
            )
        }

        reducer = lcm(troop.map { it.testCondition })
    }

    fun round() {
        troop.forEach { monkey -> monkey.keepAway(worryReduction) }
        troop.forEach { monkey -> monkey.items.replaceAll { it % reducer } }
    }

    class Monkey(
        private val troop: MutableList<Monkey>,
        val items: MutableList<Long>,
        val inspection: (Long) -> Long,
        val testCondition: Int,
        val onTrue: Int,
        val onFalse: Int,
        private var inspections: Int = 0
    ) {
        fun keepAway(worryReduction: Int) {
            inspections += items.size
            items.forEach { item ->
                val newWorry = inspection(item).floorDiv(worryReduction)
                troop[if (newWorry % testCondition == 0L) onTrue else onFalse].items.add(newWorry)
            }
            items.clear()
        }

        fun inspectionCount() = inspections
    }

    fun monkeySeeMonkeyDo(rounds: Int) = repeat(rounds) { round() }.run {
        troop
            .sortedByDescending { it.inspectionCount() }
            .take(2)
            .fold(1L) { acc, monkey -> acc * monkey.inspectionCount() }
    }

    override fun solve1() = monkeySeeMonkeyDo(20).toInt()

    override fun solve2() = monkeySeeMonkeyDo(10_000)
}

fun main() {
    val input = InputReader.inputAsString(2022, 11)
    println(Day11(input).solve1())
    println(Day11(input, 1).solve2())
}