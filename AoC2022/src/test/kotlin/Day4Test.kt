import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day4Test : BaseDayTest(4) {
    @Test
    fun `finding overlaps`() {
        Day4.Assignments("2-4,6-8").overlap() shouldBe emptySet()
        Day4.Assignments("2-3,4-5").overlap() shouldBe emptySet()
        Day4.Assignments("5-7,7-9").overlap() shouldBe setOf(7)
        Day4.Assignments("2-8,3-7").overlap() shouldBe setOf(3, 4, 5, 6, 7)
        Day4.Assignments("6-6,4-6").overlap() shouldBe setOf(6)
        Day4.Assignments("2-6,4-8").overlap() shouldBe setOf(4, 5, 6)
    }

    @Test
    fun `check for all inclusive`() {
        Day4.Assignments("2-8,3-7").apply {
            this.overlap() shouldBe this.minSet()
        }
        Day4.Assignments("6-6,4-6").apply {
            this.overlap() shouldBe this.minSet()
        }
    }

    @Test
    fun `check part 1 with test input`() {
        Day4(input).countFullOverlaps() shouldBe 2
    }

    @Test
    fun `check part 2 with test input`() {
        Day4(input).countOverlaps() shouldBe 4
    }
}