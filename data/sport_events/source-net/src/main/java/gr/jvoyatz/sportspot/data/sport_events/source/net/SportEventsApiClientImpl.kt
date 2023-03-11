package gr.jvoyatz.sportspot.data.sport_events.source.net

import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.core.network.SportEventsApiService
import gr.jvoyatz.sportspot.core.network.config.safeRawApiCall
import gr.jvoyatz.sportspot.core.network.dto.SportEventDto
import javax.inject.Inject

/**
 * Implements the [SportEventsApiClient] interface.
 * Takes as a param through the constructor it's only dependency,
 * the [SportEventsApiService].
 *
 * It is responsible of providing a way to get the data from the server.
 */
class SportEventsApiClientImpl @Inject constructor(
    private val sportsApi: SportEventsApiService
) : SportEventsApiClient {

    /**
     * Wrapping the result into an ApiResponse object,
     * using [safeRawApiCall]
     */
    override suspend fun getSportEvents(): ApiResponse<List<SportEventDto>, String> {
        return safeRawApiCall {
            sportsApi.fetchSportEvents()
        }
    }
}