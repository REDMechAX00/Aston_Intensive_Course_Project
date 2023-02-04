package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode

import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult

interface AllEpisodesDetailsRemoteRepository {

    suspend fun getAllEpisodesDetails(
        listOfIds: List<Int?>
    ): RemoteDetailsResult<List<EpisodeDetailsDomainModel>>
}