package gr.jvoyatz.sportspot.presentation.home

/**
 * Represents user's actions while browsing in Home screen
 */
sealed interface HomeIntent {

    object GetSportEvents: HomeIntent
    /**
     * fetches new sport events
     */
    object RefreshSportEvents: HomeIntent

    /**
     * Mark a sport event as favorite
     */
    class FavoriteSportEvent(val sportEventId: Long) : HomeIntent
}