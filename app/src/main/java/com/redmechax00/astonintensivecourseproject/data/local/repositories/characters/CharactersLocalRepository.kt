package com.redmechax00.astonintensivecourseproject.data.local.repositories.characters

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.entity.CharacterEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel

interface CharactersLocalRepository {

    fun getCharacters(
        searchParams: CharactersFilterModel
    ): PagingSource<Int, CharacterDomainModel>

    suspend fun insertCharacters(charactersEntityList: List<CharacterEntity>)
}