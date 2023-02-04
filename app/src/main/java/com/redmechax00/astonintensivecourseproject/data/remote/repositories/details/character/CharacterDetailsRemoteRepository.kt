package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character

import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult

interface CharacterDetailsRemoteRepository {

    suspend fun getCharacterDetails(id: Int?): RemoteDetailsResult<CharacterDetailsDomainModel>
}