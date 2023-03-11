package gr.jvoyatz.sportspot.core.network

import gr.jvoyatz.sportspot.core.network.dto.SportEventDto
import retrofit2.http.GET

interface SportEventsApiService {
    @GET("sports")
    suspend fun fetchSportEvents(): List<SportEventDto>
}