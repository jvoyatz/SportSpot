package gr.jvoyatz.sportspot.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.jvoyatz.sportspot.core.database.entities.SportEventsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsEventsDao {
    @Query("SELECT * from SportEventsEntity")
    fun selectSportEvents(): Flow<List<SportEventsEntity>>

    @Query("DELETE from SportEventsEntity")
    suspend fun deleteSportEvents()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSportEvents(list: List<SportEventsEntity>)
}