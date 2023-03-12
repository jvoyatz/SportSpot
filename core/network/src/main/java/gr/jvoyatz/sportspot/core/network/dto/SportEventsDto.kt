package gr.jvoyatz.sportspot.core.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportEventsDto(
    @Json(name = "d")
    val name: String = "", // SOCCER
    @Json(name = "e")
    val events: List<SportEventDto> = listOf(),
    @Json(name = "i")
    val id: String = "" // FOOT
)