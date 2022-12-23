package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.Point
import kotlin.math.abs

class Day15(input: String) : BaseDay<Int, Long>() {

    private val signalMap = mutableMapOf<Point, Char>()
    private val setup: List<Triple<Point, Point, Int>>

    init {
        val coords = Regex("x=([-0-9]+), y=([-0-9]+)")
        setup = input.lines().map {
            coords.findAll(it).let { m ->
                val signal = Point(m.first().groupValues[1].toInt(), m.first().groupValues[2].toInt())
                val beacon = Point(m.last().groupValues[1].toInt(), m.last().groupValues[2].toInt())
                val md = signal manhattanDistanceTo beacon
                Triple(
                    signal,
                    beacon,
                    md
                )
            }
        }
    }

    /*signalMap[signal] = 'S'
        signalMap[beacon] = 'B'
        val md = signal manhattanDistanceTo beacon
        for (y in -md..md) {
            val offset = if (y.sign == 1) md - y else md + y
            signalLines.getOrPut(y) { mutableListOf() }.let { lines ->
                val (positiveOffset, negativeOffset) = Pair(signal.x + offset, signal.x - offset)
                val toRemove = lines.filter { p ->
                    (p.second in negativeOffset..positiveOffset) ||
                        (p.first in negativeOffset..positiveOffset)
                }.also { pairs ->
                    // we know there is overlap, so we want the top and bottom of each
                    val min = (listOf(negativeOffset) + pairs.map { p -> p.first }).min()
                    val max = (listOf(positiveOffset) + pairs.map { p -> p.second }).max()
                    lines.add(Pair(min, max))
                }
                lines.removeAll(toRemove)
            }
        }
    }

     */

    fun searcher(row: Int) = setup
        .mapNotNull { (signal, _, md) ->
            if (row in signal.y - md..signal.y + md) {
                val offset = abs(row - signal.y)
                Pair(signal.x - (md - offset), signal.x + (md - offset))
            } else null
        }
        .sortedWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })
        .let {
            val result = mutableListOf<Pair<Int, Int>>()
            val list = it.toMutableList()
            while (list.isNotEmpty()) {
                val (min, max) = list.removeFirst()
                val removed = list.takeWhile { c -> c.first <= max }
                if (removed.isEmpty()) {
                    result.add(Pair(min, max))
                } else {
                    list.removeAll(removed)
                    list.add(0, Pair(min, removed.maxOf { r -> r.second }))
                }
            }
            result
        }

    override fun solve1() = solve1(10)

    fun solve1(row: Int): Int {
        return searcher(row).sumOf { (min, max) -> max - min } - signalMap.count { it.key.y == row }
    }

    override fun solve2() = solve2(20)
    fun solve2(maximum: Int): Long {
        for (i in 0..maximum) {
            val result = searcher(i)
            if (result.size > 1) {
                return (result.first().second + 1) * 4_000_000L + i
            }
        }
        return 0
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 15)
    Day15(input).apply {
        println(solve1(2_000_000))
        println(solve2(4_000_000))
    }
}