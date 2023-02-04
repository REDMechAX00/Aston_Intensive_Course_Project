package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.location

import android.util.Log
import com.redmechax00.astonintensivecourseproject.data.remote.models.details.toLocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.services.LocationDetailsService
import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import retrofit2.HttpException
import javax.inject.Inject

class LocationDetailsRemoteRepositoryImpl @Inject constructor(
    private val locationDetailsService: LocationDetailsService
) : LocationDetailsRemoteRepository {

    companion object {
        const val DEFAULT_DETAILS_ID = 0
    }

    override suspend fun getLocationDetails(id: Int?): RemoteDetailsResult<LocationDetailsDomainModel> {

        return try {
            val response = locationDetailsService.getLocationDetails(id ?: DEFAULT_DETAILS_ID)
            Log.d("dddd", response.raw().request.url.toString())

            if (response.isSuccessful) {
                val details = response.body()?.toLocationDetailsDomainModel()
                RemoteDetailsResult.Success(successData = details)
            } else {
                RemoteDetailsResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            RemoteDetailsResult.Error(e)
        } catch (e: Exception) {
            RemoteDetailsResult.Error(e)
        }
    }
}