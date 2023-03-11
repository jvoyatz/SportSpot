package gr.jvoyatz.sportspot.data.sport_events.source.net

import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.data.sport_events.source.net.api.SportEventsApiService
import gr.jvoyatz.sportspot.data.sport_events.source.net.dto.SportEventsDtoList

/**
 * Implements the [SportEventsApiClient] interface.
 * Takes as a param through the constructor it's only dependency,
 * the [SportEventsApiService].
 *
 * It is responsible of providing a way to get the data from the server.
 */
class SportEventsApiClientImpl(
    private val sportsApi: SportEventsApiService
) : SportEventsApiClient {

    override fun getSportEvents(): ApiResponse<SportEventsDtoList, String> {
        TODO("Not yet implemented")
    }
}