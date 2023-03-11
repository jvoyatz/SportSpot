package gr.jvoyatz.sportspot.core.database.entities


data class SportEventEntity(
    val id: Long,
    val name: String,
    val sportId: String,
    val description: String?=null,
    val startDateTimeStamp: Long
)