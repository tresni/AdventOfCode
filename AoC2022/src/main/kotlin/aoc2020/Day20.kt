package aoc2020

import utils.*

class Day20(input: String) : BaseDay<Long, Long>() {
    val tiles: List<Tile>

    init {
        tiles = input.lines().chunked(12).map {
            Tile(
                it[0].substringAfter(" ").substringBefore(":").toInt(),
                it.drop(1).dropLast(1).toMutableList()
            )
        }
    }

    class Tile(
        val id: Int,
        private var details: MutableList<String>,
    ) {
        val neighbors = mutableMapOf<Direction, Tile>()
        var aligned = false

        fun borders(): Set<String> = setOf(
            details.first(),
            details.map { it.first() }.joinToString(""),
            details.last(),
            details.map { it.last() }.joinToString(""),
        )

        private fun directionByBorder(border: String): Direction? = when (border) {
            details.first() -> Direction.North
            details.map { it.first() }.joinToString("") -> Direction.West
            details.last() -> Direction.South
            details.map { it.last() }.joinToString("") -> Direction.East
            else -> null
        }

        private fun borderByDirection(dir: Direction): String = when (dir) {
            Direction.North -> details.first()
            Direction.West -> details.map { it.first() }.joinToString("")
            Direction.South -> details.last()
            Direction.East -> details.map { it.last() }.joinToString("")
        }

        private fun flipVertical() {
            val temp = neighbors[Direction.South]
            neighbors[Direction.North]?.let {
                neighbors[Direction.South] = it
            } ?: neighbors.remove(Direction.South)
            if (temp != null) neighbors[Direction.North] = temp else neighbors.remove(Direction.North)

            details.reverse()
        }

        private fun flipHorizontal() {
            val temp = neighbors[Direction.East]
            neighbors[Direction.West]?.let {
                neighbors[Direction.East] = it
            } ?: neighbors.remove(Direction.East)
            if (temp != null) neighbors[Direction.West] = temp else neighbors.remove(Direction.West)

            details.replaceAll { it.reversed() }
        }

        private fun rotate90() {
            neighbors.mapKeys { it.key.right }.toMutableMap().let {
                neighbors.clear()
                neighbors.putAll(it)
            }

            details = sequence {
                for (i in 0 until details.first().length) {
                    yield(details.map { it[i] }.joinToString("").reversed())
                }
            }.toMutableList()
        }

        infix fun checkBorders(other: Tile): Direction? {
            val bordersWithRotations = Direction.values()
                .flatMap { setOf(other.borderByDirection(it), other.borderByDirection(it).reversed()) }
                .toSet()

            borders().intersect(bordersWithRotations).let { borderSet ->
                if (borderSet.isNotEmpty()) {
                    val border = borderSet.first()
                    directionByBorder(border)?.let { dir ->
                        neighbors[dir] = other
                        val otherDir =
                            other.directionByBorder(border) ?: other.directionByBorder(border.reversed())!!
                        other.neighbors[otherDir] = this
                        return dir
                    }
                }
                return null
            }
        }

        fun alignNeighbors() {
            this.aligned = true
            neighbors.forEach { (dir, other) ->
                if (other.aligned) return@forEach
                this checkBorders other
                val border = borderByDirection(dir)
                val otherDir = other.directionByBorder(border) ?: other.directionByBorder(border.reversed())!!
                when (dir.rotationsTo(otherDir)) {
                    0 -> {
                        if (dir in listOf(Direction.North, Direction.South)) other.flipVertical()
                        else other.flipHorizontal()
                        if (other.borderByDirection(dir.opposite) != border) {
                            if (dir in listOf(Direction.North, Direction.South)) other.flipHorizontal()
                            else other.flipVertical()
                        }
                    }

                    1 -> {
                        other.rotate90()
                        if (other.borderByDirection(dir.opposite) != border) {
                            if (dir in listOf(Direction.North, Direction.South)) other.flipHorizontal()
                            else other.flipVertical()
                        }
                    }

                    2 -> {
                        if (other.borderByDirection(dir.opposite) != border) {
                            if (dir in listOf(Direction.North, Direction.South)) other.flipHorizontal()
                            else other.flipVertical()
                        }

                    }

                    3 -> {
                        repeat(3) { other.rotate90() }
                        if (other.borderByDirection(dir.opposite) != border) {
                            if (dir in listOf(Direction.North, Direction.South)) other.flipVertical()
                            else other.flipHorizontal()
                        }
                    }

                    else -> Unit
                }

                println(
                    "$id :: ${other.id} r${dir.rotationsTo(otherDir)} $border $dir <> $otherDir -> " +
                        "${other.borderByDirection(dir.opposite)} ${other.directionByBorder(border)}"
                )
            }
            neighbors.filterNot { (_, t) -> t.aligned }.forEach { (_, t) -> t.alignNeighbors() }
        }
    }

    override fun solve1(): Long {
        tiles.forEach { tile ->
            tiles.minus(
                setOf(tile) + tile.neighbors.mapNotNull { (_, n) -> n }
            ).forEach { other ->
                tile checkBorders other
            }
        }
        return tiles.filter { t -> t.neighbors.size == 2 }.map { t -> t.id }.fold(1) { acc, n -> acc * n }
    }

    override fun solve2(): Long {
        solve1()
        tiles.first { t -> t.neighbors.size == 2 }.run {
            aligned = true
            alignNeighbors()
        }
        return 0
    }
}

fun main() {
    Day20(InputReader.inputAsString(2020, 20)).let {
        println(it.solve1())
        println(it.solve2())
    }
}