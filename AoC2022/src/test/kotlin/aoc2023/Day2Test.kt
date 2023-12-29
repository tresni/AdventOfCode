package aoc2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.*

class Day2Test : BaseDayTest(2023, 2) {
    @Test
    fun `part 1 test`() {
        Day2().possibleGameSum(input.lines()) shouldBe 8
    }

    @Test
    fun `part 2 test`() {
        Day2().diePower(input.lines()) shouldBe listOf(48, 12, 1560, 630, 36)
        Day2().diePower(input.lines()).sum() shouldBe 2286
    }
}