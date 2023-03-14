package gr.jvoyatz.sportspot.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity

private const val version = 1

@Database(entities = arrayOf(SportCategoryEntity::class), version = version)
@TypeConverters(value = [SportEventConverter::class])
abstract class SportSpotDatabase: RoomDatabase() {
    abstract fun sportEventsDao(): SportEventsDao

    companion object {
        private var instance: SportSpotDatabase? = null

        fun getDatabase(context: Context, sportEventTypeConverter: SportEventConverter): SportSpotDatabase {
            return instance ?: lazy(this) {
                Room.inMemoryDatabaseBuilder(
                    context,
                    SportSpotDatabase::class.java/*, "blase_database"*/
                )
                .addTypeConverter(sportEventTypeConverter)
                .build()
            }.let {
                instance = it.value
                instance!!
            }
        }
    }
}