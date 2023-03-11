package gr.jvoyatz.sportspot.data.sport_events.source.db

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SportEventDbModule {

    @Singleton
    @Binds
    abstract fun bindSportDbClient(impl: SportEventsDbClientImpl): SportEventsDbClient
}