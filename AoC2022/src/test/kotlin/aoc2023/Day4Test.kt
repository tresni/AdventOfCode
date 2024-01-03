package aoc2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.*

class Day4Test : BaseDayTest(2023, 4) {

    @Test
    fun `calculate matches per card`() {
        input.lines().map {
            Day4().matchesPerCard(it)
        } shouldBe listOf(
            4, 2, 2, 1, 0, 0
        )
    }

    @Test
    fun `calculate won cards per card`() {
        input.lines().map {
            Day4().nextCardsWon(it)
        } shouldBe listOf(
            listOf(1, 1, 1, 1),
            listOf(1, 1),
            listOf(1, 1),
            listOf(1),
            listOf(),
            listOf()
        )
    }

    @Test
    fun `calculate total won cards`() {
        Day4().totalCardsWon(input.lines()) shouldBe 30
    }
}