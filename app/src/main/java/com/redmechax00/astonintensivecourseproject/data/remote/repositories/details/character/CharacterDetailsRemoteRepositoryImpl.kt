package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character

import android.util.Log
import com.redmechax00.astonintensivecourseproject.data.remote.models.details.toCharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.services.CharacterDetailsService
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import retrofit2.HttpException
import javax.inject.Inject

class CharacterDetailsRemoteRepositoryImpl @Inject constructor(
    private val characterDetailsService: CharacterDetailsService
) : CharacterDetailsRemoteRepository {

    companion object {
        const val DEFAULT_DETAILS_ID = 0
    }

    override suspend fun getCharacterDetails(id: Int?): RemoteDetailsResult<CharacterDetailsDomainModel> {

        return try {
            val response = characterDetailsService.getCharacterDetails(id ?: DEFAULT_DETAILS_ID)
            Log.d("dddd", response.raw().request.url.toString())

            if (response.isSuccessful) {
                val details = response.body()?.toCharacterDetailsDomainModel()
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