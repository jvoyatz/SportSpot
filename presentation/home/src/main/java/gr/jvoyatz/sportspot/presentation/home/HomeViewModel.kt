package gr.jvoyatz.sportspot.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.jvoyatz.sportpot.domain.usecases.usecases.GetSportEvents
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSportEvents: GetSportEvents
): ViewModel(){


    init {
        Timber.d("viewmodel intialized $getSportEvents")
    }
}