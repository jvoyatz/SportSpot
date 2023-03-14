package gr.jvoyatz.sportspot.domain.model

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.domain.model.utils.MockData
import org.junit.Test

class SportEventTest{

    @Test
    fun `test Event object instance variables are the same as the given`(){
        //given
        val givenEvent = MockData.mockEvent
        val (id, eventName, sportId, descr,  startDateTs) = givenEvent

        //when
        val event = SportEvent(id, eventName, sportId, startDateTimeStamp = startDateTs)

        //then
        Truth.assertThat(event == givenEvent).isTrue()

    }

    @Test
    fun `test Event object descriptions is not null`(){
        //given
        val givenEvent = MockData.mockEvent
        val (id, eventName, sportId, descr, startDateTs) = givenEvent

        //when
        val event = SportEvent(id, eventName, sportId, descr, startDateTimeStamp = startDateTs)

        //then
        Truth.assertThat(event.description).isNotNull()
        Truth.assertThat(event == givenEvent).isTrue()
    }
}