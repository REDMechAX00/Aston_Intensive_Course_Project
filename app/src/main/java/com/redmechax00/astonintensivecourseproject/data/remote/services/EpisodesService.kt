package com.redmechax00.astonintensivecourseproject.data.remote.services

import androidx.annotation.IntRange
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.EpisodesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface EpisodesService {

    companion object{
        const val PATH_SEGMENT_EPISODE = "episode"
    }

    @GET(PATH_SEGMENT_EPISODE)
    suspend fun getEpisodes(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): Response<EpisodesResponseDto>
}