package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.Point
import utils.search.bfs

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

    override fun solve1(): Int {
        return bfs(start, { point -> point == end }) { start, end ->
            end in topoMap && (topoMap[end]?.code ?: 0) - (topoMap[start]?.code ?: 0) <= 1
        } ?: 0
    }

    override fun solve2(): Int {
        return bfs(end, { point -> topoMap[point] == 'a' }) { start, end ->
            end in topoMap && (topoMap[start]?.code ?: 0) - (topoMap[end]?.code ?: 0) <= 1
        } ?: 0
    }
}

fun main() {
    Day12(InputReader.inputAsString(2022, 12)).apply {
        println(solve1())
        println(solve2())
    }
}