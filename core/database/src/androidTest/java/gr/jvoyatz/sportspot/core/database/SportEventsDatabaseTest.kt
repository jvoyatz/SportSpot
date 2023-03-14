package gr.jvoyatz.sportspot.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import gr.jvoyatz.sportspot.core.database.entities.SportEventEntity
import gr.jvoyatz.sportspot.core.database.entities.SportCategoryEntity
import gr.jvoyatz.sportspot.core.testing.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SportEventsDatabaseTest {

    private lateinit var sportEventsDao: SportEventsDao
    private lateinit var db: SportSpotDatabase
    private val moshi: Moshi = Utils.moshi

    private val event = SportEventEntity(1, "asdf", "foot","", 1234)
    private val eventsEntity = SportCategoryEntity("foot", "name",/*  EventHolder(*/listOf(event))//)

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val typeConverter = SportEventConverter(moshi)
        db = Room.inMemoryDatabaseBuilder(context, SportSpotDatabase::class.java)
            .addTypeConverter(typeConverter).build()
        sportEventsDao = db.sportEventsDao();
    }

    @After
    @kotlin.jvm.Throws(IOException::class)
    fun tearDown(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertNewSportEvents() = runTest {

        //when
        sportEventsDao.insertEvents(listOf(eventsEntity))

        //then
        val result = sportEventsDao.selectEvents().first()
        Truth.assertThat(result.size).isEqualTo(1)
    }

    @Test
    @Throws(Exception::class)
    fun deleteSportEvents() = runTest {
        //given
        sportEventsDao.insertEvents(listOf(eventsEntity))
        var storedEvents = sportEventsDao.selectEvents().first()
        Truth.assertThat(storedEvents).isNotEmpty()

        //when
        sportEventsDao.deleteEvents()

        //then
        storedEvents = sportEventsDao.selectEvents().first()
        Truth.assertThat(storedEvents).isEmpty()
    }


    @Test
    @Throws(Exception::class)
    fun selectSportEventsAsync() = runTest {
        //given
        var sportEvents = listOf(
            eventsEntity,
            eventsEntity.copy(name = "new test test ! name", id = "soccer", events = listOf())
        )
        sportEventsDao.insertEvents(sportEvents)

        //when
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            sportEventsDao.selectEvents().collect{
                latch.countDown()
            }
        }

        latch.await()
        job.cancelAndJoin()
    }

}