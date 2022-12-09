import aoc2022.Day5
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day5Test : BaseDayTest(5) {
    @Test
    fun `read initial crates properly`() {
        Day5.Crates(
            """
                [D]
            [N] [C]
            [Z] [M] [P]
             1   2   3
        """.trimIndent()
        ) shouldBe listOf(listOf("Z", "N"), listOf("M", "C", "D"), listOf("P"))
    }

    @Test
    fun `Reads crates from input`() {
        Day5(input).crates shouldBe listOf(listOf("Z", "N"), listOf("M", "C", "D"), listOf("P"))
    }

    @Test
    fun `follows instructions`() {
        Day5(input).runInstructions() shouldBe "CMZ"
    }

    @Test
    fun `follows advanced instructions`() {
        Day5(input).runInstructions(advanced = true) shouldBe "MCD"
    }
}