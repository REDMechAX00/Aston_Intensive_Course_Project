package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.locations

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import javax.inject.Inject

class LocationsRemoteRepositoryImpl @Inject constructor(
    private val locationsPagingSourceFactory: LocationsRemotePagingSource.Factory
) : LocationsRemoteRepository {

    override fun getLocations(
        locationsFilter: LocationsFilterModel
    ): PagingSource<Int, LocationDomainModel> {
        return locationsPagingSourceFactory.create(locationsFilter)
    }
}