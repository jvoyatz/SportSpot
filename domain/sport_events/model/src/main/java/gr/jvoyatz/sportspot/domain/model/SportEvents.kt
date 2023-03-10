package gr.jvoyatz.sportspot.domain.model

/**
 * Events scheduled for a certain sport
 */
data class SportEvents(
    val id: String,
    val name: String,
    val events: List<FavorableEvent> = listOf()
)