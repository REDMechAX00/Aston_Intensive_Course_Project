package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.episodes

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel

interface EpisodesRemoteRepository {
    fun getEpisodes (
        episodesFilter: EpisodesFilterModel
    ): PagingSource<Int, EpisodeDomainModel>
}