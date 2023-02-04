package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.episodes

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redmechax00.astonintensivecourseproject.data.local.entity.EpisodeEntity
import com.redmechax00.astonintensivecourseproject.data.local.repositories.episodes.EpisodesLocalRepository
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.toEpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.toEpisodeEntity
import com.redmechax00.astonintensivecourseproject.data.remote.services.EpisodesService
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class EpisodesRemotePagingSource @AssistedInject constructor(
    private val episodesService: EpisodesService,
    @Assisted(KEY_ASSISTED_EPISODES_FILTER) private val episodesFilter: EpisodesFilterModel,
    private val episodesLocalRepository: EpisodesLocalRepository
) : PagingSource<Int, EpisodeDomainModel>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val KEY_ASSISTED_EPISODES_FILTER = "episodes_filter"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDomainModel> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        try {
            val response = episodesService.getEpisodes(
                page = pageNumber,
                name = episodesFilter.name,
                episode = "${episodesFilter.season ?: ""}${episodesFilter.series ?: ""}"
                    .ifEmpty { null }
            )

            Log.d("dddd", "${episodesFilter.season ?: ""}${episodesFilter.series ?: ""}")
            Log.d("dddd", response.raw().request.url.toString())

            return if (response.isSuccessful) {
                val episodesResponseDto = response.body()
                val episodes =
                    (episodesResponseDto?.results ?: listOf()).map { it.toEpisodeDomainModel() }
                val nextPageNumber = if (episodes.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

                val episodesEntityList =
                    (episodesResponseDto?.results ?: listOf()).map { it.toEpisodeEntity() }
                if (episodesEntityList.isNotEmpty()) {
                    saveToLocalStorage(episodesEntityList)
                }

                LoadResult.Page(
                    data = episodes,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun saveToLocalStorage(episodesEntityList: List<EpisodeEntity>) {
        episodesLocalRepository.insertEpisodes(episodesEntityList)
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted(KEY_ASSISTED_EPISODES_FILTER) filter: EpisodesFilterModel
        ): EpisodesRemotePagingSource
    }
}