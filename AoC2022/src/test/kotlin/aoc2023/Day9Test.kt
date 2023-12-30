package aoc2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.*

class Day9Test : BaseDayTest(2023, 9) {
    @Test
    fun `part 1 test`() {
        val lines = input.lines().map { it.split(" ").map { v -> v.toInt() }}
        Day9().predictNext(lines[0]) shouldBe 18
        Day9().predictNext(lines[1]) shouldBe 28
        Day9().predictNext(lines[2]) shouldBe 68
    }

    @Test
    fun `part 2 test`() {
        val last = input.lines().map { it.split(" ").map { v -> v.toInt() }}.last()
        Day9().predictPrev(last) shouldBe 5
    }

}