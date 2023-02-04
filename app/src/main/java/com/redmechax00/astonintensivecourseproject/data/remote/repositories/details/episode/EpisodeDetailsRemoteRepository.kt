package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode

import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult

interface EpisodeDetailsRemoteRepository {

    suspend fun getEpisodeDetails(id: Int?): RemoteDetailsResult<EpisodeDetailsDomainModel>
}