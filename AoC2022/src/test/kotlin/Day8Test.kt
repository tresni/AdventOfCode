import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day8Test : BaseDayTest(8) {
    @Test
    fun test() {
        Day8("222\n212\n222").also {
            it.isVisible(1, 1) shouldBe false
            it.isVisible(0, 0) shouldBe true
            it.isVisible(2, 2) shouldBe true
        }
    }

    @Test
    fun withInput() {
        Day8(input).also {
            it.isVisible(0, 0) shouldBe true
            it.isVisible(4, 0) shouldBe true
            it.isVisible(0, 4) shouldBe true
            it.isVisible(4, 4) shouldBe true


            it.isVisible(0, 3) shouldBe true
            it.isVisible(1, 2) shouldBe true
            it.isVisible(1, 3) shouldBe false
            it.isVisible(1, 4) shouldBe true
            it.isVisible(2, 3) shouldBe true

            it.isVisible(1, 4) shouldBe true
            it.isVisible(2, 2) shouldBe false
            it.isVisible(3, 1) shouldBe false
            it.isVisible(3, 3) shouldBe false
            it.countVisible() shouldBe 21
        }
    }

    @Test
    fun scenicScore() {
        Day8("111\n121\n111").also {
            it.scenicScore(0, 0) shouldBe 0
            it.scenicScore(0, 2) shouldBe 0
            it.scenicScore(2, 0) shouldBe 0
            it.scenicScore(2, 2) shouldBe 0
            it.scenicScore(1, 1) shouldBe 1
        }
        Day8(input).also {
            it.scenicScore(2, 1) shouldBe 4
            it.scenicScore(2, 3) shouldBe 8
        }
    }
}