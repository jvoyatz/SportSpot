package gr.jvoyatz.sportpot.domain.usecases.repository

import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvents
import kotlinx.coroutines.flow.Flow


/**
 * Defines the set of methods used to get data regarding the sport events
 */
interface SportEventsRepository {
    /**
     * Emits data with the new scheduled events
     */
    suspend fun getSportEvents(): Flow<List<SportEvents>>

    /**
     * Attempts to fetch new data from the remote service
     */
    suspend fun refreshSportEvents()

    /**
     * Finds a scheduled event with this id
     */
    suspend fun getSportEventById(sportId: String, id: Long): Flow<FavorableSportEvent?>
}