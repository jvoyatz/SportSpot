package gr.jvoyatz.sportspot.core.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SportEventsEntity(
    @PrimaryKey
    val id: String,
    val name: String = "Not Provided",
    val events : List<SportEventEntity> = listOf()
)