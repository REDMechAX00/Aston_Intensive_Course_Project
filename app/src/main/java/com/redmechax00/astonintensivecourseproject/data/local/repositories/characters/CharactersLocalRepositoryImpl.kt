package com.redmechax00.astonintensivecourseproject.data.local.repositories.characters

import androidx.paging.PagingSource
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.CharacterEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel
import javax.inject.Inject

class CharactersLocalRepositoryImpl @Inject constructor(
    private val charactersDao: AppDao,
    private val charactersLocalPagingSourceFactory: CharactersLocalPagingSource.Factory
) : CharactersLocalRepository {

    override fun getCharacters(
        searchParams: CharactersFilterModel
    ): PagingSource<Int, CharacterDomainModel> =
        charactersLocalPagingSourceFactory.create(searchParams)


    override suspend fun insertCharacters(charactersEntityList: List<CharacterEntity>) {
        charactersDao.insertCharacters(charactersEntityList)
    }
}