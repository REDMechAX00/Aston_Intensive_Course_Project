package com.redmechax00.astonintensivecourseproject.data.local.repositories.locations

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.LocationEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import javax.inject.Inject

class LocationsLocalRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val locationsLocalPagingSourceFactory: LocationsLocalPagingSource.Factory
) : LocationsLocalRepository {

    override fun getLocations(
        searchFilter: LocationsFilterModel
    ): PagingSource<Int, LocationDomainModel> =
        locationsLocalPagingSourceFactory.create(searchFilter)

    override suspend fun insertLocations(locationsEntityList: List<LocationEntity>) {
        appDao.insertLocations(locationsEntityList)
    }
}