package aoc2022

import utils.BaseDay

class Valve(val name: String, val pressure: Int) {
    private var open: Boolean = false
    private val neighbors: MutableList<Valve> = mutableListOf()

    fun generatePaths(visited: List<Valve> = emptyList()): Sequence<List<Valve>> = sequence {
        val newPath = visited.plus(this@Valve)
        if (!isOpen()) yield(newPath)

        neighbors.forEach {
            if (it !in visited)
                yieldAll(it.generatePaths(newPath))
        }
    }

    fun bestMove(startTime: Int): List<Valve> =
        generatePaths().maxByOrNull { (startTime - (it.size + 1)) * it.last().pressure } ?: emptyList()

    fun openValve(): Int {
        open = true
        return pressure
    }

    fun isOpen() = open || pressure == 0

    fun setNeighbors(n: List<Valve>) = neighbors.apply { clear(); addAll(n) }
}

class Day15(input: String) : BaseDay<Int, Int>() {
    val EXPLOSION_TIME = 30
    val LINE_REGEX =
        "Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ((?:[A-Z]{2}(?:, )?)+)".toRegex()
    var currentPosition: Valve

    init {
        val valveIndex /* heh */: MutableMap<String, Pair<Valve, List<String>>> = mutableMapOf()
        input.lines().forEach {
            LINE_REGEX.find(it)?.let { match ->
                valveIndex[match.groupValues[1]] =
                    Pair(Valve(match.groupValues[1], match.groupValues[2].toInt()), match.groupValues[3].split(", "))
            } ?: error("Well that didn't work: $it")
        }

        valveIndex.values.forEach { (valve, neighbors) ->
            valve.setNeighbors(neighbors.mapNotNull { valveIndex[it]?.first })
        }

        currentPosition = valveIndex["AA"]?.first ?: error("FML")
    }

    override fun solve1(): Int {
        var timeIndex = 0
        var totalPressure = 0
        var currentPressure = 0
        while (timeIndex < EXPLOSION_TIME) {
            val timeRemaining = EXPLOSION_TIME - timeIndex
            currentPosition = currentPosition.bestMove(timeRemaining)
                .reduceOrNull { _, valve ->
                    println("== Minute ${timeIndex + 1} ==")
                    println("Releasing $currentPressure pressure (total ${totalPressure + currentPressure})")
                    println("You move to valve ${valve.name}")
                    println()
                    timeIndex += 1
                    if (timeIndex < EXPLOSION_TIME) totalPressure += currentPressure
                    valve
                } ?: run {
                for (i in timeIndex until EXPLOSION_TIME) {
                    println("== Minute ${timeIndex + 1} ==")
                    println("Releasing $currentPressure pressure (total ${totalPressure + currentPressure})")
                    println()
                    timeIndex += 1
                    totalPressure += currentPressure
                }
                currentPosition
            }


            if (!currentPosition.isOpen()) {
                println("== Minute ${timeIndex + 1} ==")
                println("Releasing $currentPressure pressure (total ${totalPressure})")
                println("You open valve ${currentPosition.name}")
                println()
                currentPressure += currentPosition.openValve()
                timeIndex += 1
            }
        }
        return totalPressure
    }

    override fun solve2(): Int {
        TODO("Not yet implemented")
    }
}