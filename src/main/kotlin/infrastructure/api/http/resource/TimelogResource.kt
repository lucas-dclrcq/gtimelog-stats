package infrastructure.api.http.resource

import domain.TimelogRepository
import infrastructure.api.http.dto.TimelogDTO
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/timelog")
class TimelogResource(private val timelogRepository: TimelogRepository) {
    @GET
    fun get(): TimelogDTO {
        val timelog = timelogRepository.get()
        return TimelogDTO.from(timelog)
    }
}