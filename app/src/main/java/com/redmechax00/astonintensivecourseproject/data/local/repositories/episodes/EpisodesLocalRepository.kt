package com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.entity.EpisodeEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel

interface EpisodesLocalRepository {

    fun getEpisodes(
        searchParams: EpisodesFilterModel
    ): PagingSource<Int, EpisodeDomainModel>

    suspend fun insertEpisodes(episodesEntityList: List<EpisodeEntity>)
}