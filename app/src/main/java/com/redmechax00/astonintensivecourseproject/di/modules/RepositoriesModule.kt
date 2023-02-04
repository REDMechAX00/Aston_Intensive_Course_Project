package com.redmechax00.astonintensivecourseproject.di.modules

import com.redmechax00.astonintensivecourseproject.data.local.repositories.characters.CharactersLocalRepository
import com.redmechax00.astonintensivecourseproject.data.local.repositories.characters.CharactersLocalRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes.EpisodesLocalRepository
import com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes.EpisodesLocalRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.local.repositories.locations.LocationsLocalRepository
import com.redmechax00.astonintensivecourseproject.data.local.repositories.locations.LocationsLocalRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character.AllCharactersDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character.AllCharactersDetailsRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character.CharacterDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character.CharacterDetailsRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode.AllEpisodesDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode.AllEpisodesDetailsRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode.EpisodeDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode.EpisodeDetailsRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.location.LocationDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.location.LocationDetailsRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.characters.CharactersRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.characters.CharactersRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.episodes.EpisodesRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.episodes.EpisodesRemoteRepositoryImpl
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.locations.LocationsRemoteRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.locations.LocationsRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoriesModule {

    companion object {

        @Singleton
        @Provides
        fun provideCharactersRemoteRepository(
            charactersRemoteRepositoryImpl: CharactersRemoteRepositoryImpl
        ): CharactersRemoteRepository = charactersRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideLocationsRemoteRepository(
            locationsRemoteRepositoryImpl: LocationsRemoteRepositoryImpl
        ): LocationsRemoteRepository = locationsRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideEpisodesRemoteRepository(
            episodesRemoteRepositoryImpl: EpisodesRemoteRepositoryImpl
        ): EpisodesRemoteRepository = episodesRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideCharacterDetailsRemoteRepository(
            characterDetailsRemoteRepositoryImpl: CharacterDetailsRemoteRepositoryImpl
        ): CharacterDetailsRemoteRepository = characterDetailsRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideLocationDetailsRemoteRepository(
            locationDetailsRemoteRepositoryImpl: LocationDetailsRemoteRepositoryImpl
        ): LocationDetailsRemoteRepository = locationDetailsRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideEpisodeDetailsRemoteRepository(
            episodeDetailsRemoteRepositoryImpl: EpisodeDetailsRemoteRepositoryImpl
        ): EpisodeDetailsRemoteRepository = episodeDetailsRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideAllCharactersDetailsRemoteRepository(
            allCharactersDetailsRemoteRepositoryImpl: AllCharactersDetailsRemoteRepositoryImpl
        ): AllCharactersDetailsRemoteRepository = allCharactersDetailsRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideAllEpisodesDetailsRemoteRepository(
            allEpisodesDetailsRemoteRepositoryImpl: AllEpisodesDetailsRemoteRepositoryImpl
        ): AllEpisodesDetailsRemoteRepository = allEpisodesDetailsRemoteRepositoryImpl

        @Singleton
        @Provides
        fun provideCharactersLocalRepository(
            charactersLocalRepositoryImpl: CharactersLocalRepositoryImpl
        ): CharactersLocalRepository = charactersLocalRepositoryImpl

        @Singleton
        @Provides
        fun provideLocationsLocalRepository(
            locationsLocalRepositoryImpl: LocationsLocalRepositoryImpl
        ): LocationsLocalRepository = locationsLocalRepositoryImpl

        @Singleton
        @Provides
        fun provideEpisodesLocalRepository(
            episodesLocalRepositoryImpl: EpisodesLocalRepositoryImpl
        ): EpisodesLocalRepository = episodesLocalRepositoryImpl
    }
}