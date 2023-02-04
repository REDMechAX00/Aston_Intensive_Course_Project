package com.redmechax00.astonintensivecourseproject.data.remote.repositories.tabs.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.redmechax00.astonintensivecourseproject.data.local.entity.CharacterEntity
import com.redmechax00.astonintensivecourseproject.data.local.repositories.characters.CharactersLocalRepository
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.toCharacterDomainModel
import com.redmechax00.astonintensivecourseproject.data.remote.models.tabs.toCharacterEntity
import com.redmechax00.astonintensivecourseproject.data.remote.services.CharactersService
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class CharactersRemotePagingSource @AssistedInject constructor(
    private val charactersService: CharactersService,
    @Assisted(
        KEY_ASSISTED_CHARACTERS_SEARCH_PARAMS
    ) private val searchFilter: CharactersFilterModel,
    private val charactersLocalRepository: CharactersLocalRepository
) : PagingSource<Int, CharacterDomainModel>() {

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
        const val KEY_ASSISTED_CHARACTERS_SEARCH_PARAMS = "characters_search_params"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDomainModel> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        try {
            val response = charactersService.getCharacters(
                page = pageNumber,
                name = searchFilter.name,
                status = searchFilter.status,
                species = searchFilter.species,
                type = searchFilter.type,
                gender = searchFilter.gender
            )

            return if (response.isSuccessful) {
                val charactersResponseDto = response.body()
                val charactersDomainList =
                    (charactersResponseDto?.results ?: listOf()).map { it.toCharacterDomainModel() }
                val nextPageNumber = if (charactersDomainList.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

                val charactersEntityList =
                    (charactersResponseDto?.results ?: listOf()).map { it.toCharacterEntity() }
                if (charactersEntityList.isNotEmpty()) {
                    saveToLocalStorage(charactersEntityList)
                }

                LoadResult.Page(
                    data = charactersDomainList,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun saveToLocalStorage(charactersEntityList: List<CharacterEntity>) {
        charactersLocalRepository.insertCharacters(charactersEntityList)
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
        ): CharactersRemotePagingSource
    }
}