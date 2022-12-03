import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day2Test: BaseDayTest(2) {
    @Test
    fun `basic tests`() {
        Day2("A Y").score() shouldBe 8
        Day2("B X").score() shouldBe 1
        Day2("C Z").score() shouldBe 6
    }

    @Test
    fun `should end with a score of 15`() {
        Day2(input).score() shouldBe 15
    }

    @ParameterizedTest
    @MethodSource("handShapeStream")
    fun `ensure scores`(handShape: Day2.Companion.HandShape) {
        Day2("${handShape.weak.primary} ${handShape.opp.secondary}").score() shouldBe Day2.LOSE + handShape.points
        Day2("${handShape.opp.primary} ${handShape.opp.secondary}").score() shouldBe Day2.TIE + handShape.points
        Day2("${handShape.strong.primary} ${handShape.opp.secondary}").score() shouldBe Day2.WIN + handShape.points
    }

    @Test
    fun `basic rigged`() {
        Day2("A Y").rig() shouldBe 4
        Day2("B X").rig() shouldBe 1
        Day2("C Z").rig() shouldBe 7
    }

    @Test
    fun `extended rigged`() {
        Day2(input).rig() shouldBe 12
    }

    @ParameterizedTest
    @MethodSource("handShapeStream")
    fun `ensure rigged`(handShape: Day2.Companion.HandShape) {
        Day2("${handShape.opp.primary} X").rig() shouldBe Day2.LOSE + Day2.shapeTable[handShape.strong.primary]!!.points
        Day2("${handShape.opp.primary} Y").rig() shouldBe Day2.TIE + handShape.points
        Day2("${handShape.opp.primary} Z").rig() shouldBe Day2.WIN + Day2.shapeTable[handShape.weak.primary]!!.points
    }

    companion object {
        @JvmStatic
        fun handShapeStream(): Stream<Arguments> = Stream.of(
            Arguments.of(Day2.ROCK),
            Arguments.of(Day2.PAPER),
            Arguments.of(Day2.SCISSORS)
        )
    }
}
