package gr.jvoyatz.sportpot.domain.usecases.usecases

import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.core.common.ResultData
import gr.jvoyatz.sportspot.core.common.asResult
import gr.jvoyatz.sportspot.domain.model.SportEvents
import kotlinx.coroutines.flow.Flow

/**
 * SAM interface that returns a list of SportEvents wrapped in ResultData container class
 */
fun interface GetSportEvents: () -> Flow<ResultData<List<SportEvents>>>

/**
 * Here we use a method to bind the method signature to the SAM interface declaration
 */
 fun bindMethodSignatureToGetSportEventInterface(repository: SportEventsRepository): GetSportEvents =
    GetSportEvents {
        repository.getSportEvents().asResult()
    }