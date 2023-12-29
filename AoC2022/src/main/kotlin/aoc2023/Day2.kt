package aoc2023

import utils.*

class Day2 : BaseDay<Int, Int>() {

    private val colors = mapOf("red" to 12, "green" to 13, "blue" to 14)
    override fun solve1() = possibleGameSum(InputReader.inputAsList(2023, 2))

    override fun solve2() = diePower(InputReader.inputAsList(2023, 2)).sum()

    fun possibleGameSum(input: List<String>): Int  = input.sumOf {
            val game = Regex("Game (\\d+):").find(it)?.groups?.get(1)?.value?.toInt() ?: return@sumOf 0
            if (colors.all { (c, limit) ->
                Regex("(\\d+) $c").findAll(it).all { m ->
                    m.groups[1]!!.value.toInt() <= limit
                }
            }) game else 0
        }

    fun diePower(input: List<String>): List<Int> = input.map {
            colors.map { (c, _) ->
                Regex("(\\d+) $c").findAll(it).maxOf { m ->
                    m.groups[1]!!.value.toInt()
                }
            }.fold(1) { a, b -> a * b }
        }
}

fun main() {
    println(Day2().solve1())
    println(Day2().solve2())
}