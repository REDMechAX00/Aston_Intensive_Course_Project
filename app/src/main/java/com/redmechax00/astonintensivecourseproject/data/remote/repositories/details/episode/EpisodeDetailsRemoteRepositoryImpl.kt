package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode

import android.util.Log
import com.redmechax00.astonintensivecourseproject.data.remote.models.details.toEpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.services.EpisodeDetailsService
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import retrofit2.HttpException
import javax.inject.Inject

class EpisodeDetailsRemoteRepositoryImpl @Inject constructor(
    private val episodeDetailsService: EpisodeDetailsService
) : EpisodeDetailsRemoteRepository {

    companion object {
        const val DEFAULT_DETAILS_ID = 0
    }

    override suspend fun getEpisodeDetails(id: Int?): RemoteDetailsResult<EpisodeDetailsDomainModel> {

        return try {
            val response = episodeDetailsService.getEpisodeDetails(id ?: DEFAULT_DETAILS_ID)
            Log.d("dddd", response.raw().request.url.toString())

            if (response.isSuccessful) {
                val details = response.body()?.toEpisodeDetailsDomainModel()
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