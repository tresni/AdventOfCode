package aoc2023

import utils.*

class Day9 : BaseDay<Int, Int>() {
    override fun solve1() = InputReader.inputAsList(2023, 9).sumOf {
        predictNext(it.split(" ").map { v -> v.toInt() })
    }

    override fun solve2() = InputReader.inputAsList(2023, 9).sumOf {
        predictPrev(it.split(" ").map { v -> v.toInt() })
    }

    fun predict(values: List<Int>, initial: (List<Int>) -> Int, block: (Int, Int) -> Int): Int =
        block(
            initial(values),
            values
                .zipWithNext { a, b ->  b - a }
                .let {
                    if (it.all { v -> v == it.first() }) it.first()
                    else predict(it, initial, block)
                }
        )

    fun predictNext(values: List<Int>): Int = predict(values, List<Int>::last, Int::plus)

    fun predictPrev(values: List<Int>): Int = predict(values, List<Int>::first, Int::minus)
}

fun main() {
    println(Day9().solve1())
    println(Day9().solve2())
}