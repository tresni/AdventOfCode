package aoc2022

import kotlinx.serialization.json.*
import utils.BaseDay
import utils.InputReader

infix fun JsonArray.validPacket(other: JsonArray): Boolean? {
    this.forEachIndexed { index, element ->
        if (index >= other.size) {
            return false
        } else if (element is JsonArray && other[index] !is JsonArray) {
            (element.jsonArray validPacket JsonArray(listOf(other[index])))?.let {
                return it
            }
        } else if (element !is JsonArray && other[index] is JsonArray) {
            (JsonArray(listOf(element)) validPacket other[index].jsonArray)?.let {
                return it
            }
        } else if (this[index] is JsonArray && other[index] is JsonArray) {
            (element.jsonArray validPacket other[index].jsonArray)?.let {
                return it
            }
        } else if (element.jsonPrimitive.int < other[index].jsonPrimitive.int) {
            return true
        } else if (element.jsonPrimitive.int > other[index].jsonPrimitive.int) {
            return false
        }
    }

    // we made it all the way through this array, that means if we have smaller array, we should return true
    if (this.size < other.size) {
        return true
    } else if (this.size > other.size) {
        return false
    }
    return null
}

fun String.asJsonArray() = Json.parseToJsonElement(this).jsonArray

class CompareJsonArray {
    companion object : Comparator<JsonArray> {
        override fun compare(left: JsonArray, right: JsonArray): Int {
            (left validPacket right)?.let {
                if (it) return -1
                else return 1
            }
            return 0
        }
    }
}

class Day13(input: String) : BaseDay<Int, Int>() {
    val valuePairs = mutableListOf<Pair<JsonArray, JsonArray>>()

    init {
        input.lines().chunked(3).forEach {
            valuePairs.add(
                Pair(
                    Json.parseToJsonElement(it[0]).jsonArray,
                    Json.parseToJsonElement(it[1]).jsonArray,
                )
            )
        }
    }

    override fun solve1(): Int {
        return valuePairs.mapIndexedNotNull { index, (left, right) ->
            if (left validPacket right == true) {
                index + 1
            } else {
                null
            }
        }.sum()
    }

    override fun solve2(): Int {
        val dividerPackets = listOf("[[2]]".asJsonArray(), "[[6]]".asJsonArray())
        return valuePairs.flatMap { listOf(it.first, it.second) }.toMutableList().apply {
            this.addAll(dividerPackets)
        }.sortedWith(CompareJsonArray).let {
            (it.indexOf(dividerPackets[0]) + 1) * (it.indexOf(dividerPackets[1]) + 1)
        }
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 13)
    Day13(input).apply {
        println(solve1())
        println(solve2())
    }
}