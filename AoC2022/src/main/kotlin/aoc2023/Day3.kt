package aoc2023

import utils.*

class Day3 : BaseDay<Int, Int>() {
    val numbers = mutableSetOf<Pair<Int, Set<Point>>>()
    val symbols = mutableMapOf<Point, Char>()

    init {
        InputReader.inputAsList(2023, 3).forEachIndexed { lineIndex, line ->
            var index = 0
            while (index < line.length) {
                val c = line[index]
                when (c) {
                    in '0'..'9' -> {
                        val numString = line.substring(index).takeWhile { it.isDigit() }
                        numbers.add(
                            Pair(
                                numString.toInt(),
                                (index until index + numString.length).map { Point(it, lineIndex) }.toSet()
                            )
                        )
                        index += numString.length - 1
                    }
                    '.' -> Unit
                    else -> symbols[Point(index, lineIndex)] = c
                }

                index++
            }
        }
    }
    override fun solve1() = listPartNumbers().sum()

    override fun solve2() = findGears().values.sumOf {
        it[0] * it[1]
    }

    fun listPartNumbers(): List<Int> {
        return numbers.mapNotNull { (num, points) ->
            if (points.any { (it.neighbours intersect symbols.keys).isNotEmpty() }) num else null
        }
    }

    fun findGears(): Map<Point, List<Int>> {
        val gears = symbols.filterValues { it == '*' }
        return numbers.flatMap { (num, points) ->
            points.flatMap {
                (it.neighbours intersect gears.keys).map { p -> p to num }
            }.toSet()
        }.groupBy { it.first }.mapValues { it.value.map { v -> v.second} }.filterValues { it.size == 2 }
    }
}

fun main() {
    println(Day3().solve1())
    println(Day3().solve2())
}