package com.redmechax00.astonintensivecourseproject.data.local.repositories.characters

import android.content.res.Resources.NotFoundException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.toCharacterDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabViewModel.Companion.NETWORK_PAGE_SIZE
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharactersLocalPagingSource @AssistedInject constructor(
    private val charactersDao: AppDao,
    @Assisted(
        KEY_ASSISTED_CHARACTERS_SEARCH_PARAMS
    ) private val searchFilter: CharactersFilterModel,
) : PagingSource<Int, CharacterDomainModel>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val KEY_ASSISTED_CHARACTERS_SEARCH_PARAMS = "characters_search_params"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDomainModel> {
        val pageNumber = (params.key ?: INITIAL_PAGE_NUMBER)
        val charactersDomainList = charactersDao.getCharacters(
            offset = (pageNumber - INITIAL_PAGE_NUMBER) * NETWORK_PAGE_SIZE,
            limit = NETWORK_PAGE_SIZE,
            name = searchFilter.name,
            status = searchFilter.status,
            species = searchFilter.species,
            type = searchFilter.type,
            gender = searchFilter.gender
        ).map { it.toCharacterDomainModel() }

        val nextPageNumber =
            if (charactersDomainList.isEmpty()) null else pageNumber + 1
        val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

        return if (charactersDomainList.isNotEmpty()) {
            LoadResult.Page(
                data = charactersDomainList,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } else {
            LoadResult.Error(NotFoundException())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted(
                KEY_ASSISTED_CHARACTERS_SEARCH_PARAMS
            ) searchParams: CharactersFilterModel
        ): CharactersLocalPagingSource
    }
}