package gr.jvoyatz.sportspot.data.sports_events.repo.fake

import gr.jvoyatz.sportspot.core.database.entities.SportEventsEntity
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeDbClient: SportEventsDbClient {

    var getSportEventsCalled = false
    var insertSportEventsCalled = false

     val fakeFlow: MutableSharedFlow<List<SportEventsEntity>> = MutableSharedFlow()


    suspend fun emit(value: List<SportEventsEntity>){
        fakeFlow.emit(value)
    }
    override fun getSportEvents(): Flow<List<SportEventsEntity>> {
        getSportEventsCalled = true
        return fakeFlow
    }

    override suspend fun deleteSportEvents() {
    }

    override suspend fun insertSportEvents(events: List<SportEventsEntity>) {
        emit(events)
        insertSportEventsCalled = true
    }
}