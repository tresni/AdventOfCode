package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest
import kotlin.system.measureTimeMillis

class Day16Test : BaseDayTest(2022, 16) {
    @Test
    fun solve1() {
        measureTimeMillis {
            Day16(input).solve1() shouldBe 1651
        }.also {
            println("Solved part 1 in ${"%.3f".format(it.div(1000.0))}s")
        }
    }

    @Test
    fun solve2() {
        measureTimeMillis {
            Day16(input).solve2() shouldBe 1707
        }.also {
            println("Solved part 2 in ${"%.3f".format(it.div(1000.0))}s")
        }
    }

}