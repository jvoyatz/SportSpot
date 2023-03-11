package gr.jvoyatz.sportspot.data.sport_events.source.net.di

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClient
import gr.jvoyatz.sportspot.data.sport_events.source.net.SportEventsApiClientImpl
import gr.jvoyatz.sportspot.data.sport_events.source.net.api.SportEventsApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


    @Singleton
    @Provides
    fun provideSportEventsApiService(retrofit: Retrofit): SportEventsApiService =
        retrofit.create(SportEventsApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ApiBindsModule{
        @Binds
        @Singleton
        abstract fun bindSportsEventsApiClient(apiClientImpl: SportEventsApiClientImpl): SportEventsApiClient
    }
}