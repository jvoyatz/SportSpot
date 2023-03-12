package gr.jvoyatz.sportspot.data.sport_events.source.db

import gr.jvoyatz.sportspot.core.database.SportsEventsDao
import gr.jvoyatz.sportspot.core.database.entities.SportEventsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SportEventsDbClientImpl @Inject constructor(
   private val dao: SportsEventsDao
): SportEventsDbClient {
    override suspend fun getSportEvents(): Flow<List<SportEventsEntity>> {
        return dao.selectSportEvents()
    }

    override suspend fun deleteSportEvents() {
        dao.deleteSportEvents()
    }

    override suspend fun insertSportEvents(events: List<SportEventsEntity>) {
        dao.insertSportEvents(events)
    }

    override suspend fun getSportEventById(id: Long) {
        TODO("Not yet implemented")
    }
}