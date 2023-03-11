package gr.jvoyatz.sportspot.domain.model.utils

import gr.jvoyatz.sportspot.domain.model.SportEvent
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvents

object MockData {
    val mockEvent = SportEvent(1, "name", "sportId", null, startDateTimeStamp = 100234L)
    val mockEmptySportsEvent = SportEvents("sportId", "sportName")
    val mockNonEmptySportsEvent = SportEvents("sportId", "sportName",
        listOf(
            FavorableSportEvent(mockEvent, false),
            FavorableSportEvent(mockEvent, true)))
}