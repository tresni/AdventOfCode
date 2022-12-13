package utils.search

import utils.Point

fun bfs(start: Point, end: (Point) -> Boolean, check: (Point, Point) -> Boolean = { _, _ -> true }): Int? {
    val searched = mutableMapOf<Point, Point?>(start to null) // key = child, value = parent
    val queue = mutableListOf(start)
    while (queue.isNotEmpty()) {
        val investigate = queue.removeFirst()
        if (end(investigate)) {
            var n: Point? = investigate
            var i = 0
            while (n != start) {
                n = searched[n]
                i++
            }
            return i
        }

        investigate.neighboursNotDiagonal.forEach {
            if (it in searched) return@forEach
            if (check(investigate, it)) {
                searched[it] = investigate
                queue.add(it)
            }
        }
    }
    return null
}