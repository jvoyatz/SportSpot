package gr.jvoyatz.sportspot.domain.model

/**
 * A certain event arranged on specific date
 *
 * Contains:
 *  ! an id, unique
 *  ! a name for this event
 *  ! the sport type id
 *  ! a nullable description,
 *  ! a timestamp
 */
data class SportEvent(
    val id: Long,
    val name: String,
    val sportId: String,
    val description: String?=null,
    val startDateTimeStamp: Long
)