package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.usecases.AllCharactersDetailsUseCase
import com.redmechax00.astonintensivecourseproject.domain.usecases.LocationDetailsUseCase
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class LocationDetailsViewModel @Inject constructor(
    private val locationDetailsUseCaseProvider: Provider<LocationDetailsUseCase>,
    private val allCharactersDetailsUseCaseProvider: Provider<AllCharactersDetailsUseCase>,
) : ViewModel() {

    private val searchLocationId: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val details: StateFlow<RemoteDetailsResult<LocationDetailsDomainModel>> =
        searchLocationId.asStateFlow()
            .map(::getDetails)
            .debounce(timeoutMillis = 400)
            .flatMapLatest { value ->
                flow {
                    emit(value)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, RemoteDetailsResult.Success.empty())

    private val residentsFlow: MutableStateFlow<List<Int?>> = MutableStateFlow(listOf())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val residents: StateFlow<RemoteDetailsResult<List<CharacterDetailsDomainModel>>> =
        residentsFlow.asStateFlow()
            .map(::getAllCharacters)
            .debounce(timeoutMillis = 400)
            .flatMapLatest { value ->
                flow {
                    emit(value)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, RemoteDetailsResult.Success.empty())

    private suspend fun getDetails(locationId: Int?): RemoteDetailsResult<LocationDetailsDomainModel> {
        val locationDetailsUseCase = locationDetailsUseCaseProvider.get()
        return locationDetailsUseCase(locationId)
    }

    private suspend fun getAllCharacters(
        charactersIds: List<Int?>
    ): RemoteDetailsResult<List<CharacterDetailsDomainModel>> {
        val allCharactersDetailsUseCase = allCharactersDetailsUseCaseProvider.get()
        return allCharactersDetailsUseCase(charactersIds)
    }

    fun initLocationId(id: Int?) {
        searchLocationId.tryEmit(id)
    }

    fun clearLocationId() {
        searchLocationId.tryEmit(null)
    }

    fun initResidents(listOfIds: List<Int?>) {
        residentsFlow.tryEmit(listOfIds)
    }

    fun clearResidents() {
        residentsFlow.tryEmit(listOf())
    }
}