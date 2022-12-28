import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
}

group = "com.brianandjenny.adventofcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

open class GenerateTask : DefaultTask() {
    @Input
    @Option(option = "day", description = "Which day to generate")
    open var day: String? = null

    @Input
    @Optional
    @Option(option = "year", description = "Which year to generate for, defaults to current year")
    open var year: String? = null

    private val TEMPLATE_DAY = """
        package aoc{{year}}
        
        import utils.BaseDay
        import utils.InputReader
        
        class Day{{day}}(input: String) : BaseDay<Int, Int>() {
            override fun solve1(): Int {
                TODO("Not yet implemented")
            }
        
            override fun solve2(): Int {
                TODO("Not yet implemented")
            }
        }
        
        fun main() {
            Day{{day}}(InputReader.inputAsString({{year}}, {{day}})).apply {
                println(solve1())
                println(solve2())
            }
        }

    """.trimIndent()

    private val TEMPLATE_TEST = """
        package aoc{{year}}

        import io.kotest.matchers.shouldBe
        import org.junit.jupiter.api.Test
        import utils.BaseDayTest

        class Day{{day}}Test : BaseDayTest({{year}}, {{day}}) {
            @Test
            fun solve1() {
                Day{{day}}(input).solve1() shouldBe 0
            }

            @Test
            fun solve2() {
                Day{{day}}(input).solve2() shouldBe 0
            }
        }
        
    """.trimIndent()

    @TaskAction
    fun generate() {
        require(day != null) { "day is required: $day" }
        val day = "%02d".format(day!!.toInt())
        val year: String = year ?: Calendar.getInstance().get(Calendar.YEAR).toString()

        File("src/main/kotlin/aoc$year/Day$day.kt").writeText(
            TEMPLATE_DAY
                .replace("{{year}}", year)
                .replace("{{day}}", day)
        )
        File("src/test/kotlin/aoc$year/Day${day}Test.kt").writeText(
            TEMPLATE_TEST
                .replace("{{year}}", year)
                .replace("{{day}}", day)
        )
        listOf(
            "src/main/resources/$year/Day$day/input.txt",
            "src/test/resources/$year/Day$day/input.txt",
        ).forEach { File(it) }
    }
}

tasks.register<GenerateTask>("generateAoC")
