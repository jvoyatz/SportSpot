package gr.jvoyatz.sportspot.data.sport_events.source.net

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.core.network.*
import gr.jvoyatz.sportspot.core.testing.utils.ApiServer
import gr.jvoyatz.sportspot.core.testing.utils.loadResourceFile
import gr.jvoyatz.sportspot.data.sport_events.source.net.api.SportEventsApiService
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SportEventsApiClientImplTest: ApiServer<SportEventsApiService>(){

    lateinit var sportEventsApiClient: SportEventsApiClient

    init {
        clazz = SportEventsApiService::class.java
    }

    override fun setupServer() {
        super.setupServer()
        sportEventsApiClient = SportEventsApiClientImpl(apiClient)
    }

    @Test
    fun `fetch data successfully returns 200 with body`()= runTest{
        //given
        val sportEventsData = loadResourceFile("/sport_events.json")
        enqueue {
            success(sportEventsData)
        }

        //when
        val apiResponse = sportEventsApiClient.getSportEvents()

        //then
        Truth.assertThat(apiResponse).isNotNull()
        Truth.assertThat(apiResponse.isSuccess())
        Truth.assertThat(apiResponse.asSuccess()!!.body).isNotEmpty()
    }

    @Test
    fun `fetch data but we got a bad request as response for some reason, returns api response http error`()= runTest{
        //given
        enqueue {
            httpError()
        }

        //when
        val apiResponse = sportEventsApiClient.getSportEvents()

        //then
        Truth.assertThat(apiResponse).isNotNull()
        Truth.assertThat(apiResponse.isError())
        Truth.assertThat(apiResponse).isInstanceOf(HttpError::class.java)
        val httperror = apiResponse.asHttpError()
        Truth.assertThat(httperror!!.code).isEqualTo(400)
    }


    @Test
    fun `fetch data but we got a an ioexception as response for some reason, returns api response network error`()= runTest{
        //given
        enqueue {
            networkError()
        }

        //when
        val apiResponse = sportEventsApiClient.getSportEvents()

        //then
        Truth.assertThat(apiResponse).isNotNull()
        Truth.assertThat(apiResponse.isError())
        Truth.assertThat(apiResponse).isInstanceOf(NetworkError::class.java)
    }

    @Test
    fun `fetch data but sth happened and got an unknown error exception, returns api response unknown error`()= runTest{
        //given
        enqueue {
            unknownError()
        }

        //when
        val apiResponse = sportEventsApiClient.getSportEvents()

        //then
        Truth.assertThat(apiResponse).isNotNull()
        Truth.assertThat(apiResponse.isError())
        Truth.assertThat(apiResponse).isInstanceOf(UnknownError::class.java)
    }
}