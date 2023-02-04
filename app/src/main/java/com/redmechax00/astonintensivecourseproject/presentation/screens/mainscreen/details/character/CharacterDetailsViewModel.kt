package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.usecases.AllEpisodesDetailsUseCase
import com.redmechax00.astonintensivecourseproject.domain.usecases.CharacterDetailsUseCase
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUseCaseProvider: Provider<CharacterDetailsUseCase>,
    private val allEpisodesDetailsUseCaseProvider: Provider<AllEpisodesDetailsUseCase>
) : ViewModel() {

    private val searchCharacterId: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val details: StateFlow<RemoteDetailsResult<CharacterDetailsDomainModel>> =
        searchCharacterId.asStateFlow()
            .map(::getDetails)
            .debounce(timeoutMillis = 400)
            .flatMapLatest { value ->
                flow {
                    emit(value)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, RemoteDetailsResult.Success.empty())

    private suspend fun getDetails(characterId: Int?): RemoteDetailsResult<CharacterDetailsDomainModel> {
        val characterDetailsUseCase = characterDetailsUseCaseProvider.get()
        return characterDetailsUseCase(characterId)
    }

    private val episodesFlow: MutableStateFlow<List<Int?>> = MutableStateFlow(listOf())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val episodes: StateFlow<RemoteDetailsResult<List<EpisodeDetailsDomainModel>>> =
        episodesFlow.asStateFlow()
            .map(::getAllEpisodes)
            .debounce(timeoutMillis = 400)
            .flatMapLatest { value ->
                flow {
                    emit(value)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, RemoteDetailsResult.Success.empty())

    private suspend fun getAllEpisodes(
        episodesIds: List<Int?>
    ): RemoteDetailsResult<List<EpisodeDetailsDomainModel>> {
        val allEpisodesDetailsUseCase = allEpisodesDetailsUseCaseProvider.get()
        return allEpisodesDetailsUseCase(episodesIds)
    }

    fun initCharacterId(id: Int?) {
        searchCharacterId.value = id
    }

    fun clearCharacterId() {
        searchCharacterId.value = null
    }

    fun initEpisodes(listOfIds: List<Int?>) {
        episodesFlow.tryEmit(listOfIds)
    }

    fun clearEpisodes() {
        episodesFlow.tryEmit(listOf())
    }
}