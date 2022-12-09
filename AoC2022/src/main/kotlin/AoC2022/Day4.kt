class Day4(val input: String) {
    fun countFullOverlaps() = input.split("\n").count {
        Assignments(it).let { a -> a.overlap() == a.minSet() }
    }

    fun countOverlaps() = input.split("\n").count {
        Assignments(it).overlap().isNotEmpty()
    }

    class Assignments(val input: String) {
        fun toSets() = input.split(",")
            .map {
                it.split("-").map { r -> r.toInt() }.let { ints ->
                    (ints.first()..ints.last()).toSet()
                }
            }

        fun overlap() = this.toSets().reduce { acc, ints -> acc.intersect(ints) }

        fun minSet() = this.toSets().minBy { it.size }
    }
}

fun main() {
    val input = readInput(4)
    println(Day4(input).countFullOverlaps())
    println(Day4(input).countOverlaps())
}