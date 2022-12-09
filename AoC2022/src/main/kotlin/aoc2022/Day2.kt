package aoc2022

import utils.readInput

class Day2(val input: String) {
    fun score(): Int {
        return input.uppercase().split("\n")
            .map {
                val plays = it.split(" ", limit = 2)
                shapeTable[plays[1]]?.let { shape ->
                    return@map shape.points +
                        if (shape.weak.primary == plays[0]) {
                            LOSE
                        } else if (shape.opp.primary == plays[0]) {
                            TIE
                        } else {
                            WIN
                        }
                } ?: 0
            }
            .sum()
    }

    fun rig(): Int {
        return input.uppercase().split("\n")
            .map {
                val plays = it.split(" ", limit = 2)
                shapeTable[plays[0]]?.let { opp ->
                    return@map when (plays[1]) {
                        "X" -> LOSE + (shapeTable[opp.strong.secondary]?.points ?: 0)
                        "Y" -> TIE + (shapeTable[opp.opp.secondary]?.points ?: 0)
                        else -> WIN + (shapeTable[opp.weak.secondary]?.points ?: 0)
                    }
                } ?: 0
            }
            .sum()
    }

    companion object {
        enum class RPS(val primary: String, val secondary: String) {
            ROCK("A", "X"),
            PAPER("B", "Y"),
            SCISSORS("C", "Z"),
        }

        data class HandShape(val opp: RPS, val weak: RPS, val strong: RPS, val points: Int)

        val ROCK = HandShape(RPS.ROCK, RPS.PAPER, RPS.SCISSORS, 1)
        val PAPER = HandShape(RPS.PAPER, RPS.SCISSORS, RPS.ROCK, 2)
        val SCISSORS = HandShape(RPS.SCISSORS, RPS.ROCK, RPS.PAPER, 3)

        const val WIN = 6
        const val TIE = 3
        const val LOSE = 0

        val shapeTable = mapOf(
            RPS.ROCK.primary to ROCK,
            RPS.ROCK.secondary to ROCK,
            RPS.PAPER.primary to PAPER,
            RPS.PAPER.secondary to PAPER,
            RPS.SCISSORS.primary to SCISSORS,
            RPS.SCISSORS.secondary to SCISSORS
        )
    }
}

fun main() {
    val input = readInput(2)

    println(Day2(input).score())
    println(Day2(input).rig())
}
