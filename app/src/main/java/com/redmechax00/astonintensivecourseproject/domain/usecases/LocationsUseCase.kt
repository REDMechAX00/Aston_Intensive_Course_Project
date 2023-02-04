package com.redmechax00.astonintensivecourseproject.domain.usecases

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.repositories.locations.LocationsLocalRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.locations.LocationsRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import javax.inject.Inject

class LocationsUseCase @Inject constructor(
    private val locationsRemoteRepository: LocationsRemoteRepository,
    private val locationsLocalRepository: LocationsLocalRepository
) {

    fun getRemoteLocations(
        searchFilter: LocationsFilterModel
    ): PagingSource<Int, LocationDomainModel> =
        locationsRemoteRepository.getLocations(searchFilter)

    fun getLocalLocations(
        searchFilter: LocationsFilterModel
    ): PagingSource<Int, LocationDomainModel> =
        locationsLocalRepository.getLocations(searchFilter)
}