package utils

fun <T> bfs(
    start: T,
    end: (T) -> Boolean,
    neighbors: (T) -> List<T>,
    check: (T, T) -> Boolean = { _, _ -> true }
): List<T>? {
    val searched = mutableMapOf<T, T?>(start to null) // key = child, value = parent
    val queue = mutableListOf(start)
    while (queue.isNotEmpty()) {
        val investigate = queue.removeFirst()
        if (end(investigate)) {
            val path = mutableListOf<T>(investigate)
            while (path.last() != start) {
                searched[path.last() ?: break]?.let { path.add(it) }
            }
            return path
        }

        neighbors(investigate).forEach {
            if (it in searched) return@forEach
            if (check(investigate, it)) {
                searched[it] = investigate
                queue.add(it)
            }
        }
    }
    return null
}