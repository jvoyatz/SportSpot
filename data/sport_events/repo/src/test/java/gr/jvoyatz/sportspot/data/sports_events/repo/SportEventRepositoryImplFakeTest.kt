package gr.jvoyatz.sportspot.data.sports_events.repo

import app.cash.turbine.test
import com.google.common.truth.Truth
import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.core.testing.utils.Utils
import gr.jvoyatz.sportspot.data.sports_events.repo.fake.FakeApiClient
import gr.jvoyatz.sportspot.data.sports_events.repo.fake.FakeDbClient
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.BaseTest
import gr.jvoyatz.sportspot.domain.model.SportEventException
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SportEventRepositoryImplFakeTest: BaseTest(){

    @get: Rule
    val dispatcherRule = Utils.coroutineRule

    private val apiClient = FakeApiClient()
    private val dbClient = FakeDbClient()
    private lateinit var repository: SportEventsRepository

    @Before
    fun setup(){
        repository = SportEventRepositoryImpl(apiClient, dbClient)
    }

    @Test
    fun `happy path sports event data`() = runTest{

        val entities = entities!!

        repository.getSportEvents().test {
            dbClient.emit(entities)
            val data = awaitItem()
            Truth.assertThat(data.size).isEqualTo(entities.size)
        }

        Truth.assertThat(dbClient.getSportEventsCalled).isTrue()
    }

//    @Test
//    fun `happy path, sports event data - empty`() = runTest{
//        val entities = listOf<SportEventsEntity>()
//        apiClient.apiResponse = ApiResponse.success(data!!)
//
//        repository.getSportEvents().test {
//
//            println(this.awaitItem())
//            // Truth.assertThat(data.size).isEqualTo(entities.size)
//        }
//
////        dbClient.emit(entities)
//    }

    @Test
    fun `refresh data successfully`() = runTest{
        //given
        apiClient.apiResponse = ApiResponse.success(data!!)

        //when
        repository.getSportEvents().test {
            repository.refreshSportEvents()
            val items = awaitItem()
            Truth.assertThat(items.size).isEqualTo(data!!.size)
        }

        //then
        Truth.assertThat(apiClient.wasCalled).isTrue()
        Truth.assertThat(dbClient.getSportEventsCalled).isTrue()
    }

    @Test
    fun `refresh data no network`() = runTest{
        //given
        apiClient.apiResponse = ApiResponse.networkError()

        //when
        try {
            repository.getSportEvents().test {
                repository.refreshSportEvents()
                val items = awaitItem()
                Truth.assertThat(items.size).isEqualTo(0)
            }
        }catch (e: Exception){
            Truth.assertThat(e).isInstanceOf(SportEventException.NetworkException::class.java)
        }

        //then
        Truth.assertThat(apiClient.wasCalled).isTrue()
        Truth.assertThat(dbClient.insertSportEventsCalled).isFalse()
    }

    @Test
    fun `refresh data http error`() = runTest{
        //given
        apiClient.apiResponse = ApiResponse.httpError()

        //when
        try {
            repository.getSportEvents().test {
                repository.refreshSportEvents()
                val items = awaitItem()
                Truth.assertThat(items.size).isEqualTo(0)
            }
        }catch (e: Exception){
            Truth.assertThat(e).isInstanceOf(SportEventException.HttpException::class.java)
        }

        //then
        Truth.assertThat(apiClient.wasCalled).isTrue()
        Truth.assertThat(dbClient.insertSportEventsCalled).isFalse()
    }

    @Test
    fun `refresh data unknown error`() = runTest{
        //given
        apiClient.apiResponse = ApiResponse.unknownError()

        //when
        try {
            repository.getSportEvents().test {
                repository.refreshSportEvents()
                val items = awaitItem()
                Truth.assertThat(items.size).isEqualTo(0)
            }
        }catch (e: Exception){
            Truth.assertThat(e).isInstanceOf(SportEventException.UnknownException::class.java)
        }

        //then
        Truth.assertThat(apiClient.wasCalled).isTrue()
        Truth.assertThat(dbClient.insertSportEventsCalled).isFalse()
    }

    @Test
    fun `get sport event by random id returns null`()= runTest{
        //given
        val entities = entities!!

        //when
        repository.getSportEventById("foot", 11).test {
            dbClient.emit(entities)
            val event = awaitItem()
            Truth.assertThat(event).isNull()
        }
    }

    @Test
    fun `get sport event by random id returns non null`()= runTest{
        //given
        val entities = entities!!

        //when
        repository.getSportEventById("FOOT", 29135390).test {
            dbClient.emit(entities)
            val event = awaitItem()
            Truth.assertThat(event).isNotNull()
        }
    }
}

