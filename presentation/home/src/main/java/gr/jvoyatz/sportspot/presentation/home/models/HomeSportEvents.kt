package gr.jvoyatz.sportspot.presentation.home.models

import android.os.Parcelable
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeSportEvents(
    val id: String,
    val name: String,
    val events: List<HomeFavorableSportEvent> = listOf(),
    val iconId : Int = -1
):Parcelable

internal val LoadingHomeSportEvent = HomeSportEvents(
    AdapterItemTypeConstants.LOADING,
    "")

internal fun getErrorHomeSportEvent(message: String ?= null) =
    HomeSportEvents(AdapterItemTypeConstants.ERROR, message ?: "An unknown error occurred")