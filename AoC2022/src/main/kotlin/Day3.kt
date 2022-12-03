class Day3(val input: String) {
    fun rucksackPriorities() = input.split("\n").sumOf { Rucksack(it).needsMoved().sumOf { c -> Rucksack.priority(c)}}
    fun findBadges() = input.split("\n").chunked(3).map {
        it.map { s -> s.toSet() }.reduce { acc, itt -> acc.intersect(itt) }
    }

    class Rucksack(private val inventory: String) {
        fun topCompartment() = inventory.substring(0, inventory.length / 2 )
        fun bottomCompartment() = inventory.substring(inventory.length / 2)

        fun needsMoved() = topCompartment().toSet().intersect(bottomCompartment().toSet())

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
    val input = Utils.readInput(3)
    println(Day3(input).rucksackPriorities())
    println(Day3(input).findBadges().sumOf { s -> s.sumOf { c -> Day3.Rucksack.priority(c) }})
}