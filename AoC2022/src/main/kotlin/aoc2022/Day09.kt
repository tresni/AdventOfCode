package aoc2022

import utils.BaseDay
import utils.Point
import utils.asString
import utils.readInput

class Day09(val input: String) : BaseDay<Int, Int>() {
    private val hasTouched = mutableMapOf(Point.ORIGIN to true)

    fun print() = hasTouched.asString()

    private fun followTheLeader(input: String, followers: Int = 1): Int {
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
    println(Day09(readInput(9)).solve1())
    println(Day09(readInput(9)).solve2())
}