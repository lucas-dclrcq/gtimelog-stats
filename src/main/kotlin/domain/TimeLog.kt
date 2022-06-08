package domain

import java.time.LocalDateTime

data class Timelog(val tasks: List<Task>) {
    fun append(task: Task): Timelog {
        return Timelog(tasks.plus(task))
    }
}

data class Task(val timestamp: LocalDateTime, val category: Category, val description: Description, val tags: List<Tag>)

data class Description(val value: String)

data class Category(val value: String) {
    companion object {
        fun emptyCategory(): Category = Category("")
    }
}

data class Tag(val value: String)