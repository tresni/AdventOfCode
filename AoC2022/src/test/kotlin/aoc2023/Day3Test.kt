package aoc2023

import io.kotest.matchers.maps.shouldContainValue
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.*

class Day3Test: BaseDayTest(2023, 3) {

    @Test
    fun `part 1 example`() {
        Day3().listPartNumbers().also {
            it shouldBe listOf(467, 35, 633, 617, 592, 755, 664, 598)
            it.sum() shouldBe 4361
        }
        Day3().listPartNumbers()
    }

    @Test
    fun `part 2 example`() {
        Day3().findGears().also {
            it shouldHaveSize 2
            it shouldContainValue listOf(467, 35)
        }
    }
}