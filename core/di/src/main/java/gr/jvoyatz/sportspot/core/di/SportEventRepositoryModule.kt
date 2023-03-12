package gr.jvoyatz.sportspot.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.sportpot.domain.usecases.repository.SportEventsRepository
import gr.jvoyatz.sportspot.data.sports_events.repo.SportEventRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SportEventRepositoryModule {

//    @Provides
//    @Singleton
//    fun provideSportEventRepository(apiClient: SportEventsApiClient, dbClient: SportEventsDbClient): SportEventsRepository{
//        return SportEventRepositoryImpl(apiClient, dbClient)
//    }

    @InstallIn(SingletonComponent::class)
    @Module
    sealed interface SportsEventRepositoryBindsModule{
        @Singleton
        @Binds
        fun bindSportEventRepository(impl: SportEventRepositoryImpl): SportEventsRepository
    }
}