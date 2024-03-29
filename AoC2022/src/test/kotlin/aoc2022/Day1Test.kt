package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day1Test : BaseDayTest(2022, 1) {
    @Test
    fun `should give the max calories from all elves`() {
        Day1().mostCalories(input) shouldBe 24000
    }

    @Test
    fun `should get the total calories from top 3 elves`() {
        Day1().topCalories(input, top = 3) shouldBe 45000
    }
}