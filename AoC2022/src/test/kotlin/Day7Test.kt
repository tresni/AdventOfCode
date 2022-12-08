import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day7Test : BaseDayTest(7) {
    @Test
    fun `sample returns a and e`() {
        val sample = Day7(input)
        sample.rootDirectory.size() shouldBe 48381165
        sample.rootDirectory.sub.keys shouldBe setOf("a", "d")
        val aDir = sample.rootDirectory.sub["a"]!!
        aDir.files.keys shouldBe setOf("f", "g", "h.lst")
        aDir.size() shouldBe 94853
        val eDir = aDir.sub["e"]!!
        eDir.files.keys shouldBe setOf("i")
        eDir.size() shouldBe 584
        sample.rootDirectory.smallDirectories() shouldBe listOf(eDir, aDir)
        sample.rootDirectory.smallDirectoriesSize() shouldBe 95437
        sample.findCandidateDirectory() shouldBe 24933642
    }
}