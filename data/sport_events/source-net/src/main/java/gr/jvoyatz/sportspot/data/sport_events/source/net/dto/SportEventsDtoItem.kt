package gr.jvoyatz.sportspot.data.sport_events.source.net.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportEventsDtoItem(
    @Json(name = "d")
    val name: String = "", // SOCCER
    @Json(name = "e")
    val events: List<SportEventDto> = listOf(),
    @Json(name = "i")
    val id: String = "" // FOOT
)