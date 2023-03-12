package gr.jvoyatz.sportspot.data.sports_events.repo.fake

import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.core.network.dto.SportEventsDto
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient

class FakeApiClient: SportEventsApiClient {

    var wasCalled = false
    lateinit var apiResponse: ApiResponse<List<SportEventsDto>, String>

    override suspend fun getSportEvents(): ApiResponse<List<SportEventsDto>, String> {
        wasCalled = true
        return apiResponse
    }

}