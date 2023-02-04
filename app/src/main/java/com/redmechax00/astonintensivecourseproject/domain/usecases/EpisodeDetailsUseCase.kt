package com.redmechax00.astonintensivecourseproject.domain.usecases

import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode.EpisodeDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import javax.inject.Inject

class EpisodeDetailsUseCase @Inject constructor(
    private val episodeDetailsRemoteRepository: EpisodeDetailsRemoteRepository
) {

    suspend operator fun invoke(id: Int?): RemoteDetailsResult<EpisodeDetailsDomainModel> =
        episodeDetailsRemoteRepository.getEpisodeDetails(id)
}