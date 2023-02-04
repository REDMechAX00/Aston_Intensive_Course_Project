package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.domain.usecases.LocationsUseCase
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabViewModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.model.LocationUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.toLocationUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class LocationsTabViewModel @Inject constructor(
    private val locationsUseCaseProvider: Provider<LocationsUseCase>
) : ViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val DEBOUNCE_TIMEOUT_MILLIS = 400L
    }

    private val _searchFilter = MutableStateFlow(LocationsFilterModel())
    val searchFilter: StateFlow<LocationsFilterModel> = _searchFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val locations: StateFlow<PagingData<LocationUIModel>> = searchFilter
        .map(::newPager)
        .debounce(timeoutMillis = DEBOUNCE_TIMEOUT_MILLIS)
        .flatMapLatest { pager -> pager.flow }
        .map { pagingData -> pagingData.map { domainModel -> domainModel.toLocationUiModel() } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val localLocations: StateFlow<PagingData<LocationUIModel>> = searchFilter
        .map(::newLocalPager)
        .debounce(timeoutMillis = DEBOUNCE_TIMEOUT_MILLIS)
        .flatMapLatest { pager -> pager.flow }
        .map { pagingData -> pagingData.map { domainModel -> domainModel.toLocationUiModel() } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(
        locationsFilter: LocationsFilterModel
    ): Pager<Int, LocationDomainModel> {

        return Pager(PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false)) {
            val locationsUseCase = locationsUseCaseProvider.get()
            locationsUseCase.getRemoteLocations(locationsFilter)
        }
    }

    private fun newLocalPager(
        locationsFilter: LocationsFilterModel
    ): Pager<Int, LocationDomainModel> {

        return Pager(
            PagingConfig(
                CharactersTabViewModel.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            val locationsUseCase = locationsUseCaseProvider.get()
            locationsUseCase.getLocalLocations(locationsFilter)
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

    fun updateSearchFilterWithBottomSheet(filter: LocationsFilterModel) {
        val updatedSearchFilter = searchFilter.value.copy()
        updatedSearchFilter.type = filter.type
        updatedSearchFilter.dimension = filter.dimension
        _searchFilter.tryEmit(updatedSearchFilter)
    }
}
