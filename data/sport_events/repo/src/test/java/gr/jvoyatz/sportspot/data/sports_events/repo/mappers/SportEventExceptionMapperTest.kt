package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.core.network.ApiResponse
import gr.jvoyatz.sportspot.core.network.config.asError
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventDtoExceptionMapper.dtoToDomain
import gr.jvoyatz.sportspot.domain.model.SportEventException
import org.junit.Test

class SportEventExceptionMapperTest{

    @Test
    fun `in case of an error http api response, return SportEvent HttpException`(){
        //given
        val error = ApiResponse.httpError<String, Unit>()

        //when
        val domainException = error.asError()!!.dtoToDomain()

        //then
        Truth.assertThat(domainException).isInstanceOf(SportEventException.HttpException::class.java)
    }

    @Test
    fun `in case of an network error api response, return SportEvent NetworkException`(){
        //given
        val error = ApiResponse.networkError<String, Unit>()

        //when
        val domainException = error.asError()!!.dtoToDomain()

        //then
        Truth.assertThat(domainException).isInstanceOf(SportEventException.NetworkException::class.java)
    }

    @Test
    fun `in case of an unknown error  api response, return SportEvent UnknownException`(){
        //given
        val error = ApiResponse.unknownError<String, Unit>()

        //when
        val domainException = error.asError()!!.dtoToDomain()

        //then
        Truth.assertThat(domainException).isInstanceOf(SportEventException.UnknownException::class.java)
    }
}