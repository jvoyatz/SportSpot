package gr.jvoyatz.sportspot.presentation.home.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeSportEvents(
    val id: String,
    val name: String,
    val events: List<HomeFavorableSportEvent> = listOf()
):Parcelable