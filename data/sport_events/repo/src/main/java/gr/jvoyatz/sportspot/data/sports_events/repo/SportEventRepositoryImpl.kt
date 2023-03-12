package gr.jvoyatz.sportspot.data.sports_events.repo

import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.network.config.onSuspendedError
import gr.jvoyatz.sportspot.core.network.config.onSuspendedSuccess
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClient
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventDtoExceptionMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventsDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventsEntityMapper.domainToEntity
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventsEntityMapper.entityToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.asSportEventException
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

//keep it for debugging
////                val sorted = list.flatMap { it.events }.map { fav -> fav.event }
////                    .map { it.startDateTimeStamp }.sortedDescending()
////                Timber.d("sorted list of longs ${sorted.joinToString { "$it," }}")
class SportEventRepositoryImpl @Inject constructor(
    private val apiClient: SportEventsApiClient,
    private val dbClient: SportEventsDbClient
): SportEventsRepository {

    /**
     * It executes a query in the `in memory` db, to get all the cached data.
     * If no data found, then it calls the refreshSportEvents method to get fresh data
     */
    override suspend fun getSportEvents(): Flow<List<gr.jvoyatz.sportspot.domain.model.SportEvents>> {
        return dbClient.getSportEvents()
            .map {entities ->
                val list = entities.mapList { it.entityToDomain() }
                list
            }
            .onEach {
                if(it.isEmpty()){
                    refreshSportEvents()
                }
            }.catch { throwable ->
                with(throwable){
                    Timber.e("caught an exception $this")
                    this.asSportEventException().also { throw it }
                }
            }
    }

    /**
     * Executes a network request to fetch sport events
     *  in case of a success, it saves them into the local cache after having
     *  them transformed into [gr.jvoyatz.sportspot.core.database.entities.SportEventEntity] types.
     *
     *  when an error occurred, it parses the exception to detect the type of the error
     *  and generates the appropriate exception class instance
     */
    override suspend fun refreshSportEvents() {
        apiClient.getSportEvents()
            .onSuspendedSuccess({ dtoList ->
                dtoList.mapList { it.dtoToDomain().domainToEntity() }
            }) {
                Timber.i("onSuspendedSuccessCalled with $this  in thread ${Thread.currentThread()}")
                if(this.isNotEmpty()) dbClient.insertSportEvents(this)
            }
            .onSuspendedError {
                Timber.d("getSportEvents#onError called with $this in thread ${Thread.currentThread()}")
                this.dtoToDomain().also { throw it }
            }
    }

    override suspend fun getSportEventById(sportId: String, id: Long): Flow<FavorableSportEvent?> {
        return getSportEvents()
            .map {list ->
                list.firstOrNull { it.id == sportId }
            }
            .map { sportEvents ->
                sportEvents?.events?.firstOrNull {
                    it.event.id == id
                }
            }
    }
}