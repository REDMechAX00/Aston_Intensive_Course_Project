package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.redmechax00.astonintensivecourseproject.MyApplication
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.data.remote.services.CharactersService.Companion.PATH_SEGMENT_CHARACTER
import com.redmechax00.astonintensivecourseproject.databinding.FragmentEpisodeDetailsBinding
import com.redmechax00.astonintensivecourseproject.di.modules.NetworkModule.Companion.BASE_URL
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsCharacterPreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.toBaseDetailsCharacterPreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.toEpisodeDetailsUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeDetailsFragment :
    BaseDetailsFragment<FragmentEpisodeDetailsBinding, EpisodeDetailsViewModel>(
        R.layout.fragment_episode_details,
        EpisodeDetailsViewModel::class.java
    ) {

    private lateinit var charactersAdapter: EpisodeDetailsCharactersAdapter

    override fun createBinding(view: View): FragmentEpisodeDetailsBinding =
        FragmentEpisodeDetailsBinding.bind(view)

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication)
            .getAppComponent()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        val detailsId = arguments?.getInt(MainScreenFragment.TAG_EXTRA_DETAILS_ID)
        detailsViewModel.initEpisodeId(detailsId)
    }

    private fun initFields() {
        with(binding.episodeDetailsRecyclerViewCharacters) {
            layoutManager = LinearLayoutManager(requireContext())
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            charactersAdapter = EpisodeDetailsCharactersAdapter { item -> onItemClicked(item) }
            adapter = charactersAdapter
        }
    }

    private fun onItemClicked(item: BaseDetailsCharacterPreviewUIModel) {
        startDetailsFragment(MainScreenFragment.TAG_FRAGMENT_CHARACTER_DETAILS, item.id)
    }

    override fun onDestroyFragmentView() {
        detailsViewModel.clearEpisodeId()
        detailsViewModel.clearCharacters()
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.details
                    .collectLatest(::handleResult)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.residents
                    .collectLatest(::handleCharactersResult)
            }
        }
    }

    override fun setListeners() {
        binding.episodeDetailsToolbarBtnBack
            .setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun handleResult(result: RemoteDetailsResult<EpisodeDetailsDomainModel>) {
        when (result.status) {
            RemoteDetailsResult.ResultStatus.SUCCESS -> {
                val domainData = result.data
                if (domainData != null) {
                    val uiData = domainData.toEpisodeDetailsUIModel()
                    val charactersIds = mutableListOf<Int?>()
                    val pathWithoutId = "$BASE_URL$PATH_SEGMENT_CHARACTER"

                    uiData.characters.forEach {
                        charactersIds.add(getIdFromDetailsUrl(pathWithoutId, pathWithId = it))
                    }
                    detailsViewModel.initCharacters(charactersIds)
                    updateUIWithData(uiData)
                }/* else {

                }*/
            }
            RemoteDetailsResult.ResultStatus.ERROR -> {
                //val error = result.e
            }
        }
    }

    private fun handleCharactersResult(result: RemoteDetailsResult<List<CharacterDetailsDomainModel>>) {
        when (result.status) {
            RemoteDetailsResult.ResultStatus.SUCCESS -> {
                val domainData = result.data
                if (domainData != null) {
                    val uiData = domainData.map { it.toBaseDetailsCharacterPreviewUIModel() }
                    charactersAdapter.submitList(uiData)
                } /*else {

                }*/
            }
            RemoteDetailsResult.ResultStatus.ERROR -> {
                //val error = result.e
            }
        }
    }

    private fun updateUIWithData(uiData: EpisodeDetailsUIModel) {
        with(binding) {
            episodeDetailsCollapsingToolbar.title = uiData.name
            episodeDetailsCollapsingToolbarAirDate.text = uiData.airDate
            episodeDetailsCollapsingToolbarEpisode.text = uiData.episode
        }
    }
}