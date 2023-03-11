package gr.jvoyatz.sportspot.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import gr.jvoyatz.sportspot.presentation.home.HomeUiState

@Module
@InstallIn(ViewModelComponent::class)
class HomeFragmentModule {

    @Provides
    fun provideInitHomeUiState(): HomeUiState = HomeUiState()
}