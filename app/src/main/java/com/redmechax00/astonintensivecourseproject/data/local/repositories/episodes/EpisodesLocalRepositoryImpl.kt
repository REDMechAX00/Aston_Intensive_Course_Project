package com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.EpisodeEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import javax.inject.Inject

class EpisodesLocalRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val episodesLocalPagingSourceFactory: EpisodesLocalPagingSource.Factory
) : EpisodesLocalRepository {

    override fun getEpisodes(
        searchParams: EpisodesFilterModel
    ): PagingSource<Int, EpisodeDomainModel> =
        episodesLocalPagingSourceFactory.create(searchParams)

    override suspend fun insertEpisodes(episodesEntityList: List<EpisodeEntity>) {
        appDao.insertEpisodes(episodesEntityList)
    }
}