package com.redmechax00.astonintensivecourseproject.domain.usecases

import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.location.LocationDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import javax.inject.Inject

class LocationDetailsUseCase @Inject constructor(
    private val locationDetailsRemoteRepository: LocationDetailsRemoteRepository
) {

    suspend operator fun invoke(id: Int?): RemoteDetailsResult<LocationDetailsDomainModel> =
        locationDetailsRemoteRepository.getLocationDetails(id)
}