package gr.jvoyatz.sportspot.core.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportEventDto(
    @Json(name = "d")
    val d: String, // Juventus FC - Paris Saint-Germain
    @Json(name = "i")
    val i: String, // 29135390
    @Json(name = "sh")
    val sh: String = "", // Juventus FC - Paris Saint-Germain
    @Json(name = "si")
    val si: String, // FOOT
    @Json(name = "tt")
    val tt: Long // 1667447160
)