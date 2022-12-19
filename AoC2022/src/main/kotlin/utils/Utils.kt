package utils

import java.io.File
import java.lang.System.lineSeparator

object InputReader {
    fun inputAsString(year: Int, day: Int, suffix: String = ""): String {
        return fromResources(year, day, suffix).readText()
    }

    fun inputAsList(year: Int, day: Int, suffix: String = ""): List<String> {
        return fromResources(year, day, suffix).readLines()
    }

    fun inputAsGroups(year: Int, day: Int, suffix: String = ""): List<String> {
        return fromResources(year, day, suffix).readText().split(lineSeparator() + lineSeparator())
    }

    fun inputAsSet(year: Int, day: Int, suffix: String = ""): Set<String> {
        val s = LinkedHashSet<String>()
        s.addAll(fromResources(year, day, suffix).readLines())
        return s
    }

    fun inputAsInts(year: Int, day: Int, suffix: String = "", linesSeparated: Boolean = false): List<Int> {
        return (if (linesSeparated) inputAsList(year, day, suffix)
        else inputAsString(year, day, suffix).split("[^\\d]+|\n"))
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    private fun fromResources(year: Int, day: Int, suffix: String = ""): File {
        return File(
            javaClass.classLoader.getResource(
                "$year/Day${day.toString().padStart(2, '0')}/input$suffix.txt"
            )?.toURI()
                ?: throw IllegalArgumentException("Cannot find input for Day $day")
        )
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> =
    this[0].indices.map { j -> this.indices.reversed().map { i -> this[i][j] } }

// from https://github.com/nkiesel/AdventOfCode_2022/blob/main/src/test/kotlin/Util.kt
fun <T> Collection<T>.powerSet(): Set<Set<T>> = powerSet(this, setOf(emptySet()))

private tailrec fun <T> powerSet(left: Collection<T>, acc: Set<Set<T>>): Set<Set<T>> {
    return if (left.isEmpty()) {
        acc
    } else {
        powerSet(left.drop(1), acc + acc.map { it + left.first() })
    }
}

fun String.easyReading() = this.replace("#", "█").replace(".", " ")