package gr.jvoyatz.sportspot.presentation.home.integration

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth
import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportpot.domain.usecases.usecases.GetSportEvents
import gr.jvoyatz.sportpot.domain.usecases.usecases.SetFavoriteSportEvent
import gr.jvoyatz.sportpot.domain.usecases.usecases.SportEventsUseCases
import gr.jvoyatz.sportspot.core.common.AppDispatchers
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.database.SportEventsDao
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import gr.jvoyatz.sportspot.core.network.SportEventsApiService
import gr.jvoyatz.sportspot.core.network.dto.SportCategoryDto
import gr.jvoyatz.sportspot.core.testing.utils.MainDispatcherRule
import gr.jvoyatz.sportspot.core.testing.utils.deserializeList
import gr.jvoyatz.sportspot.core.testing.utils.loadResourceFile
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClient
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClientImpl
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClientImpl
import gr.jvoyatz.sportspot.data.sports_events.repo.SportEventRepositoryImpl
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryEntityMapper.domainToEntity
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryEntityMapper.entityToDomain
import gr.jvoyatz.sportspot.domain.model.SportCategory
import gr.jvoyatz.sportspot.presentation.home.HomeUiState
import gr.jvoyatz.sportspot.presentation.home.HomeViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelIntegrationTest{

    private lateinit var appDispatchers: AppDispatchers
    private lateinit var setFavoriteSportEvent: SetFavoriteSportEvent
    private lateinit var getSportEvents: GetSportEvents
    private lateinit var repository: SportEventsRepository
    private lateinit var dbClient: SportEventsDbClientImpl
    private lateinit var apiClient: SportEventsApiClient

    @get:Rule
    val rule: MainDispatcherRule = MainDispatcherRule()
//
//
    @RelaxedMockK
    private lateinit var api: SportEventsApiService

    @RelaxedMockK
    private lateinit var dao: SportEventsDao

    @SpyK
    private var savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this)

        apiClient = SportEventsApiClientImpl(api)
        dbClient = SportEventsDbClientImpl(dao)
        repository = SportEventRepositoryImpl(apiClient, dbClient)
        appDispatchers = object: AppDispatchers {
            override val io: CoroutineDispatcher
                get() = rule.testDispatcher
            override val main: CoroutineDispatcher
                get() = rule.testDispatcher
            override val default: CoroutineDispatcher
                get() = rule.testDispatcher
            override val unconfined: CoroutineDispatcher
                get() = rule.testDispatcher
        }

        val usecases = SportEventsUseCases(repository)
        getSportEvents = usecases.getSportEvents
        setFavoriteSportEvent = usecases.setFavoriteSportEvent
    }

//    class FakeDataSource : SportEventsDbClient {
//        private val flow = MutableSharedFlow<List<SportCategoryEntity>>()
//        suspend fun emit(value: List<SportCategoryEntity>) = flow.emit(value)
//        override fun getSportEvents(): Flow<List<SportCategoryEntity>> {
//            return flow
//        }
//
//        override suspend fun deleteSportEvents() {
//            TODO("Not yet implemented")
//        }
//
//        override suspend fun insertSportEvents(events: List<SportCategoryEntity>) {
//           emit(events)
//        }
//    }


//    @Test
//    fun `test basic initialization with no data()`() = runTest{
//        val db: FakeDataSource = FakeDataSource()
//        coEvery { api.fetchSportEvents() } coAnswers  {
//            println("here!!!!!!!!")
//            db.emit(entities!!)
//            data!!
//        }
//        coEvery { dao.selectEvents() }.returns(flowOf(listOf()))
//
//        createViewModel()
//
//        viewModel.uiState.test {
//            val item = awaitItem()
//            Truth.assertThat(item).isNotNull()
//            Truth.assertThat(item.sportsEvents).isNotNull()
//            println("asdfdfasd ${item.sportsEvents!!.size}")
////            Truth.assertThat(item.sportsEvents!!.size).isEqualTo(data!!.size)
////            Truth.assertThat(item.sportsEvents!!.size).isEqualTo(entities!!.size)
//            coEvery { api.fetchSportEvents() }
//            coEvery { dao.selectEvents() }
//        }
//
//        val values = mutableListOf<List<SportCategoryEntity>>()
//        backgroundScope.launch (rule.testDispatcher){
//            db.getSportEvents().toList(values)
//        }
//        db.emit(entities!!)
//        println(values)
//    }


    @Test
    fun `get sport events from db`() = runTest{
        coEvery { api.fetchSportEvents() } returns  listOf<SportCategoryDto>()
        coEvery { dao.selectEvents() } returns flowOf(entities!!)
        createViewModel()

        viewModel.uiState.test {
            val item = awaitItem()
            Truth.assertThat(item).isNotNull()
            Truth.assertThat(item.sportsEvents).isNotNull()
            Truth.assertThat(item.sportsEvents!!.size).isEqualTo(entities!!.size)
            coVerify (exactly = 0) { api.fetchSportEvents() }
            coEvery { dao.selectEvents() }
        }
    }

    private fun createViewModel(){
        viewModel = HomeViewModel(
            getSportEvents,
            setFavoriteSportEvent,
            savedStateHandle,
            appDispatchers,
            HomeUiState()
        )
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