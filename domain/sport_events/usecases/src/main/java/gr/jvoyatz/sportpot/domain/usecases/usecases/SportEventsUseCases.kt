package gr.jvoyatz.sportpot.domain.usecases.usecases

import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.core.common.asResult
import gr.jvoyatz.sportspot.core.common.resultOf
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Acts as `facade` class, meaning that contains the several use cases defined for a specific feature of
 * the App, eg SportEvents.
 * We use this class, to avoid polluting our dagger graph with multiple dependencies (--> usecases).
 * We can get them by injecting this class and accessing the field declared in here
 * eg getSportEvents
 */
@Singleton
class SportEventsUseCases @Inject constructor(
    repository: SportEventsRepository
) {
    val getSportEvents: GetSportEvents
    val refreshSportEvents: RefreshSportEvents
    val getSportEventById: GetSportEventById
    val markSportEventAsFavorite: SetFavoriteSportEvent

    init {
        getSportEvents = bindMethodSignatureToGetSportEventInterface(repository)

        refreshSportEvents = RefreshSportEvents { repository.refreshSportEvents() }

        getSportEventById = GetSportEventById { sportId, id ->
            repository.getSportEventById(sportId, id).asResult()
        }

        markSportEventAsFavorite = SetFavoriteSportEvent { event, isFavorite ->
            flowOf(Unit)
            .map {
                resultOf {
                    repository.markSportEventAsFavorite(event.sportId, event.id, isFavorite)
                }
            }
        }
    }
}