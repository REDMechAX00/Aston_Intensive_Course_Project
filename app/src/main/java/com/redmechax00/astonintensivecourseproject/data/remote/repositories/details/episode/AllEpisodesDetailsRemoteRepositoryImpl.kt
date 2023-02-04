package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode

import android.util.Log
import com.redmechax00.astonintensivecourseproject.data.remote.models.details.toEpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.services.AllEpisodesDetailsService
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import retrofit2.HttpException
import javax.inject.Inject

class AllEpisodesDetailsRemoteRepositoryImpl @Inject constructor(
    private val allEpisodesDetailsService: AllEpisodesDetailsService
) : AllEpisodesDetailsRemoteRepository {

    override suspend fun getAllEpisodesDetails(listOfIds: List<Int?>): RemoteDetailsResult<List<EpisodeDetailsDomainModel>> {

        return try {
            val response = allEpisodesDetailsService.getAllCharacters(listOfIds)

            Log.d("dddd", response.raw().request.url.toString())

            if (response.isSuccessful) {
                val details = response.body()?.map { it.toEpisodeDetailsDomainModel() }
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