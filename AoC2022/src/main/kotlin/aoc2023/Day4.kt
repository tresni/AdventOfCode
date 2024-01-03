package aoc2023

import utils.*
import kotlin.math.pow

class Day4 : BaseDay<Int, Int>() {
    override fun solve1(): Int = InputReader.inputAsList(2023, 4)
        .sumOf { matchesPerCard(it).let { p -> if (p == 0) 0 else 2.0.pow(p - 1).toInt() } }

    override fun solve2(): Int = totalCardsWon(InputReader.inputAsList(2023, 4))

    private fun String.numberSet() = trim().split(" +".toRegex()).map { it.toInt() }.toSet()

    fun matchesPerCard(card: String): Int = card.split(":", "|").let { (_, selected, winning) ->
        (selected.numberSet() intersect winning.numberSet()).size
    }

    fun nextCardsWon(card: String, copies: Int = 1) = List(matchesPerCard(card)) { copies }

    fun totalCardsWon(cards: List<String>): Int =
        cards.fold(0 to MutableList(cards.size) { 1 }) { (total, coming), card ->
            val copies = coming.removeFirst()
            total + copies to coming.zip(nextCardsWon(card, copies), Int::plus).let {
                it + coming.takeLast(coming.size - it.size)
            }.toMutableList()
        }.first
}

fun main() {
    println(Day4().solve1())
    println(Day4().solve2())
}