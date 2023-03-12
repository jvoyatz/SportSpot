package gr.jvoyatz.sportspot.presentation.home.models

import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvents


fun SportEvent.toUiModel() = HomeSportEvent(
    id = id,
    name = name,
    sportId = sportId,
    description = description,
    startDateTimeStamp = startDateTimeStamp
)

fun FavorableSportEvent.toUiModel() = HomeFavorableSportEvent(
    isFavorite = isFavorite,
    event = event.toUiModel()
)

fun SportEvents.toUiModel() = HomeSportEvents(
    id = id,
    name = name,
    events = events.map { it.toUiModel() }
)