import java.io.File

class Day1 {
    fun mostCalories(supplies: String?): Int = supplies?.let { elves ->
        elves.split("\n\n").maxOfOrNull { it.split("\n").sumOf { it.toInt() } }
    } ?: 0
}

fun main() {
    print(Day1().mostCalories(File("src/main/resources/Day1/input.txt").readText()))
}
