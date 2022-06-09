package infrastructure.api.http.dto

import domain.*
import domain.Kind.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class TimelogDTOTest {
    @Test
    internal fun `should map timelog to DTO`() {
        val timelog = Timelog(
            listOf(
                Task(
                    LocalDateTime.of(2022, 3, 3, 3, 3),
                    Category("cat"),
                    Description("desc"),
                    listOf(Tag("tag1"), Tag("tag2")),
                    WORK
                )
            )
        )

        val dto = TimelogDTO.from(timelog)

        assertThat(dto)
            .isEqualTo(
                TimelogDTO(
                    listOf(
                        TaskDTO(
                            LocalDateTime.of(2022, 3, 3, 3, 3),
                            "cat",

                            "desc",
                            listOf("tag1", "tag2")
                        )
                    )
                )
            )
    }
}