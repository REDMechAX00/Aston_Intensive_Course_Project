package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.locations

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel

interface LocationsRemoteRepository {
    fun getLocations (
        locationsFilter: LocationsFilterModel
    ): PagingSource<Int, LocationDomainModel>
}