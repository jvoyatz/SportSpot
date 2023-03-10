package gr.jvoyatz.domain.model

/**
 * A certain event that it has been marked as favorite by the user
 */
data class FavorableEvent(
    val event: Event,
    val isFavorite: Boolean
)