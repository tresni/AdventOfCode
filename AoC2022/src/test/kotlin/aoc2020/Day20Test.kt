package aoc2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest
import utils.Direction

class Day20Test : BaseDayTest(2020, 20) {
    @Test
    fun `we load the right number of tiles`() {
        Day20(input).tiles.size shouldBe 9
    }

    @Test
    fun `we can find where tiles are supposed to connect`() {
        Day20(input).tiles.let {
            it[1951]!! borders it[2311]!! shouldBe Direction.East
            it[1951]!! borders it[2729]!! shouldBe Direction.North
        }
    }

    @Test
    fun `we can find the 4 corners`() {
        Day20(input).let {
            it.tiles.forEach { (n, tile) ->
                it.tiles.minus(
                    setOf(n) + tile.neighbors.mapNotNull { (_, n) -> n.id }
                ).forEach { (_, other) ->
                    tile borders other
                }
            }
            it.tiles[1951]?.neighbors?.map { (_, t) -> t.id }?.sorted() shouldBe listOf(2311, 2729)
            it.tiles[2971]?.neighbors?.map { (_, t) -> t.id }?.sorted() shouldBe listOf(1489, 2729)
            it.tiles[1171]?.neighbors?.map { (_, t) -> t.id }?.sorted() shouldBe listOf(1489, 2473)
            it.tiles[3079]?.neighbors?.map { (_, t) -> t.id }?.sorted() shouldBe listOf(2311, 2473)

            it.tiles.filter { (n, t) -> t.neighbors.size == 2 }.size shouldBe 4
        }
    }

    @Test
    fun `validate part1 example`() {
        Day20(input).solve1() shouldBe 20899048083289
    }
}