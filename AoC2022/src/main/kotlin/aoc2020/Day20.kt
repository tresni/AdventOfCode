package aoc2020

import utils.BaseDay
import utils.Direction
import utils.InputReader

class Day20(input: String) : BaseDay<Long, Long>() {
    val tiles = mutableMapOf<Int, Tile>()

    class Tile(
        val id: Int,
        private val details: List<String>,
    ) {
        val neighbors = mutableMapOf<Int, Tile>()
        private val borderDetails by lazy {
            mapOf(
                details.first() to Direction.North,
                details.map { it.first() }.joinToString("") to Direction.West,
                details.last() to Direction.South,
                details.map { it.last() }.joinToString("") to Direction.East
            )
        }

        infix fun borders(other: Tile): Direction? {
            val bordersWithRotations = other.borderDetails.keys.let {
                it + it.map { s -> s.reversed() }
            }
            borderDetails.keys.intersect(bordersWithRotations).let {
                if (it.isNotEmpty()) {
                    if (it.size > 1) {
                        println("Well shit")
                    }
                    borderDetails[it.first()]?.let { dir ->
                        neighbors[dir.ordinal] = other
                        val otherDir =
                            other.borderDetails.getOrElse(it.first()) { other.borderDetails[it.first().reversed()] }
                        other.neighbors[otherDir!!.ordinal] = this
                        return dir
                    }
                }
                return null
            }
        }
    }

    init {
        input.lines().chunked(12).forEach {
            val id = it[0].substringAfter(" ").substringBefore(":").toInt()
            tiles[id] = Tile(
                it[0].substringAfter(" ").substringBefore(":").toInt(),
                it.drop(1).dropLast(1)
            )
        }
    }

    override fun solve1(): Long {
        tiles.forEach { (n, tile) ->
            tiles.minus(
                setOf(n) + tile.neighbors.mapNotNull { (_, n) -> n.id }
            ).forEach { (_, other) ->
                tile borders other
            }
        }
        return tiles.filter { t -> t.value.neighbors.size == 2 }.map { t -> t.value.id }.fold(1) { acc, n -> acc * n }
    }

    override fun solve2(): Long {
        TODO("Not yet implemented")
    }
}

fun main() {
    Day20(InputReader.inputAsString(2020, 20)).let {
        println(it.solve1())
        println(it.solve2())
    }
}