package gr.jvoyatz.sportspot.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.jvoyatz.sportpot.domain.usecases.usecases.GetSportEvents
import timber.log.Timber
import javax.inject.Inject

private const val SAVED_HOME_FRAGMENT_UI_STATE = "sportsSpotEventsHomeFragmentUiState"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSportEvents: GetSportEvents,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

   // private val sportEventsFlow = MutableShare

    init {
        Timber.d("viewmodel intialized $getSportEvents")
    }
}