package com.redmechax00.astonintensivecourseproject.data.remote.services

import androidx.annotation.IntRange
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.LocationsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface LocationsService {

    companion object {
        const val PATH_SEGMENT_LOCATION = "location"
    }

    @GET(PATH_SEGMENT_LOCATION)
    suspend fun getLocations(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): Response<LocationsResponseDto>
}