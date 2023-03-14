package gr.jvoyatz.sportspot.presentation.home.models

import android.os.Parcelable
import gr.jvoyatz.core.common_android.adapters.AdapterItemTypeConstants
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeSportCategory(
    val id: String,
    val name: String,
    val events: List<HomeFavorableSportEvent> = listOf(),
    val iconId: Int = -1
) : Parcelable {
    @IgnoredOnParcel
    var onErrorAction: (() -> Unit)? = null
}


//util classes for providing and handling recyclerview adapter state
internal val LoadingHomeSportEvent = HomeSportCategory(
    AdapterItemTypeConstants.LOADING,
    ""
)

internal fun getErrorHomeSportCategory(message: String? = null, onErrorAction: (() -> Unit)?) =
    HomeSportCategory(AdapterItemTypeConstants.ERROR, message ?: "An unknown error occurred")
        .also {
            it.onErrorAction = onErrorAction
        }

internal fun getEmptyHomeSportEvent(message: String? = null) =
    HomeSportCategory(AdapterItemTypeConstants.EMPTY, message ?: "")
