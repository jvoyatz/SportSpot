package gr.jvoyatz.sportspot.presentation.home

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth
import gr.jvoyatz.sportpot.domain.usecases.usecases.GetSportEvents
import gr.jvoyatz.sportpot.domain.usecases.usecases.SetFavoriteSportEvent
import gr.jvoyatz.sportspot.core.common.AppDispatchers
import gr.jvoyatz.sportspot.core.common.ResultData
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import gr.jvoyatz.sportspot.core.network.dto.SportCategoryDto
import gr.jvoyatz.sportspot.core.testing.utils.MainDispatcherRule
import gr.jvoyatz.sportspot.core.testing.utils.deserializeList
import gr.jvoyatz.sportspot.core.testing.utils.loadResourceFile
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryEntityMapper.domainToEntity
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryEntityMapper.entityToDomain
import gr.jvoyatz.sportspot.domain.model.SportCategory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest{

    @get:Rule
    val rule: MainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var getSportEvents: GetSportEvents

    @RelaxedMockK
    private lateinit var setFavoriteSportEvent: SetFavoriteSportEvent

    @SpyK
    private var savedStateHandle: SavedStateHandle = SavedStateHandle()

    private lateinit var appDispatchers: AppDispatchers
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        appDispatchers = object: AppDispatchers{
            override val io: CoroutineDispatcher
                get() = rule.testDispatcher
            override val main: CoroutineDispatcher
                get() = rule.testDispatcher
            override val default: CoroutineDispatcher
                get() = rule.testDispatcher
            override val unconfined: CoroutineDispatcher
                get() = rule.testDispatcher
        }
    }

    private fun getHomeViewModel() {
        viewModel = HomeViewModel(
            getSportEvents,
            setFavoriteSportEvent,
            savedStateHandle,
            appDispatchers,
            HomeUiState()
        )
    }

    @Test
    fun `creating the viewmodel, with emptyflow emission, state default`() = runTest{
        //given
        every { getSportEvents() } returns emptyFlow()
        getHomeViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            Truth.assertThat(state.isLoading).isEqualTo(true)
            Truth.assertThat(state.sportsEvents).isNull()
        }
    }

    @Test
    fun `creating the viewmodel, with success data emission, state is homeUistate with corresponding ui models `() = runTest{
        //given
        val categories = ResultData.success(domain!!)
        every { getSportEvents() } returns flowOf(categories)
        getHomeViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            Truth.assertThat(state.sportsEvents).isNotNull()
            Truth.assertThat(state.sportsEvents!!.size).isEqualTo(categories.data.size)
        }
    }

    @Test
    fun `creating the viewmodel, with error data emission, state isError true`() = runTest{
        //given
        every { getSportEvents() } returns flowOf(ResultData.error(IllegalStateException("")))
        getHomeViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            Truth.assertThat(state.isError).isTrue()
        }
    }


    @Test
    fun `ensure when passing intent of fetching new data, executes`() = runTest{
        //given
        every { getSportEvents() } returns flowOf(ResultData.success(domain!!))
        getHomeViewModel()

        //when
        viewModel.onUserIntent(HomeIntent.GetSportEvents)

        viewModel.uiState.test {
            val data = awaitItem()
            verify(exactly = 2) { getSportEvents.invoke() }
            Truth.assertThat(data.sportsEvents).isNotEmpty()
        }

    }

    companion object {
        var data: List<SportCategoryDto>? = null
        var entities: List<SportCategoryEntity>? = null
        var domain: List<SportCategory>? = null
        @JvmStatic
        @BeforeClass
        fun loadData() {
            val content = loadResourceFile("/sport_events.json")
            data = deserializeList<SportCategoryDto>(content)
            entities = data!!.mapList { it.dtoToDomain().domainToEntity() }
            domain = entities!!.map { it.entityToDomain() }
        }
    }
}