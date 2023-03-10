package gr.jvoyatz.sportspot.domain.model.utils

import gr.jvoyatz.sportspot.domain.model.Event
import gr.jvoyatz.sportspot.domain.model.FavorableEvent
import gr.jvoyatz.sportspot.domain.model.SportEvents

object MockData {
    val mockEvent = Event(1, "name", "sportId", null, startDateTimeStamp = 100234L)
    val mockEmptySportsEvent = SportEvents("sportId", "sportName")
    val mockNonEmptySportsEvent = SportEvents("sportId", "sportName",
        listOf(
            FavorableEvent(mockEvent, false),
            FavorableEvent(mockEvent, true)))
}