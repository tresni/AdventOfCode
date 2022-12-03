import java.io.File

class Day3(val input: String) {
    fun priorities() = input.split("\n").sumOf { Rucksack(it).needsMoved().sumOf { c -> Rucksack.priority(c)}}

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
    println(Day3(File("src/main/resources/Day3/input.txt").readText()).priorities())
}