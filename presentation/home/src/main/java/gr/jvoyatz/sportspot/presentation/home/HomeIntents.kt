package gr.jvoyatz.sportspot.presentation.home

/**
 * Represents user's actions while browsing in Home screen
 */
sealed interface HomeIntents {

    object GetSportEvents: HomeIntents
    /**
     * fetches new sport events
     */
    object RefreshSportEvents: HomeIntents

    /**
     * Mark a sport event as favorite
     */
    class FavoriteSportEvent(val sportEventId: Long) : HomeIntents
}