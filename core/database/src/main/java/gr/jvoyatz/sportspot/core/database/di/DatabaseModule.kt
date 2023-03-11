package gr.jvoyatz.sportspot.core.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.sportspot.core.database.SportSpotDatabase
import gr.jvoyatz.sportspot.core.database.SportsEventsDao
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ):SportSpotDatabase = SportSpotDatabase.getDatabase(context)


    @Singleton
    @Provides
    fun provideBoredActivityDao(
        db: SportSpotDatabase
    ): SportsEventsDao = db.sportEventsDao()
}