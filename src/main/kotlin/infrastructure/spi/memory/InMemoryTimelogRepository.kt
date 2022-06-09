package infrastructure.spi.memory

import domain.TimeloadLoader
import domain.Timelog
import domain.TimelogRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class InMemoryTimelogRepository(loader: TimeloadLoader): TimelogRepository {
    private val timelog: Timelog = loader.load()

    override fun get(): Timelog {
        return timelog
    }
}