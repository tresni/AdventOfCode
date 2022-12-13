package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day12Test : BaseDayTest(2022, 12) {
    @Test
    fun `solve for part 1`() {
        Day12(input).solve1() shouldBe 31
    }

    @Test
    fun `solve for part 2`() {
        Day12(input).solve2() shouldBe 29
    }
}