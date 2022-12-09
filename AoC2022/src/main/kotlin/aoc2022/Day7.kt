package aoc2022

import utils.InputReader

class Day7(val input: String) {

    class Directory(
        val parent: Directory?
    ) {
        private var size: Int? = null
        val sub = mutableMapOf<String, Directory>()
        val files = mutableMapOf<String, Int>()

        fun cd(name: String): Directory {
            if (name !in sub) {
                sub[name] = Directory(this)
            }
            return sub[name]!!
        }

        fun size(): Int {
            if (size == null) {
                size = files.values.sum() + sub.map { it.value.size() }.sum()
            }
            return size as Int
        }

        fun smallDirectories(): List<Directory> {
            return sub.flatMap {
                it.value.smallDirectories()
            } + sub.filter {
                it.value.size() < MAX_SIZE
            }.values.toList()
        }

        fun minDirectorySize(size: Int): List<Directory> {
            return sub.flatMap {
                it.value.minDirectorySize(size)
            } + sub.filter {
                it.value.size() >= size
            }.values.toList()
        }

        fun smallDirectoriesSize(): Int = smallDirectories().sumOf { it.size() }
    }

    val rootDirectory = Directory(null)
    private var currentDirectory = rootDirectory

    init {
        input.split("\n").forEach {
            val tokens = it.split(" ")
            when (tokens[0]) {
                "$" -> {
                    if (tokens[1] == "cd") {
                        currentDirectory = when (tokens[2]) {
                            ".." -> currentDirectory.parent!!
                            "/" -> rootDirectory
                            else -> currentDirectory.cd(tokens[2])
                        }
                    }
                }

                "dir" -> currentDirectory.cd(tokens[1])
                else -> currentDirectory.files[tokens[1]] = tokens[0].toInt()
            }
        }
    }

    fun findCandidateDirectory(): Int {
        val free = AVAILABLE - rootDirectory.size()
        val need = NEED_SIZE - free
        return rootDirectory.minDirectorySize(need).minOf { it.size() }
    }

    companion object {
        const val MAX_SIZE = 100000
        const val NEED_SIZE = 30000000
        const val AVAILABLE = 70000000
    }
}

fun main() {
    val filesystem = Day7(InputReader.inputAsString(2022, 7))
    println(filesystem.rootDirectory.smallDirectoriesSize())
    println(filesystem.findCandidateDirectory())
}