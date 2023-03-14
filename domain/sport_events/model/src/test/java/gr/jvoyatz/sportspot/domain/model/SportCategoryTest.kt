package gr.jvoyatz.sportspot.domain.model

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.domain.model.utils.MockData
import org.junit.Test

class SportCategoryTest{

    @Test
    fun `test that sports events contains no scheduled events`(){
        //given
        val givenSportEvent = MockData.mockEmptySportsEvent

        //when
        val newSportEvent = SportCategory(givenSportEvent.id, givenSportEvent.name, givenSportEvent.events)

        //then
        Truth.assertThat(newSportEvent.events).isEmpty()
        Truth.assertThat(newSportEvent !== givenSportEvent).isTrue()
        Truth.assertThat(newSportEvent == givenSportEvent).isTrue()
    }

    @Test
    fun `test that sports events contains  scheduled events`(){
        //given
        val givenSportEvent = MockData.mockNonEmptySportsEvent

        //when
        val newSportEvent = SportCategory(givenSportEvent.id, givenSportEvent.name, givenSportEvent.events)

        //then
        Truth.assertThat(newSportEvent.events).isNotEmpty()
        Truth.assertThat(newSportEvent !== givenSportEvent).isTrue()
        Truth.assertThat(newSportEvent == givenSportEvent).isTrue()
    }
}