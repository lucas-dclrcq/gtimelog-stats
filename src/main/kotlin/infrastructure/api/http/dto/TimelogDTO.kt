package infrastructure.api.http.dto

import com.fasterxml.jackson.annotation.JsonProperty
import domain.Kind
import domain.Task
import domain.Timelog
import java.time.LocalDateTime

data class TimelogDTO(
    @field:JsonProperty("")
    val tasks: List<TaskDTO>? = null
) {
    companion object {
        fun from(timelog: Timelog): TimelogDTO {
            return TimelogDTO(timelog.tasks.map(TaskDTO.Companion::from))
        }
    }
}

data class TaskDTO(
    @field:JsonProperty("timestamp")
    val timestamp: LocalDateTime? = null,
    @field:JsonProperty("category")
    val category: String? = null,
    @field:JsonProperty("description")
    val description: String? = null,
    @field:JsonProperty("tags")
    val tags: List<String>? = null,
    @field:JsonProperty("kind")
    val kind: Kind? = null
) {
    companion object {
        fun from(task: Task): TaskDTO {
            return TaskDTO(
                task.timestamp,
                task.category.value,
                task.description.value,
                task.tags.map { it.value },
                task.kind
            )
        }
    }
}