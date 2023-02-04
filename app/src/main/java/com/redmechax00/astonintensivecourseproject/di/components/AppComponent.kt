package com.redmechax00.astonintensivecourseproject.di.components

import android.content.Context
import com.redmechax00.astonintensivecourseproject.di.modules.DaoModule
import com.redmechax00.astonintensivecourseproject.di.modules.NetworkModule
import com.redmechax00.astonintensivecourseproject.di.modules.RepositoriesModule
import com.redmechax00.astonintensivecourseproject.di.modules.ViewModelModule
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character.CharacterDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode.EpisodeDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location.LocationDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.TabsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.EpisodesTabFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.LocationsTabFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        NetworkModule::class,
        RepositoriesModule::class,
        DaoModule::class
    ]
)
interface AppComponent {

    companion object {

        fun create(context: Context): AppComponent {
            return DaggerAppComponent.factory()
                .create(
                    context = context
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainScreenFragment: MainScreenFragment)

    fun inject(tabsFragment: TabsFragment)

    fun inject(charactersTabFragment: CharactersTabFragment)

    fun inject(locationsTabFragment: LocationsTabFragment)

    fun inject(episodesTabFragment: EpisodesTabFragment)

    fun inject(characterDetailsFragment: CharacterDetailsFragment)

    fun inject(locationDetailsFragment: LocationDetailsFragment)

    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}