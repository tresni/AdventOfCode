class Day5(input: String) {

    internal val crates: List<MutableList<String>>
    internal val instructions: List<String>

    init {
        val (initial, details) = input.split("\n\n")
        crates = Crates(initial)
        instructions = details.split("\n")
    }

    fun runInstructions(advanced: Boolean = false): String {
        instructions.forEach {
            "move (\\d+) from (\\d+) to (\\d+)".toRegex().find(it)?.let { match ->
                val count = match.groupValues[1].toInt()
                val source = match.groupValues[2].toInt() - 1

                this.crates[match.groupValues[3].toInt() - 1].addAll(this.crates[source].takeLast(count).reversed().run {
                    if (advanced) {
                        this.reversed()
                    }
                    else {
                        this
                    }
                })
                repeat(count) { this.crates[source].removeAt(this.crates[source].size - 1) }
            }
        }
        return crates.joinToString("") { it.last() }
    }
    companion object {
        internal fun Crates(input: String): List<MutableList<String>> {
            val crates = mutableMapOf<Int, MutableList<String>>()
            input.split("\n").map {
                if (!it.contains("[")) return@map
                it.chunked(4).forEachIndexed { index, s ->
                    if (s.isNotBlank()) {
                        crates.getOrPut(index) { mutableListOf() }.add(0, s[1].toString())
                    }
                }
            }
            return crates.toSortedMap().map { it.value }
        }
    }
}

fun main() {
    val input = readInput(5)
    println(Day5(input).runInstructions())
    println(Day5(input).runInstructions(advanced = true))
}