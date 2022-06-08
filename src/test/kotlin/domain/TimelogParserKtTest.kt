package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TimelogParserKtTest {
    @Test
    fun `should parse basic task`() {
        val rawTask = "2022-06-07 09:15: test"

        val timelog = parseTimeLog(listOf(rawTask))

        assertThat(timelog.tasks).hasSize(1)
        assertThat(timelog.tasks.first()).isEqualTo(
            Task(
                LocalDateTime.of(2022, 6, 7, 9, 15),
                Category.emptyCategory(),
                Description("test"),
                emptyList()
            )
        )

    }

    @Test
    fun `should parse task with category`() {
        val rawTask = "2022-06-07 09:15: category: test"

        val timelog = parseTimeLog(listOf(rawTask))

        assertThat(timelog.tasks).hasSize(1)
        assertThat(timelog.tasks.first()).isEqualTo(
            Task(
                LocalDateTime.of(2022, 6, 7, 9, 15),
                Category("category"),
                Description("test"),
                emptyList()
            )
        )
    }

    @Test
    fun `should parse task with category and tags`() {
        val rawTask = "2022-06-07 09:15: category: test -- tag1 tag2"

        val timelog = parseTimeLog(listOf(rawTask))

        assertThat(timelog.tasks).hasSize(1)
        assertThat(timelog.tasks.first()).isEqualTo(
            Task(
                LocalDateTime.of(2022, 6, 7, 9, 15),
                Category("category"),
                Description("test"),
                listOf(Tag("tag1"), Tag("tag2"))
            )
        )
    }

    @Test
    fun `should parse task with tags but no category`() {
        val rawTask = "2022-06-07 09:15: test -- tag1 tag2"

        val timelog = parseTimeLog(listOf(rawTask))

        assertThat(timelog.tasks).hasSize(1)
        assertThat(timelog.tasks.first()).isEqualTo(
            Task(
                LocalDateTime.of(2022, 6, 7, 9, 15),
                Category.emptyCategory(),
                Description("test"),
                listOf(Tag("tag1"), Tag("tag2"))
            )
        )
    }
}