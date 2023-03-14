package gr.jvoyatz.sportspot.data.sports_events.repo.mappers

import gr.jvoyatz.sportspot.core.common.EntityMapper
import gr.jvoyatz.sportspot.core.common.mapList
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventEntityMapper.domainToEntity
import gr.jvoyatz.sportspot.data.sports_events.repo.mappers.SportEventEntityMapper.entityToDomain
import gr.jvoyatz.sportspot.domain.model.FavorableSportEvent
import gr.jvoyatz.sportspot.domain.model.SportEvents

object SportEventsEntityMapper : EntityMapper<SportCategoryEntity, SportEvents> {
    override fun SportCategoryEntity.entityToDomain(): SportEvents = SportEvents(
        id, name, this.events.mapList { FavorableSportEvent( it.entityToDomain(), it.isFavorite) }
    )

    override fun SportEvents.domainToEntity(): SportCategoryEntity = SportCategoryEntity(
        this.id, this.name, this.events.mapList {
            it.event.domainToEntity().copy(
                isFavorite = it.isFavorite
            )
        }
    )
}