package gr.jvoyatz.sportspot.presentation.home.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
/*data*/ class HomeFavorableSportEvent(
    val event: HomeSportEvent,
    val isFavorite: Boolean
) : Parcelable{
    @IgnoredOnParcel
    lateinit var onFavoriteAction: (HomeSportEvent, Boolean) -> Unit?

}