package gr.jvoyatz.sportspot.data.sport_events.source.db

import gr.jvoyatz.sportspot.core.database.SportEventsDao
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SportEventsDbClientImpl @Inject constructor(
   private val dao: SportEventsDao
): SportEventsDbClient {
    override /*suspend*/ fun getSportEvents(): Flow<List<SportCategoryEntity>> {
        return dao.selectEvents()
    }

    override suspend fun deleteSportEvents() {
        dao.deleteEvents()
    }

    override suspend fun insertSportEvents(events: List<SportCategoryEntity>) {
        dao.insertEvents(events)
    }
}