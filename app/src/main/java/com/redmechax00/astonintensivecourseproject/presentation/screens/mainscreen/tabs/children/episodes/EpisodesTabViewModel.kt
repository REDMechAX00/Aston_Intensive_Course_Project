package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.domain.usecases.EpisodesUseCase
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.model.EpisodeUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.LocationsTabViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.toEpisodeUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class EpisodesTabViewModel @Inject constructor(
    private val episodesUseCaseProvider: Provider<EpisodesUseCase>
) : ViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val DEBOUNCE_TIMEOUT_MILLIS = 400L
    }

    private val _searchFilter = MutableStateFlow(EpisodesFilterModel())
    val searchFilter: StateFlow<EpisodesFilterModel> = _searchFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val episodes: StateFlow<PagingData<EpisodeUIModel>> = searchFilter
        .map(::newPager)
        .debounce(timeoutMillis = DEBOUNCE_TIMEOUT_MILLIS)
        .flatMapLatest { pager -> pager.flow }
        .map { pagingData -> pagingData.map { domainModel -> domainModel.toEpisodeUiModel() } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val localEpisodes: StateFlow<PagingData<EpisodeUIModel>> = searchFilter
        .map(::newLocalPager)
        .debounce(timeoutMillis = LocationsTabViewModel.DEBOUNCE_TIMEOUT_MILLIS)
        .flatMapLatest { pager -> pager.flow }
        .map { pagingData -> pagingData.map { domainModel -> domainModel.toEpisodeUiModel() } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(
        episodesFilter: EpisodesFilterModel
    ): Pager<Int, EpisodeDomainModel> {

        return Pager(PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false)) {
            val episodesUseCase = episodesUseCaseProvider.get()
            episodesUseCase.getRemoteEpisodes(episodesFilter)
        }
    }

    private fun newLocalPager(
        episodesFilter: EpisodesFilterModel
    ): Pager<Int, EpisodeDomainModel> {

        return Pager(
            PagingConfig(
                NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            val useCase = episodesUseCaseProvider.get()
            useCase.getLocalEpisodes(episodesFilter)
        }
    }

    fun refreshSearchFilter() {
        val savedSearchFilter = searchFilter.value.copy()
        val refreshedSearchFilter = searchFilter.value.copy()
        refreshedSearchFilter.name = "${refreshedSearchFilter.name} refresh"
        _searchFilter.value = refreshedSearchFilter
        _searchFilter.value = savedSearchFilter
    }

    fun updateSearchFilterName(name: String) {
        val updatedSearchFilter = searchFilter.value.copy()
        if (name.isEmpty()) updatedSearchFilter.name = null
        else updatedSearchFilter.name = name
        _searchFilter.tryEmit(updatedSearchFilter)
    }

    fun updateSearchFilterWithBottomSheet(filter: EpisodesFilterModel) {
        val updatedSearchFilter = searchFilter.value.copy()
        updatedSearchFilter.season = filter.season
        updatedSearchFilter.series = filter.series
        _searchFilter.tryEmit(updatedSearchFilter)
    }
}
