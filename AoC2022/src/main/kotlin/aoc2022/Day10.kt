package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.Point
import utils.asString

class Day10(input: String) : BaseDay<Int, String>() {

    private val cmds = listOf(1) + input.lines().flatMap {
        val parts = it.split(" ")
        when (parts[0]) {
            "noop" -> listOf(0)
            "addx" -> listOf(0, parts[1].toInt())
            else -> emptyList()
        }
    }

    fun valueAtCycle(cycle: Int) =
        cmds.subList(0, cycle).chunked(20)
            .foldIndexed(Pair(1, 0)) { index, acc, ints ->
                val offset = (index + 1) * 20
                val sum = ints.sum() + acc.second
                Pair(sum * offset, sum)
            }.first

    fun valueInCycle(start: Int, end: Int, initial: Int) =
        cmds.subList(start, end).chunked(20)
            .foldIndexed(Pair(1, initial)) { index, acc, ints ->
                val offset = (index + 1) * 20 + start
                val sum = acc.second + ints.sum()
                Pair(sum * offset, sum)
            }.first


    override fun solve1(): Int = listOf(20, 60, 100, 140, 180, 220).fold(0) { acc, index -> valueAtCycle(index) + acc }

    override fun solve2(): String =
        cmds.subList(1, cmds.size).foldIndexed(Pair(mutableMapOf<Point, Boolean>(), 1)) { index, acc, ints ->
            val sprite = index % 40
            acc.first[Point(sprite, index.floorDiv(40))] = acc.second in sprite - 1..sprite + 1
            Pair(acc.first, acc.second + ints)
        }.first.asString("")
}

fun main() {
    Day10(InputReader.inputAsString(2022, 10)).apply {
        println(solve1())
        println(solve2())
    }
}