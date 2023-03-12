package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.network.dto.SportEventsDto
import gr.jvoyatz.sportspot.core.testing.utils.deserializeList
import gr.jvoyatz.sportspot.core.testing.utils.loadResourceFile
import org.junit.BeforeClass

abstract class BaseTest {

    companion object {
        var data: List<SportEventsDto>? = null

        @JvmStatic
        @BeforeClass
        fun loadData() {
            val content = loadResourceFile("/sport_events.json")
            data = deserializeList<SportEventsDto>(content)
        }
    }

}