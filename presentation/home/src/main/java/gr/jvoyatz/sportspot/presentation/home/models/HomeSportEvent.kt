package gr.jvoyatz.sportspot.presentation.home.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeSportEvent(
    val id: Long,
    val name: String,
    val sportId: String,
    val description: String?=null,
    val startDateTimeStamp: Long
):Parcelable{
    @IgnoredOnParcel
    var team1: String? = null
        private set
    @IgnoredOnParcel
    var team2: String? = null
        private set

    init {
        try{
            with(name){
                if(this.isBlank()) return@with
                this.split("-").let{
                    team1 = it[0]
                    team2 = it[1]
                }
            }
        }catch (_:Exception){}
    }
}