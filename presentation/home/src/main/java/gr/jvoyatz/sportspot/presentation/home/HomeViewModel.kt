package gr.jvoyatz.sportspot.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.jvoyatz.sportpot.domain.usecases.usecases.GetSportEvents
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.common.onError
import gr.jvoyatz.sportspot.core.common.onSuccess
import gr.jvoyatz.sportspot.presentation.home.HomeUiState.PrepareHomeUiState
import gr.jvoyatz.sportspot.presentation.home.HomeUiState.PrepareHomeUiState.OnSportsEventsFetched
import gr.jvoyatz.sportspot.presentation.home.models.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val SAVED_HOME_FRAGMENT_UI_STATE = "sportsSpotEventsHomeFragmentUiState"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSportEvents: GetSportEvents,
    private val savedStateHandle: SavedStateHandle,
    initialUiState: HomeUiState
): ViewModel(){

    private val sportEventsFlow = MutableSharedFlow<HomeIntents>()
    val uiState = savedStateHandle.getStateFlow(SAVED_HOME_FRAGMENT_UI_STATE, initialUiState)
    private val homeIntentsFlow = MutableSharedFlow<HomeIntents>()

    init {
        Timber.d("viewmodel intialized $getSportEvents")

        viewModelScope.launch {
            homeIntentsFlow.flatMapMerge { mapHomeIntentToUseCase(it) }
            .scan(uiState.value) { a, b ->
                produceUiState(a, b)
            }
            .catch { Timber.e("caught an error $it") }
            .collect{
                Timber.d("collected new ui state $it")
                savedStateHandle[SAVED_HOME_FRAGMENT_UI_STATE] = it
            }
        }
    }

    private fun produceUiState(prevState: HomeUiState, newState: PrepareHomeUiState): HomeUiState =
        when(newState){
            is OnSportsEventsFetched -> prevState.copy(
                isLoading = false,
                isError = false,
                sportsEvents = newState.events
            )
            is PrepareHomeUiState.Loading -> prevState.copy(
                isLoading = true,
                isError = false,
                sportsEvents = null
            )
            is PrepareHomeUiState.Error -> prevState.copy(
                isLoading = false,
                isError = true,
                sportsEvents = null
            )
        }

    init {
        viewModelScope.launch {
            delay(5000)
            Timber.d("now sending intent")
            onHomeIntent(HomeIntents.GetSportEvents)
        }
    }


    private fun onHomeIntent(newIntent: HomeIntents){
        viewModelScope.launch{
            homeIntentsFlow.emit(newIntent)
        }
    }
    private fun mapHomeIntentToUseCase(intent: HomeIntents): Flow<PrepareHomeUiState> {
        return when(intent){
            is HomeIntents.GetSportEvents -> getEvents()
            else -> emptyFlow()
        }
    }

    private fun getEvents(): Flow<PrepareHomeUiState> = flow {
        getSportEvents()
            .onStart {
                Timber.d("emitting loading")
                emit(PrepareHomeUiState.Loading)
            }
            .collect { result ->
                Timber.d("Thread ${Thread.currentThread()}")
                result.onSuccess { events ->
                    Timber.d("on success emitting")
                    val uiList = events.mapList { it.toUiModel() }
                    emit(OnSportsEventsFetched(uiList))
                }
                .onError {
                    Timber.d("on error emiittting")
                }

                Timber.d("final comannd")
            }
    }.flowOn(Dispatchers.Default)
}