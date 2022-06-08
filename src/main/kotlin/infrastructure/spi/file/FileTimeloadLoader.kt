package infrastructure.spi.file

import domain.TimeloadLoader
import domain.Timelog
import domain.parseTimeLog
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.nio.file.Files
import java.nio.file.Path
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FileTimeloadLoader(@ConfigProperty(name = "timelog.path") private val timelogFilepath: Path) : TimeloadLoader {

    override fun load(): Timelog {
        val rawTimelog = Files.readAllLines(timelogFilepath).filter { it.isNotBlank() }
        return parseTimeLog(rawTimelog)
    }
}