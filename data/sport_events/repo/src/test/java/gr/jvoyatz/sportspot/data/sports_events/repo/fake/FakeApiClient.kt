package gr.jvoyatz.sportspot.data.sports_events.repo.fake

import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.core.network.dto.SportCategoryDto
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient

class FakeApiClient: SportEventsApiClient {

    var wasCalled = false
    lateinit var apiResponse: ApiResponse<List<SportCategoryDto>, String>

    override suspend fun getSportEvents(): ApiResponse<List<SportCategoryDto>, String> {
        wasCalled = true
        return apiResponse
    }

}