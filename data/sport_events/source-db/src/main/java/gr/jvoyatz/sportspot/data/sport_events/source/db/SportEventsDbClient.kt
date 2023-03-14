package gr.jvoyatz.sportspot.data.sport_events.source.db

import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Defines the methods used to get data for the scheduled events from the local cache
 */
interface SportEventsDbClient {

    /**
     * Returns all the events found in the local cache
     */
    /*suspend*/ fun getSportEvents(): Flow<List<SportCategoryEntity>>

    /**
     * Deletes all the events
     */
    suspend fun deleteSportEvents()

    /**
     * Inserts newly fetched events into the db
     */
    suspend fun insertSportEvents(events: List<SportCategoryEntity>)
}