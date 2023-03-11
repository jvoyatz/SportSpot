package gr.jvoyatz.sportspot.data.sport_events.source.net.api

import gr.jvoyatz.sportspot.data.sport_events.source.net.dto.SportEventsDtoList
import retrofit2.http.GET

interface SportEventsApiService {
    @GET("sports")
    fun fetchSportEvents(): SportEventsDtoList
}