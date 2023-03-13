package gr.jvoyatz.sportspot.domain.model

/**
 * A certain event that it has been marked as favorite by the user
 */
data class FavorableSportEvent(
    val event: SportEvent,
    var isFavorite: Boolean
)