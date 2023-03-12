package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.DtoMapper
import gr.jvoyatz.sportspot.core.network.dto.SportEventDto
import gr.jvoyatz.sportspot.domain.model.SportEvent

object SportEventDtoMapper : DtoMapper<SportEventDto, SportEvent> {

    override fun SportEventDto.dtoToDomain(): SportEvent = SportEvent(
        this.i.toLong(),  this.d, this.si, this.sh, this.tt
    )

    override fun SportEvent.domainToDto(): SportEventDto {
        TODO("Not yet implemented")
    }
}