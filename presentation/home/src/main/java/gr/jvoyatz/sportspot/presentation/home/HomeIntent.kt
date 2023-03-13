package gr.jvoyatz.sportspot.presentation.home

import gr.jvoyatz.sportspot.presentation.home.models.HomeSportEvent

/**
 * Represents user's actions while browsing in Home screen
 */
sealed interface HomeIntent {

    object OnFavoriteActionConsumed: HomeIntent

    object GetSportEvents: HomeIntent
    /**
     * fetches new sport events
     */
    object RefreshSportEvents: HomeIntent

    /**
     * Mark a sport event as favorite
     */
    class OnFavoriteSportEvent(val event: HomeSportEvent, val isFavorite: Boolean) : HomeIntent
}