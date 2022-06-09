package infrastructure.api.http.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Response(
    @field:JsonProperty("timelog")
    val timelogDTO: TimelogDTO? = null
)
