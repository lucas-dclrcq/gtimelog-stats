package domain

interface TimelogRepository {
    fun get(): Timelog
}