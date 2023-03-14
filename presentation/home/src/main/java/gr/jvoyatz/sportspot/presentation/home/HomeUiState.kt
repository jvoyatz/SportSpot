package gr.jvoyatz.sportspot.presentation.home

import android.os.Parcelable
import gr.jvoyatz.sportspot.presentation.home.models.HomeSportCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeUiState(
    val isLoading: Boolean = false,
    val sportsEvents: List<HomeSportCategory>? = null,
    val isError: Boolean = false,
    val onFavoriteEventActionSuccess : Boolean ?= null
): Parcelable{

    sealed interface PrepareHomeUiState{
        object Empty: PrepareHomeUiState
        object Loading: PrepareHomeUiState
        data class OnSportsEventsSuccess(val events: List<HomeSportCategory>): PrepareHomeUiState
        data class OnSportEventsError(val throwable: Throwable? = null): PrepareHomeUiState
        data class OnFavoriteSportEventResult(val isSuccess: Boolean): PrepareHomeUiState
    }
}