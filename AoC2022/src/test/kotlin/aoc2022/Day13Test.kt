package aoc2022

import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import org.junit.jupiter.api.Test
import utils.BaseDayTest

class Day13Test : BaseDayTest(2022, 13) {

    fun String.asJsonArray() = Json.parseToJsonElement(this).jsonArray

    @Test
    fun examples() {
        "[1,1,3,1,1]".asJsonArray() validPacket "[1,1,5,1,1]".asJsonArray() shouldBe true

        "[[1],[2,3,4]]".asJsonArray() validPacket "[[1],4]".asJsonArray() shouldBe true

        "[9]".asJsonArray() validPacket "[[8, 7, 6]]".asJsonArray() shouldBe false

        "[[4, 4], 4, 4]".asJsonArray() validPacket "[[4, 4], 4, 4, 4]".asJsonArray() shouldBe true

        "[7, 7, 7, 7]".asJsonArray() validPacket "[7, 7, 7]".asJsonArray() shouldBe false

        "[]".asJsonArray() validPacket "[3]".asJsonArray() shouldBe true

        "[[[]]]".asJsonArray() validPacket "[[]]".asJsonArray() shouldBe false

        "[1, [2, [3, [4, [5, 6, 7]]]], 8, 9]".asJsonArray() validPacket "[1, [2, [3, [4, [5, 6, 0]]]], 8, 9]".asJsonArray() shouldBe false
    }

    @Test
    fun `solves part 1 example`() {
        Day13(input).solve1() shouldBe 13
    }

}