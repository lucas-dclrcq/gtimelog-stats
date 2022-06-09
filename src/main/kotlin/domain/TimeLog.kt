package domain

import java.time.LocalDateTime

data class Timelog(val tasks: List<Task>) {
    fun append(task: Task): Timelog {
        return Timelog(tasks.plus(task))
    }
}

enum class Kind {
    WORK,
    NON_WORK,
    OMITTED
}

data class Task(val timestamp: LocalDateTime, val category: Category, val description: Description, val tags: List<Tag>, val kind: Kind)

data class Description(val value: String){
    companion object {
        fun emptyDescription() = Description("")
    }
}

data class Category(val value: String) {
    companion object {
        fun emptyCategory() = Category("")
    }
}

data class Tag(val value: String)