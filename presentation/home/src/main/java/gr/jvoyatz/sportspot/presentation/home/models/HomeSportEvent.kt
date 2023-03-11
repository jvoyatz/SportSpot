package gr.jvoyatz.sportspot.presentation.home.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeSportEvent(
    val id: Long,
    val name: String,
    val sportId: String,
    val description: String?=null,
    val startDateTimeStamp: Long
):Parcelable