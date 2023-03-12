package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.EntityMapper
import gr.jvoyatz.sportspot.core.database.entities.SportEventEntity
import gr.jvoyatz.sportspot.domain.model.SportEvent

object SportEventEntityMapper : EntityMapper<SportEventEntity, SportEvent> {
    override fun SportEventEntity.entityToDomain(): SportEvent = SportEvent(
        id, name, sportId, description, startDateTimeStamp
    )

    override fun SportEvent.domainToEntity(): SportEventEntity = SportEventEntity(
        id, name, sportId, description, startDateTimeStamp
    )
}