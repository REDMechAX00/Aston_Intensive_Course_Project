package com.redmechax00.astonintensivecourseproject.data.remote.repositories.details.character

import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult

interface AllCharactersDetailsRemoteRepository {

    suspend fun getAllCharactersDetails(
        listOfIds: List<Int?>
    ): RemoteDetailsResult<List<CharacterDetailsDomainModel>>
}