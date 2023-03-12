package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventDtoMapper.dtoToDomain
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SportEventDtoMapperTest: BaseTest(){

    @Test
    fun `convert dto to domain model`(){
        //given
        val event = data?.get(0)!!.events[0]

        //when
        val domain = event.dtoToDomain()

        //then
        Truth.assertThat(domain).isNotNull()
        Truth.assertThat(domain.id.toString()).isEqualTo(event.i)
    }
}