package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.Point
import utils.asString

val sandFall = mutableMapOf<Point, String>()

class Day14(input: String) : BaseDay<Int, Int>() {
    private val lineInTheSand: Int

    init {
        input.lines().forEach {
            it.split(" -> ")
                .map { it.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
                .reduce { acc, point ->
                    acc.pointsTo(point).forEach { p ->
                        sandFall[p] = "#"
                    }
                    point
                }
        }

        lineInTheSand = sandFall.maxOf { it.key.y }
    }

    class Sand(var loc: Point) {
        fun moving() = sandFall[loc.up()] == null ||
            sandFall[loc.up().left()] == null ||
            sandFall[loc.up().right()] == null

        fun move() {
            if (sandFall[loc.up()] == null) loc = loc.up()
            else if (sandFall[loc.up().left()] == null) loc = loc.up().left()
            else loc = loc.up().right()
        }
    }

    override fun solve1(): Int {
        while (true) {
            val sand = Sand(Point(500, 0))
            while (sand.moving()) {
                sand.move()
                if (sand.loc.y >= lineInTheSand) {
                    return sandFall.count { (_, v) -> v == "o" }
                }
            }
            sandFall[sand.loc] = "o"
            println(sandFall.asString())
        }
    }

    override fun solve2(): Int {
        while (true) {
            val sand = Sand(Point(500, 0))
            while (sand.moving()) {
                sand.move()
                if (sand.loc.y == lineInTheSand + 1) {
                    break
                }
            }
            sandFall[sand.loc] = "o"
            if (sand.loc == Point(500, 0)) return sandFall.count { (_, v) -> v == "o" }
        }
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 14)
    Day14(input).run {
        println(solve1())
        println(solve2())
    }
}