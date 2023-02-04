package com.redmechax00.astonintensivecourseproject.domain.usecases

import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.episode.AllEpisodesDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import javax.inject.Inject

class AllEpisodesDetailsUseCase @Inject constructor(
    private val allEpisodesDetailsRemoteRepository: AllEpisodesDetailsRemoteRepository
) {

    suspend operator fun invoke(listOfIds: List<Int?>): RemoteDetailsResult<List<EpisodeDetailsDomainModel>> =
        allEpisodesDetailsRemoteRepository.getAllEpisodesDetails(listOfIds)
}