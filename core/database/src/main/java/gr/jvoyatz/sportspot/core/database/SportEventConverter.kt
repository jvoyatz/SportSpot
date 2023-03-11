package gr.jvoyatz.sportspot.core.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import gr.jvoyatz.sportspot.core.database.entities.SportEventEntity
import javax.inject.Inject

@ProvidedTypeConverter
class SportEventConverter @Inject constructor(
    private val moshi: Moshi
){
    @TypeConverter
    fun fromString(value: String): List<SportEventEntity>{
        val type = Types.newParameterizedType(List::class.java, SportEventEntity::class.java)
        return moshi.adapter<List<SportEventEntity>>(type).fromJson(value).orEmpty()
    }

    @TypeConverter
    fun fromSportEventType(events: List<SportEventEntity>): String{
        val type = Types.newParameterizedType(List::class.java, SportEventEntity::class.java)
        return moshi.adapter<List<SportEventEntity>>(type).toJson(events)
    }
}