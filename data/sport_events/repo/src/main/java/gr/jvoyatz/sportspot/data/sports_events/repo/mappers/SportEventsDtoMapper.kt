package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.DtoMapper
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.network.dto.SportEventsDto
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventDtoMapper.dtoToDomain
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvents

object SportEventsDtoMapper : DtoMapper<SportEventsDto, SportEvents> {
    override fun SportEventsDto.dtoToDomain(): SportEvents = SportEvents(
        this.id, this.name, this.events.mapList { FavorableSportEvent(it.dtoToDomain(), false) }
    )

    override fun SportEvents.domainToDto(): SportEventsDto {
        TODO("Not yet implemented")
    }
}