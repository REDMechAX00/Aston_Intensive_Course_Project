package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.episodes

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import javax.inject.Inject

class EpisodesRemoteRepositoryImpl @Inject constructor(
    private val episodesPagingSourceFactory: EpisodesRemotePagingSource.Factory
) : EpisodesRemoteRepository {

    override fun getEpisodes(
        episodesFilter: EpisodesFilterModel
    ): PagingSource<Int, EpisodeDomainModel> {
        return episodesPagingSourceFactory.create(episodesFilter)
    }
}