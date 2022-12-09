import AoC2022.Day09
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test : BaseDayTest(9) {

    @Test
    fun `results of sample input`() {
        Day09(input).solve1() shouldBe 13
    }
}