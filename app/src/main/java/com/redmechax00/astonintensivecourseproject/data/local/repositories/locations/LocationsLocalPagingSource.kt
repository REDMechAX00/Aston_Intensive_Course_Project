package com.redmechax00.astonintensivecourseproject.data.local.repositories.locations

import android.content.res.Resources.NotFoundException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.toLocationDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabViewModel.Companion.NETWORK_PAGE_SIZE
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class LocationsLocalPagingSource @AssistedInject constructor(
    private val appDao: AppDao,
    @Assisted(
        KEY_ASSISTED_LOCATIONS_SEARCH_PARAMS
    ) private val searchFilter: LocationsFilterModel,
) : PagingSource<Int, LocationDomainModel>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val KEY_ASSISTED_LOCATIONS_SEARCH_PARAMS = "locations_search_params"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDomainModel> {
        val pageNumber = (params.key ?: INITIAL_PAGE_NUMBER)
        val locationsDomainList = appDao.getLocations(
            offset = (pageNumber - INITIAL_PAGE_NUMBER) * NETWORK_PAGE_SIZE,
            limit = NETWORK_PAGE_SIZE,
            name = searchFilter.name,
            type = searchFilter.type,
            dimension = searchFilter.dimension
        ).map { it.toLocationDomainModel() }

        val nextPageNumber =
            if (locationsDomainList.isEmpty()) null else pageNumber + 1
        val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

        return if (locationsDomainList.isNotEmpty()) {
            LoadResult.Page(
                data = locationsDomainList,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } else {
            LoadResult.Error(NotFoundException())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted(
                KEY_ASSISTED_LOCATIONS_SEARCH_PARAMS
            ) searchParams: LocationsFilterModel
        ): LocationsLocalPagingSource
    }
}