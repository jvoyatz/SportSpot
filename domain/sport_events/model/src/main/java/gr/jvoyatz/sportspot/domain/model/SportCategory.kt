package gr.jvoyatz.sportspot.domain.model

/**
 * Events scheduled for a certain sport
 */
data class SportCategory(
    val id: String,
    val name: String,
    val events: List<FavorableSportEvent> = listOf()
)