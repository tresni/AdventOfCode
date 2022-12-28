package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day17Test : BaseDayTest(2022, 17) {
    @Test
    fun solve1() {
        Day17(input).solve1() shouldBe 3068L
    }

    @Test
    fun solve2() {
        Day17(input).solve2() shouldBe 1514285714288L
    }
}
