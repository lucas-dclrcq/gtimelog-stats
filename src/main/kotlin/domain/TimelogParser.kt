package domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

fun parseTimeLog(rawTasks: List<String>): Timelog =
    rawTasks.map(::parseTask).fold(Timelog(emptyList()), Timelog::append)

private fun parseTask(rawTask: String): Task {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    val timestampAndRemaining = Pattern.compile(": ").split(rawTask, 2)
    val timestamp = LocalDateTime.parse(timestampAndRemaining.first(), dateTimeFormatter)

    val remaining = timestampAndRemaining.last().split(": ")

    val category = if (remaining.size > 1) Category(remaining.first()) else Category.emptyCategory()

    val remainingAgain = Pattern.compile("-- ").split(remaining.last())
    val description = Description(remainingAgain.first().trim())

    val tags = if (remainingAgain.size > 1) Pattern.compile(" ").split(remainingAgain.last()).toList().map { Tag(it) } else emptyList()

    return Task(timestamp, category, description, tags)
}
