package utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

data class Point(
    val x: Int,
    val y: Int
) {
    operator fun plus(point: Point) = Point(x + point.x, y + point.y)

    operator fun minus(point: Point) = Point(x - point.x, y - point.y)

    operator fun times(by: Int) = Point(x * by, y * by)

    fun move(dx: Int, dy: Int) = Point(x + dx, y + dy)

    fun move(direction: Direction) = this + direction.point

    fun moveBy(direction: Direction, amount: Int) = this + direction.point * amount

    @Deprecated("Use manhattanDistanceTo", ReplaceWith("manhattanDistanceTo"))
    fun distanceTo(x: Int, y: Int) = manhattanDistanceTo(x, y)

    @Deprecated("Use manhattanDistanceTo", ReplaceWith("manhattanDistanceTo"))
    infix fun distanceTo(other: Point) = manhattanDistanceTo(other)

    fun manhattanDistanceTo(x: Int, y: Int): Int = abs(x - this.x) + abs(y - this.y)

    infix fun manhattanDistanceTo(other: Point) = manhattanDistanceTo(other.x, other.y)

    fun straightDistanceTo(x: Int, y: Int) =
        sqrt((x - this.x).toDouble().pow(2) + (y - this.y).toDouble().pow(2))

    infix fun straightDistanceTo(other: Point) = straightDistanceTo(other.x, other.y)

    fun up() = move(Direction.North)

    fun left() = move(Direction.West)

    fun right() = move(Direction.East)

    fun down() = move(Direction.South)

    val neighboursNotDiagonal by lazy {
        listOf(
            up(),
            down(),
            left(),
            right()
        )
    }

    val neighboursWithItself by lazy {
        listOf(
            up().left(),
            up(),
            up().right(),
            left(),
            this,
            right(),
            down().left(),
            down(),
            down().right()
        )
    }

    val neighbours by lazy { neighboursWithItself.filterNot { it == this } }

    fun slope(other: Point): Point {
        val dX = other.x - x
        val dY = other.y - y
        val gcd = gcd(abs(dX), abs(dY))
        return Point(dX / gcd, dY / gcd)
    }

    fun pointsTo(other: Point): Sequence<Point> {
        val slope = this.slope(other)
        return sequence {
            yield(this@Point)
            var curr = this@Point
            while (curr != other) {
                curr += slope
                yield(curr)
            }
        }
    }

    fun walkTo(other: Point): Sequence<Point> {
        return sequence {
            var curr = this@Point
            while (curr != other) {
                curr += Point((other.x - x).sign, (other.y - y).sign)
                yield(curr)
            }
        }
    }

    companion object {
        val ORIGIN = Point(0, 0)
    }
}

fun <T> Map<Point, T>.asString(
    prefix: String = "\n",
    transform: (T?) -> CharSequence
): String = asString(prefix) { x, y -> transform(get(Point(x, y))) }

fun Map<Point, Boolean>.asString(
    prefix: String = "\n",
): String = asString(prefix) { x, y -> if (get(Point(x, y)) == true) "█" else " " }

fun <T> Map<Point, T>.asString(
    prefix: String = "\n",
    producer: (x: Int, y: Int) -> CharSequence = { x, y -> get(Point(x, y))?.toString() ?: " " }
): String {
    val minX = keys.minOf { it.x }
    val maxX = keys.maxOf { it.x }
    val minY = keys.minOf { it.y }
    val maxY = keys.maxOf { it.y }
    return (minY..maxY)
        .joinToString(prefix = prefix, separator = "\n") { y ->
            (minX..maxX).joinToString(separator = "") { x ->
                producer(x, y)
            }
        }
}