package gr.jvoyatz.sportspot.data.sports_events.repo

import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClient
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.BaseTest
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportCategoryEntityMapper.domainToEntity
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SportEventRepositoryImplMockTest : BaseTest() {

    @RelaxedMockK
    private lateinit var api: SportEventsApiClient

    @RelaxedMockK
    private lateinit var db: SportEventsDbClient

    private lateinit var repository: SportEventsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `refresh sport events if database is empty`() = runTest {
        // given
        repository = SportEventRepositoryImpl(api, db)
        every { db.getSportEvents() } returns flowOf(emptyList())
        coEvery { api.getSportEvents() } returns ApiResponse.success(data!!)

        //when
        repository.getSportEvents().collect()

        //then
        coVerifyOrder {
            api.getSportEvents()
            db.insertSportEvents(any())
        }
    }

    @Test
    fun `check that after refresh insert sport events called`() = runTest {
        // Given
        repository = SportEventRepositoryImpl(api, db)
        val entities = data!!.mapList { it.dtoToDomain().domainToEntity() }
        coEvery { api.getSportEvents() } returns ApiResponse.success(data!!)

        // When
        repository.refreshSportEvents()

        // Then
        coVerify { db.insertSportEvents(entities) }
    }
}