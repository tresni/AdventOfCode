class Day8(input: String) {
    private val trees: MutableList<List<Int>> = mutableListOf()

    init {
        input.lines().forEach {
            trees.add(it.map { c -> c.toString().toInt() })
        }
    }

    fun isVisible(x: Int, y: Int): Boolean {
        if (y == 0 || y == trees.size - 1 || x == 0 || x == trees[y].size - 1) return true
        return trees[y].splitsExceedValueAtIndex(x) || trees.map { it[x] }.splitsExceedValueAtIndex(y)
    }

    fun scenicScore(x: Int, y: Int): Int {
        if (y == 0 || y == trees.size - 1 || x == 0 || x == trees[y].size - 1) return 0
        val target = trees[y][x]
        return trees[y].splitAndTarget(x, target) * trees.map { it[x] }.splitAndTarget(y, target)
    }

    fun countVisible() = trees.mapIndexed { y, it -> it.filterIndexed { x, _ -> isVisible(x, y) }.size }.sum()
    fun mostScenic() = trees.mapIndexed { y, row -> List(row.size) { x -> scenicScore(x, y) }.max() }.max()

    companion object {
        private fun List<Int>.split(index: Int): Pair<List<Int>, List<Int>> =
            Pair(this.subList(0, index), this.subList(index + 1, this.size))

        fun List<Int>.splitsExceedValueAtIndex(index: Int): Boolean =
            this.split(index).let { (left, right) -> this[index] > left.max() || this[index] > right.max() }

        fun List<Int>.splitAndTarget(index: Int, target: Int): Int =
            this.split(index).let { (left, right) ->
                left.reversed().filterToTarget(target) * right.filterToTarget(target)
            }

        private fun List<Int>.filterToTarget(target: Int): Int =
            if (this.isNotEmpty()) {
                this.takeWhile { it < target }.size.let {
                    if (this.size > it) it + 1 else it
                }
            } else 0
    }
}

fun main() {
    println(Day8(readInput(8)).countVisible())
    println(Day8(readInput(8)).mostScenic())
}