import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day3Test : BaseDayTest(3) {
    @Test
    fun `first rucksack`() {
        val rucksack = Day3.Rucksack("vJrwpWtwJgWrhcsFMMfFFhFp")
        rucksack.topCompartment() shouldBe "vJrwpWtwJgWr"
        rucksack.bottomCompartment() shouldBe "hcsFMMfFFhFp"
        rucksack.needsMoved().first().also {
            it shouldBe 'p'
            Day3.Rucksack.priority(it) shouldBe 16
        }
    }

    @Test
    fun `second rucksack`() {
        val rucksack = Day3.Rucksack("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")
        rucksack.topCompartment() shouldBe "jqHRNqRjqzjGDLGL"
        rucksack.bottomCompartment() shouldBe "rsFMfFZSrLrFZsSL"
        rucksack.needsMoved().first().also {
            it shouldBe 'L'
            Day3.Rucksack.priority(it) shouldBe 38
        }
    }

    @Test
    fun `third rucksack`() {
        val rucksack = Day3.Rucksack("PmmdzqPrVvPwwTWBwg")
        rucksack.topCompartment() shouldBe "PmmdzqPrV"
        rucksack.bottomCompartment() shouldBe "vPwwTWBwg"
        rucksack.needsMoved().first().also {
            it shouldBe 'P'
            Day3.Rucksack.priority(it) shouldBe 42
        }
    }

    @Test
    fun `final rucksacks`() {
        Day3.Rucksack("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn").needsMoved().first().also {
            it shouldBe 'v'
            Day3.Rucksack.priority(it) shouldBe 22
        }
        Day3.Rucksack("ttgJtRGJQctTZtZT").needsMoved().first().also {
            it shouldBe 't'
            Day3.Rucksack.priority(it) shouldBe 20
        }
        Day3.Rucksack("CrZsJsPPZsGzwwsLwLmpwMDw").needsMoved().first().also {
            it shouldBe 's'
            Day3.Rucksack.priority(it) shouldBe 19
        }
    }

    @Test
    fun `total priorities`() {
        Day3(input).rucksackPriorities() shouldBe 157
    }

    @Test
    fun `badge priorities`() {
        val firstSet = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
        """.trimIndent()

        Day3(firstSet).findBadges() shouldBe listOf(listOf('r'))

        val secondSet = """
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()

        Day3(secondSet).findBadges() shouldBe listOf(listOf('Z'))

        Day3(listOf(firstSet, secondSet).joinToString("\n")).findBadges().also {
            it shouldBe listOf(listOf('r'), listOf('Z'))
            it.sumOf { s -> s.sumOf { c -> Day3.Rucksack.priority(c) } } shouldBe 70
        }
    }
}