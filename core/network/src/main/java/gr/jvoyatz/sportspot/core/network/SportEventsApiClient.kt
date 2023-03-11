package gr.jvoyatz.sportspot.core.network

import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.core.network.dto.SportEventDto

/**
 * Defines the methods used to fetch data for the near future Sport Events
 */
interface SportEventsApiClient {
    /**
     * Fetches the sports events from the remote service and after parsing the
     * response returned, encapsulates the data into an ApiResponse object.
     */
    suspend fun getSportEvents():ApiResponse<List<SportEventDto>, String>
}