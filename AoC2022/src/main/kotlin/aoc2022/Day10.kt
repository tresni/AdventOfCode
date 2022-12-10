package aoc2022

import utils.BaseDay
import utils.InputReader

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

    fun drawAtCycle(cycle: Int): String =
        cmds.subList(1, cycle + 1).chunked(20)
            .foldIndexed(Pair("", 1)) { index, acc, ints ->
                val offset = index * 20
                var register = acc.second
                Pair(
                    acc.first + ints
                        .mapIndexed { n, i ->
                            val sprite = (offset + n) % 40
                            (if (register in sprite - 1..sprite + 1) "#" else ".").also { register += i }
                        }
                        .joinToString("") + if (offset % 40 == 20) "\n" else "",
                    register
                )
            }.first

    override fun solve1(): Int = listOf(20, 60, 100, 140, 180, 220).fold(0) { acc, index -> valueAtCycle(index) + acc }

    override fun solve2(): String =
        drawAtCycle(240)
}

fun main() {
    Day10(InputReader.inputAsString(2022, 10)).apply {
        println(solve1())
        println(solve2())
    }
}