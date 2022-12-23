package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day15Test : BaseDayTest(2022, 15) {
    @Test
    fun solve1() {
        Day15(input).solve1() shouldBe 26
    }

    @Test
    fun solve2() {
        Day15(input).solve2(20) shouldBe 56000011
    }
}