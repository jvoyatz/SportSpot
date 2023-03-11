package gr.jvoyatz.sportspot.core.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportEventsEntity(
    val name: String = "",
    val events: List<SportEventEntity> = listOf(),
    @PrimaryKey
    val id: String = ""
)