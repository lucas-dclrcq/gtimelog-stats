package infrastructure.spi.file

import domain.*
import domain.Kind.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime

internal class FileTimeloadLoaderTest() {
    @Test
    internal fun `should load and parse timelog file`(@TempDir tempdir: Path) {
        val timelogPath = tempdir.resolve("timelog.txt");
        val rawTimelog = """
            2022-06-06 08:45: arrived
            2022-06-06 13:00: pair programming -- work dev
            2022-06-06 14:00: lunch -- lunch
            2022-06-06 18:15: pair programming -- work dev
        """.trimIndent().lines()
        Files.write(timelogPath, rawTimelog)

        val timelog = FileTimeloadLoader(timelogPath).load()


        assertThat(timelog).isEqualTo(Timelog(listOf(
            Task(LocalDateTime.of(2022, 6, 6, 8, 45), Category.emptyCategory(), Description("arrived"), emptyList(), WORK),
            Task(LocalDateTime.of(2022, 6, 6, 13, 0), Category.emptyCategory(), Description("pair programming"), listOf(Tag("work"), Tag("dev")), WORK),
            Task(LocalDateTime.of(2022, 6, 6, 14, 0), Category.emptyCategory(), Description("lunch"), listOf(Tag("lunch")), WORK),
            Task(LocalDateTime.of(2022, 6, 6, 18, 15), Category.emptyCategory(), Description("pair programming"), listOf(Tag("work"), Tag("dev")), WORK),
        )))
    }

    @Test
    internal fun `should load and parse timelog file with multiple days`(@TempDir tempdir: Path) {
        val timelogPath = tempdir.resolve("timelog.txt");
        val rawTimelog = """
            2022-06-06 08:45: arrived
            2022-06-06 13:00: pair programming -- work dev
            
            2022-06-07 14:00: lunch -- lunch
            2022-06-07 18:15: pair programming -- work dev
        """.trimIndent().lines()
        Files.write(timelogPath, rawTimelog)

        val timelog = FileTimeloadLoader(timelogPath).load()


        assertThat(timelog).isEqualTo(Timelog(listOf(
            Task(LocalDateTime.of(2022, 6, 6, 8, 45), Category.emptyCategory(), Description("arrived"), emptyList(), WORK),
            Task(LocalDateTime.of(2022, 6, 6, 13, 0), Category.emptyCategory(), Description("pair programming"), listOf(Tag("work"), Tag("dev")), WORK),
            Task(LocalDateTime.of(2022, 6, 7, 14, 0), Category.emptyCategory(), Description("lunch"), listOf(Tag("lunch")), WORK),
            Task(LocalDateTime.of(2022, 6, 7, 18, 15), Category.emptyCategory(), Description("pair programming"), listOf(Tag("work"), Tag("dev")), WORK),
        )))
    }
}