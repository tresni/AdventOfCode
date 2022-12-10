package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest
import utils.easyReading

class Day10Test : BaseDayTest(2022, 10) {

    @Test
    fun `test solutions for part 1`() {
        Day10(input).apply {
            solve1() shouldBe 13140
        }
    }

    @Test
    fun `result at iteration and value`() {
        Day10(input).apply {
            valueAtCycle(20) shouldBe 420
            valueAtCycle(60) shouldBe 1140
            valueAtCycle(100) shouldBe 1800
            valueAtCycle(140) shouldBe 2940
            valueAtCycle(180) shouldBe 2880
            valueAtCycle(220) shouldBe 3960
        }
    }

    @Test
    fun `solve part 2`() {
        Day10(input).solve2() shouldBe """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent().easyReading()
    }
}