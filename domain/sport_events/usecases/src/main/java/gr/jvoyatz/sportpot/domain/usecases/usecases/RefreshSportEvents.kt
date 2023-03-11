package gr.jvoyatz.sportpot.domain.usecases.usecases

/**
 * Calls the method exposed by the repository to refresh SportEvents
 */
fun interface RefreshSportEvents: suspend () -> Unit
