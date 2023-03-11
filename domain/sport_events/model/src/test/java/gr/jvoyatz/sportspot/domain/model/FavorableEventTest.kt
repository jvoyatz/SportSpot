package gr.jvoyatz.sportspot.domain.model

import com.google.common.truth.Truth
import gr.jvoyatz.sportspot.domain.model.utils.MockData
import org.junit.Test

class FavorableEventTest{

    @Test
    fun `test that event values are the same as well as isFavorite is false`(){
        //given
        val given = MockData.mockEvent
        val isFavorite = false

        //when
        val favorableEvent = FavorableSportEvent(given, isFavorite)

        //then
        Truth.assertThat(favorableEvent.isFavorite).isTrue()
        Truth.assertThat(favorableEvent.event).isEqualTo(given)
    }

    @Test
    fun `test that event values are the same as well as isFavorite is true`(){
        //given
        val given = MockData.mockEvent
        val isFavorite = true

        //when
        val favorableEvent = FavorableSportEvent(given, isFavorite)

        //then
        Truth.assertThat(favorableEvent.isFavorite).isTrue()
        Truth.assertThat(favorableEvent.event).isEqualTo(given)
    }
}