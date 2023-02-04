package com.redmechax00.astonintensivecourseproject.domain.usecases

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.repositories.characters.CharactersLocalRepository
import com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.characters.CharactersRemoteRepository
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val charactersRemoteRepository: CharactersRemoteRepository,
    private val charactersLocalRepository: CharactersLocalRepository
) {

    fun getRemoteCharacters(
        searchFilter: CharactersFilterModel
    ): PagingSource<Int, CharacterDomainModel> =
        charactersRemoteRepository.getCharacters(searchFilter)

    fun getLocalCharacters(
        searchFilter: CharactersFilterModel
    ): PagingSource<Int, CharacterDomainModel> =
        charactersLocalRepository.getCharacters(searchFilter)
}