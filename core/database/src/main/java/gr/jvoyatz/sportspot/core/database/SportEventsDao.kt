package gr.jvoyatz.sportspot.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportEventsDao {
    @Query("SELECT * from SportCategoryEntity")
    fun selectEvents(): Flow<List<SportCategoryEntity>>

    @Query("DELETE from SportCategoryEntity")
    suspend fun deleteEvents()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(list: List<SportCategoryEntity>)
}