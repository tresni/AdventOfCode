package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.Point
import utils.asString

class Day12(input: String) : BaseDay<Int, Int>() {

    val topoMap = mutableMapOf<Point, Char>()
    lateinit var start: Point
    lateinit var end: Point

    init {
        input.lines().forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                topoMap[Point(charIndex, lineIndex)] = when (char) {
                    'S' -> {
                        start = Point(charIndex, lineIndex)
                        'a'
                    }

                    'E' -> {
                        end = Point(charIndex, lineIndex)
                        'z'
                    }

                    else -> char
                }
            }
        }
    }

    fun bfs(): Int? {
        val searched = mutableMapOf<Point, Point?>(start to null) // key = child, value = parent
        val queue = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val investigate = queue.removeFirst()
            if (investigate == end) {
                var n: Point? = end
                var i = 0
                while (n != start) {
                    n = searched[n]
                    i++
                }
                return i
            }

            investigate.neighboursNotDiagonal.forEach {
                if (it in searched) return@forEach
                if ((topoMap[it]?.code ?: 0) - (topoMap[investigate]?.code ?: 0) <= 1) {
                    searched[it] = investigate
                    queue.add(it)
                }
            }
        }
        return null
    }

    override fun solve1(): Int {
        println(topoMap.toMap().asString())
        return bfs() ?: 0
    }

    override fun solve2(): Int {
        TODO("Not yet implemented")
    }
}

fun main() {
    Day12(InputReader.inputAsString(2022, 12)).apply {
        println(solve1())
        println(solve2())
    }
}