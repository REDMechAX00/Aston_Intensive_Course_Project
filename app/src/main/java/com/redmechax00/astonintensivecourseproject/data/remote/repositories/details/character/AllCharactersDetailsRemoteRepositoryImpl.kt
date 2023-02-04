package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character

import android.util.Log
import com.redmechax00.astonintensivecourseproject.data.remote.models.details.toCharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.services.AllCharactersDetailsService
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import retrofit2.HttpException
import javax.inject.Inject

class AllCharactersDetailsRemoteRepositoryImpl @Inject constructor(
    private val allCharactersDetailsService: AllCharactersDetailsService
) : AllCharactersDetailsRemoteRepository {

    override suspend fun getAllCharactersDetails(listOfIds: List<Int?>): RemoteDetailsResult<List<CharacterDetailsDomainModel>> {
        return try {
            val response = allCharactersDetailsService.getAllCharacters(listOfIds)

            Log.d("dddd", response.raw().request.url.toString())

            if (response.isSuccessful) {
                val details = response.body()?.map { it.toCharacterDetailsDomainModel() }
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