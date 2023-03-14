package gr.jvoyatz.sportspot.presentation.home.models

import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvent
import gr.jvoyatz.sportspot.domain.model.SportCategory
import gr.jvoyatz.sportspot.presentation.home.R


fun SportEvent.toUiModel() = HomeSportEvent(
    id = id,
    name = name,
    sportId = sportId,
    description = description,
    startDateTimeStamp = startDateTimeStamp
)

fun FavorableSportEvent.toUiModel(onFavoriteAction: (HomeSportEvent, Boolean) -> Unit) = HomeFavorableSportEvent(
    isFavorite = isFavorite,
    event = event.toUiModel()
).apply {
    this.onFavoriteAction = onFavoriteAction
}

fun SportCategory.toUiModel(onFavoriteAction: (HomeSportEvent, Boolean) -> Unit) = HomeSportCategory(
    id = id,
    name = name,
    events = events.map { it.toUiModel(onFavoriteAction) },
    sportIdIconsMap[id] ?: NOT_KNOWN_SPORT
)

fun HomeSportEvent.toDomainModel() = SportEvent(
    id, name, sportId, description, startDateTimeStamp
)

private const val NOT_KNOWN_SPORT = -1
private val sportIdIconsMap = mapOf(
    "FOOT" to R.drawable.sport_football_2,
    "BASK" to R.drawable.sport_basketball,
    "TENN" to R.drawable.sport_tennis,
    "TABL" to R.drawable.sport_ping_pong,
    "VOLL" to R.drawable.sport_volley,
    "ESPS" to R.drawable.sport_esports,
    "ICEH" to R.drawable.sport_ice_hockey,
    "HAND" to R.drawable.sport_handball,
    "SNOO" to R.drawable.sport_snooker,
    "BADM" to R.drawable.sport_badminton,
    "BCHV" to R.drawable.sport_beach_volley
)