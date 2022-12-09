package utils

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

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

    fun distanceTo(x: Int, y: Int): Int {
        val dx = (x - this.x).absoluteValue
        val dy = (y - this.y).absoluteValue
        return dx + dy
    }

    infix fun distanceTo(other: Point) = distanceTo(other.x, other.y)

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
        val gcd = gcd(dX.absoluteValue, dY.absoluteValue)
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
                val dX = min(max(other.x - x, -1), 1)
                val dY = min(max(other.y - y, -1), 1)
                curr += Point(dX, dY)
                yield(curr)
            }
        }
    }
}

fun <T> Map<Point, T>.asString(
    prefix: String = "\n",
    transform: (T?) -> CharSequence
): String = asString(prefix) { x, y -> transform(get(Point(x, y))) }

fun <T> Map<Point, T>.asString(
    prefix: String = "\n",
    producer: (x: Int, y: Int) -> CharSequence
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