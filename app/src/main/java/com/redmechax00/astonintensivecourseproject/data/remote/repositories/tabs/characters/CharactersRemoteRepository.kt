package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.characters

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel

interface CharactersRemoteRepository {
    fun getCharacters(
        searchFilter: CharactersFilterModel
    ): PagingSource<Int, CharacterDomainModel>
}