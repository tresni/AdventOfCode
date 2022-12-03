import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class Day3Test {
    @Test
    fun `first rucksack`() {
        val rucksack = Day3.Rucksack("vJrwpWtwJgWrhcsFMMfFFhFp")
        rucksack.topCompartment() shouldBe "vJrwpWtwJgWr"
        rucksack.bottomCompartment() shouldBe "hcsFMMfFFhFp"
        rucksack.needsMoved() shouldBe "p"
        Day3.Rucksack.priority(rucksack.needsMoved()[0]) shouldBe 16
    }

    @Test
    fun `second rucksack`() {
        val rucksack = Day3.Rucksack("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")
        rucksack.topCompartment() shouldBe "jqHRNqRjqzjGDLGL"
        rucksack.bottomCompartment() shouldBe "rsFMfFZSrLrFZsSL"
        rucksack.needsMoved() shouldBe "L"
        Day3.Rucksack.priority(rucksack.needsMoved()[0]) shouldBe 38
    }

    @Test
    fun `third rucksack`() {
        val rucksack = Day3.Rucksack("PmmdzqPrVvPwwTWBwg")
        rucksack.topCompartment() shouldBe "PmmdzqPrV"
        rucksack.bottomCompartment() shouldBe "vPwwTWBwg"
        rucksack.needsMoved() shouldBe "P"
        Day3.Rucksack.priority(rucksack.needsMoved()[0]) shouldBe 42
    }

    @Test
    fun `final rucksacks`() {
        Day3.Rucksack("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn").needsMoved().also {
            it shouldBe "v"
            Day3.Rucksack.priority(it[0]) shouldBe 22
        }
        Day3.Rucksack("ttgJtRGJQctTZtZT").needsMoved().also {
            it shouldBe "t"
            Day3.Rucksack.priority(it[0]) shouldBe 20
        }
        Day3.Rucksack("CrZsJsPPZsGzwwsLwLmpwMDw").needsMoved().also {
            it shouldBe "s"
            Day3.Rucksack.priority(it[0]) shouldBe 19
        }
    }

    @Test
    fun `total priorities`() {
        Day3(File("src/test/resources/Day3/input.txt").readText()).rucksackPriorities() shouldBe 157
    }

    @Test
    fun `badge priorities`() {
        val firstSet = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
        """.trimIndent()

        Day3(firstSet).findBadges() shouldBe listOf("r")

        val secondSet = """
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()

        Day3(secondSet).findBadges() shouldBe listOf("Z")

        Day3(listOf(firstSet, secondSet).joinToString("\n")).findBadges().also {
            it shouldBe listOf("r", "Z")
            it.sumOf { s -> s.sumOf { c -> Day3.Rucksack.priority(c) } } shouldBe 70
        }

    }
}