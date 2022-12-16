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
    if (this.size <= other.size) {
        return true
    }
    return null
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
        TODO("Not yet implemented")
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 13)
    Day13(input).apply {
        println(solve1())
        println(solve2())
    }
}