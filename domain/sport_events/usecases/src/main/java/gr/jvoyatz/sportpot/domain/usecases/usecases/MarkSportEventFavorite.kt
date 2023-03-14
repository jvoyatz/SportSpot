package gr.jvoyatz.sportpot.domain.usecases.usecases

import gr.jvoyatz.sportspot.core.common.ResultData
import gr.jvoyatz.sportspot.domain.model.SportEvent
import kotlinx.coroutines.flow.Flow

fun interface SetFavoriteSportEvent: suspend (SportEvent, Boolean) -> Flow<ResultData<Unit>>
