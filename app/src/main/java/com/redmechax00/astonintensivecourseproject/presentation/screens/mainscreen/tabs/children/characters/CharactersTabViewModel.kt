package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.domain.usecases.CharactersUseCase
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.model.CharacterUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.toCharacterUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class CharactersTabViewModel @Inject constructor(
    private val charactersUseCaseProvider: Provider<CharactersUseCase>
) : ViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val DEBOUNCE_TIMEOUT_MILLIS = 400L
    }

    private val _searchFilter =
        MutableStateFlow(CharactersFilterModel())
    val searchFilter: StateFlow<CharactersFilterModel> = _searchFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val characters: StateFlow<PagingData<CharacterUIModel>> = searchFilter
        .map(::newRemotePager)
        .debounce(timeoutMillis = DEBOUNCE_TIMEOUT_MILLIS)
        .flatMapLatest { pager -> pager.flow }
        .map { pagingData -> pagingData.map { domainModel -> domainModel.toCharacterUiModel() } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val localCharacters: StateFlow<PagingData<CharacterUIModel>> = searchFilter
            .map(::newLocalPager)
            .debounce(timeoutMillis = DEBOUNCE_TIMEOUT_MILLIS)
            .flatMapLatest { pager -> pager.flow }
            .map { pagingData -> pagingData.map { domainModel -> domainModel.toCharacterUiModel() } }
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newRemotePager(
        charactersSearchParams: CharactersFilterModel
    ): Pager<Int, CharacterDomainModel> {

        return Pager(PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false)) {
            val charactersUseCase = charactersUseCaseProvider.get()
            charactersUseCase.getRemoteCharacters(charactersSearchParams)
        }
    }

    private fun newLocalPager(
        charactersSearchParams: CharactersFilterModel
    ): Pager<Int, CharacterDomainModel> {

        return Pager(
            PagingConfig(
                NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            val charactersUseCase = charactersUseCaseProvider.get()
            charactersUseCase.getLocalCharacters(charactersSearchParams)
        }
    }

    fun refreshSearchFilter() {
        val savedSearchParams = searchFilter.value.copy()
        val refreshedSearchParams = searchFilter.value.copy()
        refreshedSearchParams.name = "${refreshedSearchParams.name} refresh"
        _searchFilter.value = refreshedSearchParams
        _searchFilter.value = savedSearchParams
    }

    fun updateSearchFilterName(name: String) {
        val updatedSearchParams = searchFilter.value.copy()
        if (name.isEmpty()) updatedSearchParams.name = null
        else updatedSearchParams.name = name
        _searchFilter.tryEmit(updatedSearchParams)
    }

    fun updateSearchParamsWithBottomSheetFilter(filter: CharactersFilterModel) {
        val updatedSearchFilter = searchFilter.value.copy()
        updatedSearchFilter.status = filter.status
        updatedSearchFilter.species = filter.species
        updatedSearchFilter.type = filter.type
        updatedSearchFilter.gender = filter.gender
        _searchFilter.tryEmit(updatedSearchFilter)
    }
}
