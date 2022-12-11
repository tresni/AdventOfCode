package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day11Test : BaseDayTest(2022, 11) {
    @Test
    fun `load monkeys`() {
        Day11(input).troop.let {
            it.size shouldBe 4
            it.first().items shouldBe mutableListOf(79, 98)
            it.first().operation shouldBe listOf("old", "*", "19")
            it.first().testCondition shouldBe 23
            it.first().onTrue shouldBe 2
            it.first().onFalse shouldBe 3
        }
    }

    @Test
    fun `monkey plays keepaway`() {
        val day = Day11(input)
        day.round()
        day.troop.let {
            it.first().items shouldBe mutableListOf(20, 23, 27, 26)
            it[1].items shouldBe mutableListOf(2080, 25, 167, 207, 401, 1046)
        }
    }

    @Test
    fun `solve part 1`() {
        Day11(input).solve1() shouldBe 10605
    }

    @Test
    fun `part 2 after 1 round`() {
        val day = Day11(input, 1)
        day.apply { round(); reduce() }
        day.troop.map { it.inspectionCount() } shouldBe listOf(2, 4, 3, 6)

        repeat(19) { day.apply { round(); reduce() } }
        day.troop.map { it.inspectionCount() } shouldBe listOf(99, 97, 8, 103)

        repeat(980) { day.apply { round(); reduce() } }
        day.troop.map { it.inspectionCount() } shouldBe listOf(5204, 4792, 199, 5192)
    }

    @Test
    fun `solve part 2`() {
        val day = Day11(input, 1)
        day.troop.first().inspectionCount() shouldBe 52166
        day.troop.last().inspectionCount() shouldBe 52013
        day.solve2() shouldBe 2713310158
    }
}