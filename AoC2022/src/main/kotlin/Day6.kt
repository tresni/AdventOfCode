import java.util.Deque

class Day6 {
    companion object {
        fun startOfInput(message: String): Int {
            var last4 = mutableListOf<Char>()
            return message.takeWhile {
                if (last4.size == 4 && last4.groupBy { c -> c }.map { g -> g.value.size }.all { n -> n == 1 }) {
                    false
                } else {
                    last4.add(it)
                    if (last4.size == 5) {
                        last4.removeAt(0)
                    }
                    true
                }
            }.length
        }
    }
}

fun main() {
    val input = readInput(6)
    println(Day6.startOfInput(input))
}