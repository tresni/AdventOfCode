package aoc2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.*

class Day1Test : BaseDayTest(2023, 1) {
    @Test
    fun `example input for part 1 should return 142`() {
        Day1().findCalibrationValue(input.lines()) shouldBe 142
    }

    @Test
    fun `example input for part 2 should return 281`() {
        val part2Input = InputReader.inputAsString(2023, 1, "2")
        Day1().findUpdatedCalibrationValue(part2Input.lines()) shouldBe 281
    }

    @Test
    fun `stupid`() {
        Day1().findUpdatedCalibrationValue(listOf("eightwone")) shouldBe 81
    }
}