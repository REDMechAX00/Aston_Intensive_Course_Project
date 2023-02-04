package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.usecases.AllCharactersDetailsUseCase
import com.redmechax00.astonintensivecourseproject.domain.usecases.EpisodeDetailsUseCase
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class EpisodeDetailsViewModel @Inject constructor(
    private val episodeDetailsUseCaseProvider: Provider<EpisodeDetailsUseCase>,
    private val allCharactersDetailsUseCaseProvider: Provider<AllCharactersDetailsUseCase>
) : ViewModel() {

    private val searchEpisodeId: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val details: StateFlow<RemoteDetailsResult<EpisodeDetailsDomainModel>> =
        searchEpisodeId.asStateFlow()
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

    private suspend fun getDetails(episodeId: Int?): RemoteDetailsResult<EpisodeDetailsDomainModel> {
        val episodeDetailsUseCase = episodeDetailsUseCaseProvider.get()
        return episodeDetailsUseCase(episodeId)
    }

    private suspend fun getAllCharacters(
        charactersIds: List<Int?>
    ): RemoteDetailsResult<List<CharacterDetailsDomainModel>> {
        val allCharactersDetailsUseCase = allCharactersDetailsUseCaseProvider.get()
        return allCharactersDetailsUseCase(charactersIds)
    }

    fun initEpisodeId(id: Int?) {
        searchEpisodeId.tryEmit(id)
    }

    fun clearEpisodeId() {
        searchEpisodeId.tryEmit(null)
    }

    fun initCharacters(listOfIds: List<Int?>) {
        residentsFlow.tryEmit(listOfIds)
    }

    fun clearCharacters() {
        residentsFlow.tryEmit(listOf())
    }
}