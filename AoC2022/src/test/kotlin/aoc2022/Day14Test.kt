package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest
import utils.asString

class Day14Test : BaseDayTest(2022, 14) {

    @Test
    fun `loads correctly`() {
        Day14(input)
        sandFall.asString("") { c -> c ?: "." } shouldBe """
            ....#...##
            ....#...#.
            ..###...#.
            ........#.
            ........#.
            #########.
        """.trimIndent()
    }

    @Test
    fun `solve1 for example`() {
        Day14(input).solve1() shouldBe 24
    }

    @Test
    fun `solve2 for example`() {
        Day14(input).solve2() shouldBe 93
    }
}