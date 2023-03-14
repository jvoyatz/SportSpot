package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.DtoMapper
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.network.dto.SportCategoryDto
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportCategory

object SportCategoryDtoMapper : DtoMapper<SportCategoryDto, SportCategory> {
    override fun SportCategoryDto.dtoToDomain(): SportCategory = SportCategory(
        this.id, this.name, this.events.mapList { FavorableSportEvent(it.dtoToDomain(), false) }
    )

    override fun SportCategory.domainToDto(): SportCategoryDto {
        TODO("Not yet implemented")
    }
}