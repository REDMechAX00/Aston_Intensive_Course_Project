package com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes

import android.content.res.Resources.NotFoundException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.toEpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabViewModel.Companion.NETWORK_PAGE_SIZE
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class EpisodesLocalPagingSource @AssistedInject constructor(
    private val appDao: AppDao,
    @Assisted(
        KEY_ASSISTED_EPISODES_SEARCH_PARAMS
    ) private val searchFilter: EpisodesFilterModel,
) : PagingSource<Int, EpisodeDomainModel>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val KEY_ASSISTED_EPISODES_SEARCH_PARAMS = "episodes_search_params"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDomainModel> {
        val pageNumber = (params.key ?: INITIAL_PAGE_NUMBER)
        val episodesDomainList = appDao.getEpisodes(
            offset = (pageNumber - INITIAL_PAGE_NUMBER) * NETWORK_PAGE_SIZE,
            limit = NETWORK_PAGE_SIZE,
            name = searchFilter.name,
            episode = searchFilter.season + searchFilter.series
        ).map { it.toEpisodeDomainModel() }

        val nextPageNumber =
            if (episodesDomainList.isEmpty()) null else pageNumber + 1
        val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

        return if (episodesDomainList.isNotEmpty()) {
            LoadResult.Page(
                data = episodesDomainList,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } else {
            LoadResult.Error(NotFoundException())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted(
                KEY_ASSISTED_EPISODES_SEARCH_PARAMS
            ) searchParams: EpisodesFilterModel
        ): EpisodesLocalPagingSource
    }
}