package com.redmechax00.astonintensivecourseproject.domain.usecases

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes.EpisodesLocalRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.episodes.EpisodesRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import javax.inject.Inject

class EpisodesUseCase @Inject constructor(
    private val episodesRemoteRepository: EpisodesRemoteRepository,
    private val episodesLocalRepository: EpisodesLocalRepository
) {

    fun getRemoteEpisodes(
        searchFilter: EpisodesFilterModel
    ): PagingSource<Int, EpisodeDomainModel> =
        episodesRemoteRepository.getEpisodes(searchFilter)

    fun getLocalEpisodes(
        searchFilter: EpisodesFilterModel
    ): PagingSource<Int, EpisodeDomainModel> =
        episodesLocalRepository.getEpisodes(searchFilter)
}