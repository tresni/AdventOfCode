package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest
import utils.InputReader

class Day09Test : BaseDayTest(9) {

    @Test
    fun `results of sample input`() {
        Day09(input).solve1() shouldBe 13
        Day09(InputReader.inputAsString(9, 2022, "-part2")).solve1() shouldBe 88
    }

    @Test
    fun `part2 results for sample input`() {
        Day09(input).solve2() shouldBe 1
        Day09(InputReader.inputAsString(9, 2022, "-part2")).solve2() shouldBe 36
    }
}