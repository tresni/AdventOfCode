package aoc2022

import utils.*

class Day15(input: String) : BaseDay<Int, Int>() {

    val signalMap = mutableMapOf<Point, Char>()

    init {
        val coords = Regex("x=([-0-9]+), y=([-0-9]+)")
        input.lines().forEachIndexed { index, it ->
            val (signal, beacon) = coords.findAll(it)
                .let { m ->
                    Pair(
                        Point(m.first().groupValues[1].toInt(), m.first().groupValues[2].toInt()),
                        Point(m.last().groupValues[1].toInt(), m.last().groupValues[2].toInt())
                    )
                }
            signalMap[signal] = 'S'
            signalMap[beacon] = 'B'
            val md = signal manhattanDistanceTo beacon
            signalMap.manhattanFill(signal, md, '#')
        }
    }

    override fun solve1(): Int {
        print(signalMap.asString().easyReading())
        return signalMap.filterKeys { it.y == 10 }.count { it.value == '#' }
    }

    override fun solve2(): Int {
        TODO("Not yet implemented")
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 15)
    Day15(input).apply {
        println(solve1())
        println(solve2())
    }
}