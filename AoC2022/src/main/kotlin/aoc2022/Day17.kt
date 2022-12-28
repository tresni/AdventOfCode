package aoc2022

import utils.*
import kotlin.system.measureTimeMillis

class Shape(input: String) {
    var shapeMap: Map<Point, Boolean>

    init {
        shapeMap = input.lines().reversed().flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c -> if (c == '#') Pair(Point(x, y), true) else null }
        }.toMap()
    }

    fun atOffset(yOffset: Int, xOffset: Int = 2) {
        shapeMap = shapeMap.mapKeys { (k, _) -> k.move(xOffset, yOffset) }
    }

    fun down() {
        shapeMap = shapeMap.mapKeys { (k, _) -> k.down() }
    }

    fun left() {
        shapeMap = shapeMap.mapKeys { (k, _) -> k.left() }
    }

    fun right() {
        shapeMap = shapeMap.mapKeys { (k, _) -> k.right() }
    }

    private fun horizontalEdge(reduceMe: (Int, Int) -> Boolean) = shapeMap
        .toList()
        .groupBy { (p, _) -> p.y }
        .mapValues { (_, lp) ->
            lp.reduce { a, b -> if (reduceMe(a.first.x, b.first.x)) a else b }
        }
        .values
        .toMap()

    fun leftEdge() = horizontalEdge { a, b -> a < b }

    fun rightEdge() = horizontalEdge { a, b -> a > b }
}

class Day17(input: String) : BaseDay<Long, Long>() {
    private val operations = input.asSequence().infinite().iterator()
    private val shapes = sequenceOf(
        "####", // -
        " # \n###\n # ", // +
        "  #\n  #\n###", // L
        "#\n#\n#\n#", // |
        "##\n##" // []
    ).infinite()

    private var chamber = mutableMapOf<Point, Boolean>()

    private fun fallingRocks(rocks: Long): Long {
        @Suppress("NAME_SHADOWING")
        var rocks = rocks
        var yOffset = 1L
        shapes.takeWhile { rocks-- > 0 }.map { Shape(it) }.forEach {
            it.atOffset((chamber.maxOfOrNull { (p, _) -> p.y } ?: -1) + 4)
            while (true) {
                when (operations.next()) {
                    '<' -> if (
                        !it
                            .leftEdge()
                            .any { (p, _) -> p.left().let { ip -> ip in chamber || ip.x < 0 } }
                    ) it.left()

                    '>' -> if (
                        !it
                            .rightEdge()
                            .any { (p, _) -> p.right().let { ip -> ip in chamber || ip.x >= CHAMBER_WIDTH } }
                    ) it.right()
                }

                if (
                    it.shapeMap
                        .toList()
                        .groupBy { (p, _) -> p.x }
                        .mapValues { (_, lp) -> lp.minBy { (p, _) -> p.y } }
                        .values
                        .toMap()
                        .any { (p, _) -> p.down().let { ip -> ip in chamber || ip.y < 0 } }
                ) {
                    chamber += it.shapeMap
                    chamber = chamber
                        .filterKeys { p -> p.y in it.shapeMap.map { (p, _) -> p.y } }
                        .keys
                        .groupBy { p -> p.y }
                        .filter { (_, lp) -> lp.size == CHAMBER_WIDTH }
                        .maxOfOrNull { (k, _) -> k }
                        ?.let { maxY ->
                            yOffset += maxY
                            chamber.filter { (k, _) -> k.y <= maxY }.forEach { (p, _) -> chamber.remove(p) }
                            chamber.mapKeys { (p, _) -> p.move(0, -maxY) }.toMutableMap()
                        } ?: chamber
                    break
                }
                it.down()
            }
        }
        return chamber.maxOf { (p, _) -> p.y } + yOffset
    }

    override fun solve1(): Long = fallingRocks(2022)

    override fun solve2(): Long = fallingRocks(1_000_000_000_000)

    companion object {
        const val CHAMBER_WIDTH = 7
    }
}

fun main() {
    Day17(InputReader.inputAsString(2022, 17)).apply {
        measureTimeMillis {
            println(solve1())
        }.also {
            println("Solved in $it ms")
        }
        println(solve2())
    }
}
