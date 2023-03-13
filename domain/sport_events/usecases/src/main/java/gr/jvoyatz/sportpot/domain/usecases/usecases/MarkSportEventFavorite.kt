package gr.jvoyatz.sportpot.domain.usecases.usecases

import gr.jvoyatz.sportspot.domain.model.SportEvent
import kotlinx.coroutines.flow.Flow

fun interface MarkSportEventAsFavorite: suspend (SportEvent, Boolean) -> Flow<Unit>
