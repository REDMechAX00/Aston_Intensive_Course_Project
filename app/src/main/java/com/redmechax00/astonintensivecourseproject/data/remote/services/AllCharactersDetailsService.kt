package com.redmechax00.astonintensivecourseproject.data.remote.services

import com.redmechax00.astonintensivecourseproject.data.remote.models.details.AllCharactersDetailsResponseDto
import com.redmechax00.astonintensivecourseproject.data.remote.services.CharactersService.Companion.PATH_SEGMENT_CHARACTER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface AllCharactersDetailsService {

    companion object {
        const val PATH_SEGMENT_CHARACTERS_INT_LIST = "characters_list"
    }

    @GET("$PATH_SEGMENT_CHARACTER/{${PATH_SEGMENT_CHARACTERS_INT_LIST}}")
    suspend fun getAllCharacters(
        @Path(value = PATH_SEGMENT_CHARACTERS_INT_LIST, encoded = true) listOfIds: List<Int?>
    ): Response<List<AllCharactersDetailsResponseDto>>
}