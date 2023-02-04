package com.redmechax00.astonintensivecourseproject.data.remote.services

import com.redmechax00.astonintensivecourseproject.data.remote.models.details.AllEpisodesDetailsResponseDto
import com.redmechax00.astonintensivecourseproject.data.remote.services.EpisodesService.Companion.PATH_SEGMENT_EPISODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface AllEpisodesDetailsService {

    companion object {
        const val PATH_SEGMENT_EPISODES_INT_LIST = "episodes_list"
    }

    @GET("$PATH_SEGMENT_EPISODE/{${PATH_SEGMENT_EPISODES_INT_LIST}}")
    suspend fun getAllCharacters(
        @Path(value = PATH_SEGMENT_EPISODES_INT_LIST, encoded = true) listOfIds: List<Int?>
    ): Response<List<AllEpisodesDetailsResponseDto>>
}