package aoc2022

import utils.BaseDay
import utils.InputReader
import utils.bfs

class Day15(input: String) : BaseDay<Int, Int>() {
    val EXPLOSION_TIME = 30
    val LINE_REGEX =
        "Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ((?:[A-Z]{2}(?:, )?)+)".toRegex()

    val fullGraph = mutableMapOf<String, Pair<Int, List<String>>>()
    val valveMap = mutableMapOf<Pair<String, String>, Int>()

    init {
        input.lines().forEach {
            LINE_REGEX.find(it)?.let { match ->
                val name = match.groupValues[1]
                val pressure = match.groupValues[2].toInt()
                val connections = match.groupValues[3].split(", ")
                fullGraph[name] = Pair(pressure, connections)
            } ?: error("Well that didn't work: $it")
        }

        val valvesToOpen = fullGraph.filter { it.value.first != 0 }
        val remaining = valvesToOpen.toMutableMap()
        valvesToOpen.forEach { (key, _) ->
            valveMap[Pair("AA", key)] = bfs("AA", { t -> t == key }, { t -> fullGraph[t]!!.second })!!.size
            remaining.remove(key)
            remaining.forEach { (target, _) ->
                val distance = bfs(key, { t -> t == target }, { t -> fullGraph[t]!!.second })!!.size
                valveMap[Pair(key, target)] = distance
                valveMap[Pair(target, key)] = distance
            }
        }

        println(valveMap)
    }

    fun dfs(
        graph: MutableMap<Pair<String, String>, Int>,
        start: String,
        offset: Int = EXPLOSION_TIME,
        acc: Int = 0,
        history: List<String> = emptyList()
    ): Int {
        val neighbors = graph.filterKeys { (key, target) -> key == start && target !in history }
        val node = fullGraph[start]!!
        val steam = node.first * offset
        if (neighbors.isEmpty())
            return acc + steam

        return neighbors.maxOf { (pair, distance) ->
            if (distance < offset) {
                dfs(
                    graph,
                    pair.second,
                    offset - distance,
                    acc + steam,
                    history.plus(pair.second)
                )
            } else {
                acc + steam
            }
        }
    }

    override fun solve1(): Int {
        return dfs(valveMap, "AA")
    }

    override fun solve2(): Int {
        TODO("Not yet implemented")
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 15)
    Day15(input).apply {
        println(solve1())
        println(solve2())
    }
}