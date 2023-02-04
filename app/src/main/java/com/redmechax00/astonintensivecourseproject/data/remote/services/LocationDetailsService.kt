package com.redmechax00.astonintensivecourseproject.data.remote.services

import com.redmechax00.astonintensivecourseproject.data.remote.models.details.LocationDetailsResponseDto
import com.redmechax00.astonintensivecourseproject.data.remote.services.LocationsService.Companion.PATH_SEGMENT_LOCATION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface LocationDetailsService {

    companion object {
        const val PATH_SEGMENT_ID = "id"
    }

    @GET("$PATH_SEGMENT_LOCATION/{$PATH_SEGMENT_ID}")
    suspend fun getLocationDetails(
        @Path(value = PATH_SEGMENT_ID, encoded = true) id: Int
    ): Response<LocationDetailsResponseDto>
}