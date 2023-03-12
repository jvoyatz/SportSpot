package gr.jvoyatz.sportspot.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.jvoyatz.sportpot.domain.usecases.usecases.GetSportEvents
import gr.jvoyatz.sportspot.core.common.AppDispatchers
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.common.onError
import gr.jvoyatz.sportspot.core.common.onSuccess
import gr.jvoyatz.sportspot.presentation.home.HomeUiState.PrepareHomeUiState
import gr.jvoyatz.sportspot.presentation.home.HomeUiState.PrepareHomeUiState.OnSportsEventsSuccess
import gr.jvoyatz.sportspot.presentation.home.models.toUiModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val SAVED_HOME_FRAGMENT_UI_STATE = "sportsSpotEventsHomeFragmentUiState"
@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSportEvents: GetSportEvents,
    private val savedStateHandle: SavedStateHandle,
    private val dispatchers: AppDispatchers,
    initialUiState: HomeUiState
): ViewModel(){

    val uiState = savedStateHandle.getStateFlow(SAVED_HOME_FRAGMENT_UI_STATE, initialUiState)
    private val homeIntentFlow = MutableSharedFlow<HomeIntent>()

    /**
     * When new emissions happen in the `homeIntentFlow`,
     * Flow<T>.flatMapMerge is called and transforms the emitted intent to a new
     * flow, that emits `PrepareHomeUiState`.
     * Then, `scan` is called with an initial value that of uiState.value, which, in
     * the end, will be updated accordingly after 'parsing' the second parameter of type `PrepareHomeUiState`.
     *
     * Finally, this new HomeUiState will be set in the savedStateHandle and it
     * will be emitted in the HomeFragment.
     */
    init {
        viewModelScope.launch {
            homeIntentFlow.flatMapMerge { mapHomeIntentToUseCase(it) }
            .scan(uiState.value) { a, b -> produceHomeUiState(a, b) }
            .catch { Timber.e("caught an error $it") }
            .collect{
                savedStateHandle[SAVED_HOME_FRAGMENT_UI_STATE] = it
            }
        }
    }

    init {
        viewModelScope.launch {
            delay(5000)
            Timber.d("now sending intent")
            onUserIntent(HomeIntent.GetSportEvents)
        }
    }

    /**
     * Accepts an argument which is an instance of [HomeIntent]
     * representing an action (or an intent) requested by the user.
     * This will trigger a new emission in the [homeIntentFlow].
     */
    fun onUserIntent(newIntent: HomeIntent){
        viewModelScope.launch{
            homeIntentFlow.emit(newIntent)
        }
    }

    /**
     * Gets the current state of HomeUiState, and parses the newState (PrepareHomeUiState)
     * and returns the updated HomeUiState/
     */
    private fun produceHomeUiState(prevState: HomeUiState, newState: PrepareHomeUiState): HomeUiState =
        when(newState){
            is OnSportsEventsSuccess -> prevState.copy(
                isLoading = false,
                isError = false,
                sportsEvents = newState.events
            )
            is PrepareHomeUiState.Loading -> prevState.copy(
                isLoading = true,
                isError = false,
                sportsEvents = null
            )
            is PrepareHomeUiState.OnSportEventsError -> prevState.copy(
                isLoading = false,
                isError = true,
                sportsEvents = null
            )
        }

    /**
     * Takes as a parameter a [HomeIntent] and after
     * identifying which intent passed, it calls the corresponding use case
     * `mapped` for this intent
     */
    private fun mapHomeIntentToUseCase(intent: HomeIntent): Flow<PrepareHomeUiState> {
        return when(intent){
            is HomeIntent.GetSportEvents -> getEvents()
            else -> emptyFlow()
        }
    }

    /**
     * This method invokes the [getSportEvents] use case to get all the data for
     * the sport events, fetched by the api.
     *
     * It collects the flow returned by the use case invocation and returns a new one
     * with a newly produced [PrepareHomeUiState] object.
     */
    private fun getEvents(): Flow<PrepareHomeUiState> = flow {
        getSportEvents()
            .onStart { emit(PrepareHomeUiState.Loading) }
            .collect { result ->
                Timber.d("Thread ${Thread.currentThread()}")
                result
                    .onSuccess { events ->
                        with(events.mapList { it.toUiModel() }) {
                            emit(OnSportsEventsSuccess(this))
                        }
                    }
                    .onError { emit(PrepareHomeUiState.OnSportEventsError(it)) }
            }
    }.flowOn(dispatchers.default)
}