package com.redmechax00.astonintensivecourseproject.data.remote.services

import com.redmechax00.astonintensivecourseproject.data.remote.models.details.EpisodeDetailsResponseDto
import com.redmechax00.astonintensivecourseproject.data.remote.services.EpisodesService.Companion.PATH_SEGMENT_EPISODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface EpisodeDetailsService {

    companion object {
        const val PATH_SEGMENT_ID = "id"
    }

    @GET("$PATH_SEGMENT_EPISODE/{$PATH_SEGMENT_ID}")
    suspend fun getEpisodeDetails(
        @Path(value = PATH_SEGMENT_ID, encoded = true) id: Int
    ): Response<EpisodeDetailsResponseDto>
}