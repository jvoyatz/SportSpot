package gr.jvoyatz.sportpot.domain.usecases.usecases

import gr.jvoyatz.sportspot.core.common.ResultData
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvent
import kotlinx.coroutines.flow.Flow

/**
 * SAM interface that returns a flow containing a SportEvent if found, wrapped in ResultData class
 */
fun interface GetSportEventById: suspend (String, Long) -> Flow<ResultData<FavorableSportEvent?>>