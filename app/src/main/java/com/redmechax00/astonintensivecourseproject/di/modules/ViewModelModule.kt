package com.redmechax00.astonintensivecourseproject.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redmechax00.astonintensivecourseproject.di.ViewModelFactory
import com.redmechax00.astonintensivecourseproject.di.ViewModelKey
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenSharedViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character.CharacterDetailsViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode.EpisodeDetailsViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location.LocationDetailsViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.EpisodesTabViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.LocationsTabViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenSharedViewModel::class)
    abstract fun bindMainScreenSharedViewModel(viewModel: MainScreenSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersTabViewModel::class)
    abstract fun bindCharactersTabViewModel(viewModel: CharactersTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsTabViewModel::class)
    abstract fun bindLocationsTabViewModel(viewModel: LocationsTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesTabViewModel::class)
    abstract fun bindEpisodesTabViewModel(viewModel: EpisodesTabViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    abstract fun bindLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    abstract fun bindEpisodeDetailsViewModel(viewModel: EpisodeDetailsViewModel): ViewModel
}
