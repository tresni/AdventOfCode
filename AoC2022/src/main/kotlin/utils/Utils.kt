package utils

import java.io.File
import java.lang.System.lineSeparator

object InputReader {

    fun inputAsString(day: Int, year: Int, suffix: String = ""): String {
        return fromResources(day, year, suffix).readText()
    }

    fun inputAsList(day: Int, year: Int, suffix: String = ""): List<String> {
        return fromResources(day, year, suffix).readLines()
    }

    fun inputAsGroups(day: Int, year: Int, suffix: String = ""): List<String> {
        return fromResources(day, year, suffix).readText().split(lineSeparator() + lineSeparator())
    }

    fun inputAsSet(day: Int, year: Int, suffix: String = ""): Set<String> {
        val s = LinkedHashSet<String>()
        s.addAll(fromResources(day, year, suffix).readLines())
        return s
    }

    fun inputAsInts(day: Int, year: Int, suffix: String = "", linesSeparated: Boolean = false): List<Int> {
        return (if (linesSeparated) inputAsList(day, year, suffix)
        else inputAsString(day, year, suffix).split("[^\\d]+|\n"))
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    private fun fromResources(day: Int, year: Int, suffix: String = ""): File {
        return File(
            javaClass.classLoader.getResource(
                "Day${day.toString().padStart(2, '0')}/input$suffix.txt"
            )?.toURI()
                ?: throw IllegalArgumentException("Cannot find input for Day $day")
        )
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> =
    this[0].indices.map { j -> this.indices.reversed().map { i -> this[i][j] } }


fun readInput(day: Int) =
    InputReader.inputAsString(day, 2022)