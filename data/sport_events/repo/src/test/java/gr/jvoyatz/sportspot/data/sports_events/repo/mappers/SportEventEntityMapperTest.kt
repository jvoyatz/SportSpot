package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventEntityMapper.domainToEntity
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventEntityMapper.entityToDomain
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SportEventEntityMapperTest: BaseTest(){

    @Test
    fun `convert entity to domain model`(){
        //given

        val event = data?.get(0)!!.events[0]
        val entity = event.dtoToDomain().domainToEntity().copy(id = 5)

        //when
        val domain = entity.entityToDomain()

        //then
        Truth.assertThat(domain).isNotNull()
        Truth.assertThat(domain.id).isEqualTo(5)
    }

    @Test
    fun `convert domain to entity model`(){
        //given
        val event = data?.get(0)!!.events[0]
        val domain = event.dtoToDomain()

        //when
        val entity = domain.domainToEntity()

//        //then
        Truth.assertThat(entity).isNotNull()
        Truth.assertThat(entity.id).isEqualTo(domain.id)
    }
}