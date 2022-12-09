package aoc2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day6Test : BaseDayTest(5) {

    @Test
    fun `initial tests`() {
        Day6.startOfInput("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 7
        Day6.startOfInput("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5
        Day6.startOfInput("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6
        Day6.startOfInput("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10
        Day6.startOfInput("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11
    }
}