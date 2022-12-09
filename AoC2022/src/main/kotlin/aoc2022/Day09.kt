package aoc2022

import utils.BaseDay
import utils.Point
import utils.readInput

class Day09(val input: String) : BaseDay<Int, Int>() {
    override fun solve1(): Int {
        val hasTouched = mutableMapOf(Point(0, 0) to true)
        var headCoords = Point(0, 0)
        var tailCoords = Point(0, 0)

        input.lines().forEach {
            val (command, r) = it.split(" ")
            repeat(r.toInt()) {
                headCoords = when (command) {
                    "U" -> headCoords.up()
                    "D" -> headCoords.down()
                    "L" -> headCoords.left()
                    "R" -> headCoords.right()
                    else -> return@forEach
                }

                if (!tailCoords.neighboursWithItself.contains(headCoords)) {
                    tailCoords = tailCoords.walkTo(headCoords).first().also { p -> hasTouched[p] = true }
                }
            }
        }

        return hasTouched.size

    }

    override fun solve2(): Int {
        val hasTouched = mutableMapOf(Point(0, 0) to true)
        var headCoords = Point(0, 0)
        val pieces = mutableListOf(
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
            Point(0, 0),
        )

        input.lines().forEach {
            val (command, r) = it.split(" ")
            repeat(r.toInt()) {
                headCoords = when (command) {
                    "U" -> headCoords.up()
                    "D" -> headCoords.down()
                    "L" -> headCoords.left()
                    "R" -> headCoords.right()
                    else -> return@forEach
                }

                var follow = headCoords
                pieces.forEachIndexed { n, p ->
                    if (!p.neighboursWithItself.contains(follow)) {
                        pieces[n] = p.walkTo(follow).first()
                    }
                    follow = pieces[n]
                }

                hasTouched[pieces.last()] = true
            }
        }
        return hasTouched.size
    }
}

fun main() {
    println(Day09(readInput(9)).solve1())
    println(Day09(readInput(9)).solve2())
}