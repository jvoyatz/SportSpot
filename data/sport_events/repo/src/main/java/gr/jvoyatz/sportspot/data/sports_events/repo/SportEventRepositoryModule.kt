package gr.jvoyatz.sportspot.data.sports_events.repo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.data.sport_events.source.db.SportEventsDbClient
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SportEventRepositoryModule {

    @Provides
    @Singleton
    fun provideSportEventRepository(apiClient: SportEventsApiClient, dbClient: SportEventsDbClient): SportEventsRepository{
        return SportEventRepositoryImpl(apiClient, dbClient)
    }

}