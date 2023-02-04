package com.redmechax00.astonintensivecourseproject.data.local.repositories.locations

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.entity.LocationEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel

interface LocationsLocalRepository {

    fun getLocations(
        searchFilter: LocationsFilterModel
    ): PagingSource<Int, LocationDomainModel>

    suspend fun insertLocations(locationsEntityList: List<LocationEntity>)
}