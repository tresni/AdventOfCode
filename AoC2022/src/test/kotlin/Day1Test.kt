import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class Day1Test {
    val input = File("src/test/resources/Day1/SimpleElfs.txt").readText()

    @Test
    fun `should give the max calories from all elves`() {
        Day1().mostCalories(input) shouldBe 24000
    }
}