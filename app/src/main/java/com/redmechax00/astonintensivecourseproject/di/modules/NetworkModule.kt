package com.redmechax00.astonintensivecourseproject.di.modules

import com.redmechax00.astonintensivecourseproject.data.remote.services.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"

        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Singleton
        @Provides
        fun provideCharactersService(retrofit: Retrofit): CharactersService =
            retrofit.create(CharactersService::class.java)

        @Singleton
        @Provides
        fun provideLocationsService(retrofit: Retrofit): LocationsService =
            retrofit.create(LocationsService::class.java)

        @Singleton
        @Provides
        fun provideEpisodesService(retrofit: Retrofit): EpisodesService =
            retrofit.create(EpisodesService::class.java)

        @Singleton
        @Provides
        fun provideCharacterDetailsService(retrofit: Retrofit): CharacterDetailsService =
            retrofit.create(CharacterDetailsService::class.java)

        @Singleton
        @Provides
        fun provideLocationDetailsService(retrofit: Retrofit): LocationDetailsService =
            retrofit.create(LocationDetailsService::class.java)

        @Singleton
        @Provides
        fun provideEpisodeDetailsService(retrofit: Retrofit): EpisodeDetailsService =
            retrofit.create(EpisodeDetailsService::class.java)

        @Singleton
        @Provides
        fun provideAllCharactersDetailsService(retrofit: Retrofit): AllCharactersDetailsService =
            retrofit.create(AllCharactersDetailsService::class.java)

        @Singleton
        @Provides
        fun provideAllEpisodesDetailsService(retrofit: Retrofit): AllEpisodesDetailsService =
            retrofit.create(AllEpisodesDetailsService::class.java)
    }
}