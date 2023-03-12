package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.DtoMapper
import gr.jvoyatz.sportspot.core.network.ApiError
import gr.jvoyatz.sportspot.core.network.HttpError
import gr.jvoyatz.sportspot.core.network.NetworkError
import gr.jvoyatz.sportspot.domain.model.SportEventException

private const val notProvidedErrorMsg = "Not Provided"

object SportEventDtoExceptionMapper : DtoMapper<ApiError<*, *>, SportEventException> {
    override fun ApiError<*, *>.dtoToDomain(): SportEventException {


        return when(this){
            is HttpError -> SportEventException.HttpException(this.errorBody?.toString() ?: notProvidedErrorMsg)
            is NetworkError -> SportEventException.NetworkException(this.error?.message ?: notProvidedErrorMsg)
            is gr.jvoyatz.sportspot.core.network.UnknownError -> SportEventException.UnknownException(this.error?.message ?: notProvidedErrorMsg)
            else -> SportEventException.UnknownException(notProvidedErrorMsg)
        }
    }

    override fun SportEventException.domainToDto(): ApiError<*, *> {
        TODO("Not yet implemented")
    }
}

fun Throwable.asSportEventException(): SportEventException {
    if(this !is SportEventException) {
        return SportEventException.ErrorException(this.message ?: notProvidedErrorMsg)
    }
    return this
}