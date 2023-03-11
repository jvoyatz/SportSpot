package gr.jvoyatz.sportspot.presentation.home

import android.os.Parcelable
import gr.jvoyatz.sportspot.domain.model.SportEvents
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportEvents
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeUiState(
    val isLoading: Boolean = false,
    val sportsEvents: List<HomeSportEvents>? = null,
    val isError: Boolean = false
): Parcelable{

    sealed interface PrepareHomeUiState{
        data class OnSportsEventsFetched(val events: List<SportEvents>): PrepareHomeUiState
        data class Error(val throwable: Throwable? = null): PrepareHomeUiState
    }
}