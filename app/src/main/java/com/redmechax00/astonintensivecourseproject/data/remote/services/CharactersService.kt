package com.redmechax00.astonintensivecourseproject.data.remote.services

import androidx.annotation.IntRange
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.CharactersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface CharactersService {

    companion object{
        const val PATH_SEGMENT_CHARACTER = "character"
    }

    @GET(PATH_SEGMENT_CHARACTER)
    suspend fun getCharacters(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Response<CharactersResponseDto>
}