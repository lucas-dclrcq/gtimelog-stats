package domain

fun parseTimeLog(rawTasks: List<String>): Timelog = rawTasks
    .map(::parseTask)
    .fold(Timelog(emptyList()), Timelog::append)

private fun parseTask(rawTask: String): Task = TODO()
