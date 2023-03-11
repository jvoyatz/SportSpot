package gr.jvoyatz.sportspot.core.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import gr.jvoyatz.sportspot.core.database.entities.SportEvent
import javax.inject.Inject

@ProvidedTypeConverter
class SportEventConverter @Inject constructor(
    private val moshi: Moshi
){
    @TypeConverter
    fun fromString(value: String): List<SportEvent>{
        val type = Types.newParameterizedType(List::class.java, SportEvent::class.java)
        return moshi.adapter<List<SportEvent>>(type).fromJson(value).orEmpty()
    }

    @TypeConverter
    fun fromSportEventType(events: List<SportEvent>): String{
        val type = Types.newParameterizedType(List::class.java, SportEvent::class.java)
        return moshi.adapter<List<SportEvent>>(type).toJson(events)
    }
}