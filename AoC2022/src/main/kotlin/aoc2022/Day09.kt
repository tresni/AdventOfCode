package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.Point

class Day09(val input: String) : BaseDay<Int, Int>() {
    private fun followTheLeader(input: String, followers: Int = 1): Int {
        val hasTouched = mutableMapOf(Point.ORIGIN to true)
        var headCoords = Point.ORIGIN
        val followerCoords = MutableList(followers) { Point.ORIGIN }

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
                followerCoords.forEachIndexed { n, p ->
                    if (follow !in p.neighboursWithItself) {
                        followerCoords[n] = p.walkTo(follow).first()
                    }
                    follow = followerCoords[n]
                }

                hasTouched[followerCoords.last()] = true
            }
        }
        return hasTouched.size
    }

    override fun solve1(): Int = followTheLeader(input)

    override fun solve2(): Int = followTheLeader(input, 9)
}

fun main() {
    Day09(InputReader.inputAsString(2022, 9)).also {
        println(it.solve1())
        println(it.solve2())
    }
}