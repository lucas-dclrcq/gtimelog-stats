package domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

fun parseTimeLog(rawTasks: List<String>): Timelog =
    rawTasks.map(::parseTask).fold(Timelog(emptyList()), Timelog::append)

private fun parseTask(rawTask: String): Task {
    return TimestampParser(rawTask)
        .parseTimeStamp()
        .parseCategory()
        .parseDescription()
        .parseTags()
}

private data class TimestampParser(val rawString: String) {
    fun parseTimeStamp(): CategoryParser {
        val timestampAndRemaining = Pattern.compile(": ").split(rawString, 2)
        val timestamp = LocalDateTime.parse(timestampAndRemaining.first(), dateTimeFormatter)
        val next = timestampAndRemaining.last()

        return CategoryParser(next, timestamp)
    }
}

private data class CategoryParser(val rawString: String, val timestamp: LocalDateTime) {
    fun parseCategory(): DescriptionParser {
        val split = rawString.split(": ")
        val category = if (split.size > 1) Category(split.first()) else Category.emptyCategory()
        val next = split.last()

        return DescriptionParser(next, timestamp, category)
    }
}

data class DescriptionParser(val rawString: String, val timestamp: LocalDateTime, val category: Category) {
    fun parseDescription(): TagsParser {
        val split = Pattern.compile("-- ").split(rawString)
        val rawDescription = split.first().trim()

        val kind = when {
            rawDescription.endsWith("***") -> Kind.OMITTED
            rawDescription.endsWith("**") -> Kind.NON_WORK
            else -> Kind.WORK
        }

        val description = when (kind) {
            Kind.OMITTED -> rawDescription.removeSuffix("***")
            Kind.NON_WORK -> rawDescription.removeSuffix("**")
            else -> rawDescription
        }.trim()

        val next = if (split.size > 1)  split.last() else ""

        return TagsParser(next, timestamp, category, Description(description), kind)
    }
}

data class TagsParser(val rawString: String, val timestamp: LocalDateTime, val category: Category, val description: Description, val kind: Kind) {
    fun parseTags(): Task {
        val tags = Pattern.compile(" ").split(rawString)
            .filter { it.isNotBlank() }
            .toList().map { Tag(it) }

        return Task(timestamp, category, description, tags, kind)
    }
}

