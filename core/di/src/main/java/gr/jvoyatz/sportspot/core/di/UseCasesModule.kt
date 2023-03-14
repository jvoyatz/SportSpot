package gr.jvoyatz.sportspot.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import gr.jvoyatz.sportpot.domain.usecases.usecases.*

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideGetSportEvents(useCases: SportEventsUseCases): GetSportEvents {
        return useCases.getSportEvents
    }

    @ViewModelScoped
    @Provides
    fun provideMarkEventAsFavorite(useCases: SportEventsUseCases): SetFavoriteSportEvent {
        return useCases.setFavoriteSportEvent
    }

    @ViewModelScoped
    @Provides
    fun provideGetSportEventById(useCases: SportEventsUseCases): GetSportEventById {
        return useCases.getSportEventById
    }

    @ViewModelScoped
    @Provides
    fun provideRefreshSportEvents(useCases: SportEventsUseCases): RefreshSportEvents {
        return useCases.refreshSportEvents
    }
}