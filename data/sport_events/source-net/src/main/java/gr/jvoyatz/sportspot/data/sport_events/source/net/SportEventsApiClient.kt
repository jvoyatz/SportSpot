package gr.jvoyatz.sportspot.data.sport_events.source.net

import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.data.sport_events.source.net.dto.SportEventDto
import gr.jvoyatz.sportspot.data.sport_events.source.net.dto.SportEventsDtoList

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