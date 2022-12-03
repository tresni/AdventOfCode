import java.io.File

class Utils {
    companion object {
        fun readInput(day: Int, source: String="main") = File("src/$source/resources/Day$day/input.txt").readText()
    }
}