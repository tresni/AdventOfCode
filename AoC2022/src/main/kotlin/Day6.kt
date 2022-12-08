import java.util.Deque

class Day6 {
    companion object {
        fun startOfInput(message: String, size: Int = 4): Int {
            var last4 = mutableListOf<Char>()
            return message.takeWhile {
                if (last4.size == size && last4.groupBy { c -> c }.map { g -> g.value.size }.all { n -> n == 1 }) {
                    false
                } else {
                    last4.add(it)
                    if (last4.size == size+1) {
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
    println(Day6.startOfInput(input, 14))
}