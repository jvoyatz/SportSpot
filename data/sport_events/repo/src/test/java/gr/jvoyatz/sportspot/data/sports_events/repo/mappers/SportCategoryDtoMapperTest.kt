package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventsDtoMapper.dtoToDomain
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SportCategoryDtoMapperTest: BaseTest(){

    @Test
    fun `convert dto to favorable domain model`(){
        //given
        val sportEvent = data?.get(0)!!

        //when
        val domain = sportEvent.dtoToDomain()

        //then
        Truth.assertThat(domain).isNotNull()
        Truth.assertThat(domain.events).isNotEmpty()
        Truth.assertThat(domain.events[0].isFavorite).isFalse()
    }
}