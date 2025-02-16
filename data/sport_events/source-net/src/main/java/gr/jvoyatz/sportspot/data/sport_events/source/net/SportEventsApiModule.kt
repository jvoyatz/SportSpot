package gr.jvoyatz.sportspot.data.sport_events.source.net

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SportEventsApiModule{
    @Binds
    @Singleton
    abstract fun bindSportEventsApiClient(apiClientImpl: SportEventsApiClientImpl): SportEventsApiClient
}