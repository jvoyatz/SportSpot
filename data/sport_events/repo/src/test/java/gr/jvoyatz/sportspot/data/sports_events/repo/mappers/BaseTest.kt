package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import gr.jvoyatz.sportspot.core.network.dto.SportEventsDto
import gr.jvoyatz.sportspot.core.testing.utils.deserializeList
import gr.jvoyatz.sportspot.core.testing.utils.loadResourceFile
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventsDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventsEntityMapper.domainToEntity
import org.junit.BeforeClass

abstract class BaseTest {

    companion object {
        var data: List<SportEventsDto>? = null
        var entities: List<SportCategoryEntity>? = null

        @JvmStatic
        @BeforeClass
        fun loadData() {
            val content = loadResourceFile("/sport_events.json")
            data = deserializeList<SportEventsDto>(content)
            entities = data!!.mapList { it.dtoToDomain().domainToEntity() }
        }
    }

}