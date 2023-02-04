package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.location

import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult

interface LocationDetailsRemoteRepository {

    suspend fun getLocationDetails(id: Int?): RemoteDetailsResult<LocationDetailsDomainModel>
}