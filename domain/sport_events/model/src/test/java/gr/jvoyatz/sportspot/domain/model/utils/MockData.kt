package gr.jvoyatz.sportspot.domain.model.utils

import gr.jvoyatz.sportspot.domain.model.SportEvent
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportCategory

object MockData {
    val mockEvent = SportEvent(1, "name", "sportId", null, startDateTimeStamp = 100234L)
    val mockEmptySportsEvent = SportCategory("sportId", "sportName")
    val mockNonEmptySportsEvent = SportCategory("sportId", "sportName",
        listOf(
            FavorableSportEvent(mockEvent, false),
            FavorableSportEvent(mockEvent, true)))
}