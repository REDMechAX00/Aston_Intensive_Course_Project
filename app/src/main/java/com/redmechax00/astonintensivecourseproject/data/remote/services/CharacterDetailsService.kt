package com.redmechax00.astonintensivecourseproject.data.remote.services

import com.redmechax00.astonintensivecourseproject.data.remote.models.details.CharacterDetailsResponseDto
import com.redmechax00.astonintensivecourseproject.data.remote.services.CharactersService.Companion.PATH_SEGMENT_CHARACTER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface CharacterDetailsService {

    companion object {
        const val PATH_SEGMENT_ID = "id"
    }

    @GET("$PATH_SEGMENT_CHARACTER/{$PATH_SEGMENT_ID}")
    suspend fun getCharacterDetails(
        @Path(value = PATH_SEGMENT_ID, encoded = true) id: Int
    ): Response<CharacterDetailsResponseDto>
}