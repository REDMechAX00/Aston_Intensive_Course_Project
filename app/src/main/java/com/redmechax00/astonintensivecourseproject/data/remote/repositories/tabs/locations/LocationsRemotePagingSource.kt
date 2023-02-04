package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redmechax00.astonintensivecourseproject.data.local.entity.LocationEntity
import com.redmechax00.astonintensivecourseproject.data.local.repositories.locations.LocationsLocalRepository
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.toLocationDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.toLocationEntity
import com.redmechax00.astonintensivecourseproject.data.remote.services.LocationsService
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class LocationsRemotePagingSource @AssistedInject constructor(
    private val locationsService: LocationsService,
    @Assisted(KEY_ASSISTED_LOCATIONS_FILTER) private val locationsFilter: LocationsFilterModel,
    private val locationsLocalRepository: LocationsLocalRepository
) : PagingSource<Int, LocationDomainModel>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val KEY_ASSISTED_LOCATIONS_FILTER = "locations_filter"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDomainModel> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        try {
            val response = locationsService.getLocations(
                page = pageNumber,
                name = locationsFilter.name,
                type = locationsFilter.type,
                dimension = locationsFilter.dimension
            )

            return if (response.isSuccessful) {
                val locationsResponseDto = response.body()
                val locations =
                    (locationsResponseDto?.results ?: listOf()).map { it.toLocationDomainModel() }
                val nextPageNumber = if (locations.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

                val locationsEntityList =
                    (locationsResponseDto?.results ?: listOf()).map { it.toLocationEntity() }
                if (locationsEntityList.isNotEmpty()) {
                    saveToLocalStorage(locationsEntityList)
                }

                LoadResult.Page(
                    data = locations,
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

    private suspend fun saveToLocalStorage(locationsEntityList: List<LocationEntity>) {
        locationsLocalRepository.insertLocations(locationsEntityList)
    }

    override fun getRefreshKey(state: PagingState<Int, LocationDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted(KEY_ASSISTED_LOCATIONS_FILTER) filter: LocationsFilterModel
        ): LocationsRemotePagingSource
    }
}