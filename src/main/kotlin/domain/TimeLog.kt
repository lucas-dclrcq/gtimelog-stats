package domain

import java.time.LocalDateTime

data class Timelog(val tasks: List<Task>) {
    fun append(task: Task): Timelog {
        return Timelog(tasks.plus(task))
    }
}

data class Range(val lower: LocalDateTime, val upper: LocalDateTime)
data class Task(val timestamp: LocalDateTime, val category: Category, val description: String, val tags: List<Tag>)
data class Category(val value: String)
data class Tag(val value: String)