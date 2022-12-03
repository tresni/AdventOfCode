import java.io.File

class Day3(val input: String) {
    fun rucksackPriorities() = input.split("\n").sumOf { Rucksack(it).needsMoved().sumOf { c -> Rucksack.priority(c)}}
    fun findBadges() = input.split("\n").chunked(3).map {
        it.reduce { acc, itt ->
            acc.toSet().intersect(itt.toSet()).joinToString("")
        }
    }

    class Rucksack(private val inventory: String) {
        fun topCompartment() = inventory.substring(0, inventory.length / 2 )
        fun bottomCompartment() = inventory.substring(inventory.length / 2)

        fun needsMoved() = topCompartment().asSequence().toSet().intersect(bottomCompartment().asSequence().toSet()).joinToString("")

        companion object {
            fun priority(char: Char) = when (char) {
                in 'a'..'z' -> char - 'a' + 1
                in 'A'..'Z' -> char - 'A' + 27
                else -> 0
            }
        }
    }
}

fun main() {
    val input = File("src/main/resources/Day3/input.txt").readText()
    println(Day3(input).rucksackPriorities())
    println(Day3(input).findBadges().sumOf { s -> s.sumOf { c -> Day3.Rucksack.priority(c) }})
}