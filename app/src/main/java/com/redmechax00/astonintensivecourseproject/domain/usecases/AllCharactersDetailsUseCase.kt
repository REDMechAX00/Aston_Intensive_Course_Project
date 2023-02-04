package com.redmechax00.astonintensivecourseproject.domain.usecases

import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character.AllCharactersDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import javax.inject.Inject

class AllCharactersDetailsUseCase @Inject constructor(
    private val allCharactersDetailsRemoteRepository: AllCharactersDetailsRemoteRepository
) {

    suspend operator fun invoke(listOfIds: List<Int?>): RemoteDetailsResult<List<CharacterDetailsDomainModel>> =
        allCharactersDetailsRemoteRepository.getAllCharactersDetails(listOfIds)
}