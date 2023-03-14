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
import gr.jvoyatz.sportspot.domain.model.SportEventException
import gr.jvoyatz.sportspot.domain.model.SportEvents
import kotlinx.coroutines.flow.*
import javax.inject.Inject

//keep it for debugging
////                val sorted = list.flatMap { it.events }.map { fav -> fav.event }
////                    .map { it.startDateTimeStamp }.sortedDescending()
////                Timber.d("sorted list of longs ${sorted.joinToString { "$it," }}")
class SportEventRepositoryImpl @Inject constructor(
    private val apiClient: SportEventsApiClient,
    private val dbClient: SportEventsDbClient,
  //  private val appDispatchers: AppDispatchers,
): SportEventsRepository {

    /**
     * It executes a query in the `in memory` db, to get all the cached data.
     * If no data found, then it calls the refreshSportEvents method to get fresh data
     */
    override /*suspend*/ fun getSportEvents(): Flow<List<SportEvents>> {
        return dbClient.getSportEvents()
            .map {entities ->
                entities.mapList { it.entityToDomain() }
            }
            .map { sportCategories ->
                getFilteredAndSortedEvents(sportCategories)
            }
            .distinctUntilChanged()
            .onEach {
                if(it.isEmpty()){
                    refreshSportEvents()
                }
            }.catch { throwable ->
                with(throwable){
                    this.asSportEventException().also { throw it }
                }
            }
    }

    /**
     * Sort the events found for each sport category
     * based on if they have been marked as favorite or not
     * as well as their date.
     */
    private fun getFilteredAndSortedEvents(sportCategory: List<SportEvents>): List<SportEvents> {
        return sportCategory.map { category ->
            val favEventsMap = category.events.groupBy { it.isFavorite }.toMap()

            val favorites = favEventsMap[true]?.sortedBy { it.event.startDateTimeStamp } ?: listOf()
            val nonFavorites = favEventsMap[false]?.sortedBy { it.event.startDateTimeStamp } ?: listOf()

            val combinedLists = favorites + nonFavorites
            category.copy(events = combinedLists)
        }.toList()
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
                if(this.isNotEmpty()) dbClient.insertSportEvents(this)
            }
            .onSuspendedError {
                this.dtoToDomain().also { throw it }
            }
    }

    override suspend fun getSportEventById(sportId: String, id: Long): Flow<FavorableSportEvent?> {
        return getSportEvents()
            .map {list ->
                list.firstOrNull {
                    it.id == sportId
                }
            }
            .map { sportEvents ->
                sportEvents?.events?.firstOrNull {
                    it.event.id == id
                }
            }
            //.flowOn(appDispatchers.io)
    }

    /**
     * Gets the cached events, finds if there is an existing
     * event with this id ana marks it as favorite
     */
    override suspend fun markSportEventAsFavorite(sportId: String, id: Long, asFavorite: Boolean) {

        val list = dbClient.getSportEvents().first()


        val entity = list.firstOrNull {
            println("${it.id} -- $sportId")
            it.id == sportId
        }?.events?.firstOrNull {
            println("${it.id} -- $id")
            it.id == id
        }

        if (entity != null && entity.isFavorite != asFavorite) {
            entity.isFavorite = asFavorite
        } else {
            throw SportEventException.ErrorException("did not found an entity")
        }
        if (list.isNotEmpty()) {
            dbClient.insertSportEvents(list)
        }
    }
}