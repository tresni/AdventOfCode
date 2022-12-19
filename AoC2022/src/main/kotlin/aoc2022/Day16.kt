package aoc2022

import utils.*
import kotlin.system.measureTimeMillis

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
    }

    fun multidfs(
        graph: MutableMap<Pair<String, String>, Int>,
        start: List<String>,
        offset: List<Int>,
        acc: Int,
        history: List<List<String>>
    ): Int {
        val nodes = fullGraph.filterKeys { it in start }
        val steam = nodes.map { (key, value) ->
            val index = start.indexOf(key)
            if (offset[index] >= 0)
                value.first * offset[index]
            else
                0
        }.sum()

        val neighbors = graph
            .filterKeys { (key, target) -> key in start && target !in history.flatten() }
            .toList()
            .groupBy { (k, _) -> k.first }

        if (neighbors.isEmpty())
            return acc + steam

        var combinations = neighbors[start[0]]!!.map { listOf(it.first.second) }
        repeat(start.size - 1) { index ->
            val node = start[index + 1]
            combinations = combinations
                .zipInList(
                    neighbors[node]!!
                        .map { it.first.second }
                        .filter { graph[Pair(node, it)]!! < offset[index + 1] }
                )
                .filter { it.allDistinct() }
        }

        return combinations.maxOfOrNull {
            multidfs(
                graph,
                it,
                it.mapIndexed { index, s -> offset[index] - graph[Pair(start[index], s)]!! },
                acc + steam,
                it.mapIndexed { index, s -> history[index].plus(s) }
            )
        } ?: (acc + steam)
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
        return multidfs(valveMap, listOf("AA", "AA"), listOf(26, 26), 0, listOf(emptyList(), emptyList()))
    }
}

fun main() {
    val input = InputReader.inputAsString(2022, 15)
    Day15(input).apply {
        measureTimeMillis {
            println(solve1())
        }.also {
            println("Solved in ${"%.2f".format(it.div(1000.0))}s")
        }
        measureTimeMillis {
            println(solve2())
        }.also {
            println("Solved in ${"%.2f".format(it.div(1000.0))}s")
        }
    }
}