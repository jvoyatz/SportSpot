package gr.jvoyatz.sportspot.data.sports_events.repo

import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClient
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportEventRepositoryImpl @Inject constructor(
    private val apiClient: SportEventsApiClient,
    private val dbClient: SportEventsDbClient
): SportEventsRepository {

    override suspend fun getSportEvents(): Flow<List<gr.jvoyatz.sportspot.domain.model.SportEvents>> {
        return flow {
            emit(listOf())
        }
    }

    override suspend fun refreshSportEvents() {
        TODO("Not yet implemented")
    }

    override suspend fun getSportEventById(id: Long): Flow<gr.jvoyatz.sportspot.domain.model.SportEvent> {
        TODO("Not yet implemented")
    }
}