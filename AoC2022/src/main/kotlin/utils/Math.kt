package utils

import kotlin.math.abs

fun gcd(a: Int, b: Int, vararg numbers: Int): Int = when {
    numbers.isEmpty() -> if (b == 0) a else gcd(b, a % b)
    else -> gcd(gcd(a, b), numbers.first(), *numbers.drop(1).toIntArray())
}

fun gcd(ints: List<Int>): Int = gcd(ints[0], ints[1], *ints.drop(2).toIntArray())

fun lcm(a: Int, b: Int, vararg numbers: Int): Int = when {
    numbers.isEmpty() -> abs(a * b) / gcd(a, b)
    else -> lcm(lcm(a, b), numbers.first(), *numbers.drop(1).toIntArray())
}

fun lcm(ints: List<Int>): Int = lcm(ints[0], ints[1], *ints.drop(2).toIntArray())

fun gcd(a: Long, b: Long, vararg numbers: Long): Long = when {
    numbers.isEmpty() -> if (b == 0L) a else gcd(b, a % b)
    else -> gcd(gcd(a, b), numbers.first(), *numbers.drop(1).toLongArray())
}

fun gcd(ints: List<Long>): Long = gcd(ints[0], ints[1], *ints.drop(2).toLongArray())

fun lcm(a: Long, b: Long, vararg numbers: Long): Long = when {
    numbers.isEmpty() -> abs(a * b) / gcd(a, b)
    else -> lcm(lcm(a, b), numbers.first(), *numbers.drop(1).toLongArray())
}

fun lcm(ints: List<Long>): Long = lcm(ints[0], ints[1], *ints.drop(2).toLongArray())
