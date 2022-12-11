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

    internal fun round() {
        troop.forEach { monkey ->
            monkey.inspectItems().forEach { item ->
                val newWorry = item.floorDiv(worryReduction) % reducer
                troop[monkey.findTarget(newWorry)].catch(newWorry)
            }
        }
    }

    class Monkey(
        val items: MutableList<Long>,
        private val inspection: (Long) -> Long,
        val testCondition: Int,
        val onTrue: Int,
        val onFalse: Int,
        private var inspections: Int = 0
    ) {
        fun inspectItems(): Sequence<Long> = sequence {
            inspections += items.size
            while (items.isNotEmpty()) {
                yield(inspection(items.removeFirst()))
            }
        }

        fun findTarget(item: Long) = if (item % testCondition == 0L) onTrue else onFalse

        fun catch(item: Long) = items.add(item)

        fun inspectionCount() = inspections
    }

    private fun monkeySeeMonkeyDo(rounds: Int): Long {
        repeat(rounds) { round() }
        return troop
            .sortedByDescending(Monkey::inspectionCount)
            .let { (first, second) -> first.inspectionCount().toLong() * second.inspectionCount() }
    }

    override fun solve1() = monkeySeeMonkeyDo(20).toInt()

    override fun solve2() = monkeySeeMonkeyDo(10_000)
}

fun main() {
    val input = InputReader.inputAsString(2022, 11)
    println(Day11(input).solve1())
    println(Day11(input, 1).solve2())
}