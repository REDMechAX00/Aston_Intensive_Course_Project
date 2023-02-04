package com.redmechax00.astonintensivecourseproject.domain.usecases

import com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character.CharacterDetailsRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import javax.inject.Inject

class CharacterDetailsUseCase @Inject constructor(
    private val characterDetailsRemoteRepository: CharacterDetailsRemoteRepository
) {

    suspend operator fun invoke(id: Int?): RemoteDetailsResult<CharacterDetailsDomainModel> =
        characterDetailsRemoteRepository.getCharacterDetails(id)
}