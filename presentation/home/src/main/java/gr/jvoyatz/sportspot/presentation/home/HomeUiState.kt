package gr.jvoyatz.sportspot.presentation.home

import android.os.Parcelable
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportEvents
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeUiState(
    val isLoading: Boolean = false,
    val sportsEvents: List<HomeSportEvents>? = null,
    val isError: Boolean = false
): Parcelable{

    sealed interface PrepareHomeUiState{
        object Loading: PrepareHomeUiState
        data class OnSportsEventsSuccess(val events: List<HomeSportEvents>): PrepareHomeUiState
        data class OnSportEventsError(val throwable: Throwable? = null): PrepareHomeUiState
    }
}