package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.view.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.appbar.AppBarLayout
import com.redmechax00.astonintensivecourseproject.MyApplication
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.data.remote.services.EpisodesService.Companion.PATH_SEGMENT_EPISODE
import com.redmechax00.astonintensivecourseproject.databinding.FragmentCharacterDetailsBinding
import com.redmechax00.astonintensivecourseproject.di.modules.NetworkModule.Companion.BASE_URL
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsEpisodePreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.toBaseDetailsEpisodePreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.toCharacterDetailsUIModel
import com.redmechax00.astonintensivecourseproject.util.setMargins
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class CharacterDetailsFragment :
    BaseDetailsFragment<FragmentCharacterDetailsBinding, CharacterDetailsViewModel>(
        R.layout.fragment_character_details,
        CharacterDetailsViewModel::class.java
    ) {

    private lateinit var episodesAdapter: CharacterDetailsEpisodesAdapter

    override fun createBinding(view: View): FragmentCharacterDetailsBinding =
        FragmentCharacterDetailsBinding.bind(view)

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication).getAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        val detailsId = arguments?.getInt(MainScreenFragment.TAG_EXTRA_DETAILS_ID)
        detailsViewModel.initCharacterId(detailsId)
    }

    private fun initFields() {
        with(binding.characterDetailsRecyclerViewEpisodes) {
            layoutManager = LinearLayoutManager(requireContext())
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            episodesAdapter = CharacterDetailsEpisodesAdapter { item -> onItemClicked(item) }
            adapter = episodesAdapter
        }
    }

    private fun onItemClicked(item: BaseDetailsEpisodePreviewUIModel) {
        startDetailsFragment(MainScreenFragment.TAG_FRAGMENT_EPISODE_DETAILS, item.id)
    }

    override fun onDestroyFragmentView() {
        detailsViewModel.clearCharacterId()
        detailsViewModel.clearEpisodes()
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
                detailsViewModel.episodes
                    .collectLatest(::handleEpisodesResult)
            }
        }
    }

    override fun setListeners() {
        with(binding) {
            var lastVerticalOffset = 0
            val initialTitleTextSize = characterDetailsCollapsingToolbarTitle.textSize
            val initialTitleMarginLeft = characterDetailsCollapsingToolbarTitle.marginLeft
            val initialTitleMarginBottom = characterDetailsCollapsingToolbarTitle.marginBottom
            characterDetailsAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (lastVerticalOffset != verticalOffset) {
                    lastVerticalOffset = verticalOffset
                    onAppBarOffsetChanged(
                        appBarLayout,
                        verticalOffset,
                        initialTitleTextSize,
                        initialTitleMarginBottom,
                        initialTitleMarginLeft
                    )
                }
            }

            characterDetailsToolbarBtnBack
                .setOnClickListener { parentFragmentManager.popBackStack() }
        }
    }

    private fun handleResult(result: RemoteDetailsResult<CharacterDetailsDomainModel>) {
        when (result.status) {
            RemoteDetailsResult.ResultStatus.SUCCESS -> {
                val domainData = result.data
                if (domainData != null) {
                    val uiData = domainData.toCharacterDetailsUIModel()
                    val episodesIds = mutableListOf<Int?>()
                    val pathWithoutId = "$BASE_URL${PATH_SEGMENT_EPISODE}"
                    uiData.episode.forEach {
                        episodesIds.add(getIdFromDetailsUrl(pathWithoutId, pathWithId = it))
                    }
                    detailsViewModel.initEpisodes(episodesIds)
                    updateUIWithData(uiData)
                } /*else {

                }*/
            }
            RemoteDetailsResult.ResultStatus.ERROR -> {
                //val error = result.e
            }
        }
    }

    private fun handleEpisodesResult(result: RemoteDetailsResult<List<EpisodeDetailsDomainModel>>) {
        when (result.status) {
            RemoteDetailsResult.ResultStatus.SUCCESS -> {
                val domainData = result.data
                if (domainData != null) {
                    val uiData = domainData.map { it.toBaseDetailsEpisodePreviewUIModel() }
                    episodesAdapter.submitList(uiData)
                } /*else {

                }*/
            }
            RemoteDetailsResult.ResultStatus.ERROR -> {
                //val error = result.e
            }
        }
    }

    private fun updateUIWithData(uiData: CharacterDetailsUIModel) {
        with(binding) {
            characterDetailsCollapsingToolbarTitle.text = uiData.name
            characterDetailsSpecies.text = uiData.species
            characterDetailsStatus.text = uiData.status
            characterDetailsGender.text = uiData.gender
            characterDetailsType.text = uiData.type.ifEmpty {
                requireContext().getString(R.string.text_details_item_empty)
            }
            characterDetailsOriginName.text = uiData.origin.name
            characterDetailsLocationName.text = uiData.location.name

            loadImageAndDominantColor(imageUrl = uiData.image) { bitmap, dominantColor ->
                characterDetailsImage.setImageBitmap(
                    bitmap.cropCenter(characterDetailsImage).setBottomAlphaGradientFrame()
                )
                characterDetailsCollapsingToolbar.setContentScrimColor(dominantColor)
                characterDetailsBackground.setBackgroundColor(dominantColor)
                requireActivity().window.statusBarColor = dominantColor
            }

           /* val originId = getIdFromDetailsUrl(
                pathWithId = uiData.origin.url,
                pathWithoutId = "$BASE_URL/origin"
            )
            val locationId = getIdFromDetailsUrl(
                pathWithId = uiData.location.url,
                pathWithoutId = "$BASE_URL/location"
            )*/
            val listOfEpisodesId = mutableListOf<Int?>()
            uiData.episode.forEach { episodeUrl ->
                listOfEpisodesId.add(
                    getIdFromDetailsUrl(
                        pathWithId = episodeUrl,
                        pathWithoutId = "$BASE_URL/episode"
                    )
                )
            }
        }
    }

    private fun onAppBarOffsetChanged(
        appBarLayout: AppBarLayout,
        verticalOffset: Int,
        initialTitleTextSize: Float,
        initialTitleMarginLeft: Int,
        initialTitleMarginBottom: Int
    ) {
        val totalScrollRange = appBarLayout.totalScrollRange.toFloat()

        with(binding) {
            val titleTextSize = initialTitleTextSize -
                    (initialTitleTextSize * 0.3f / totalScrollRange * abs(verticalOffset).toFloat())

            characterDetailsCollapsingToolbarTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                titleTextSize
            )

            val leftTitleMargin =
                initialTitleMarginLeft.toFloat() +
                        ((characterDetailsToolbarBtnBack.width +
                                characterDetailsToolbarBtnBack.marginStart
                                - initialTitleMarginLeft) / totalScrollRange
                                * abs(verticalOffset).toFloat())

            val marginBottomForCentering = ((characterDetailsToolbar.height -
                    characterDetailsCollapsingToolbarTitle.getLargestCharacterHeight()) / 2f) /
                    totalScrollRange * abs(verticalOffset).toFloat()

            val bottomTitleMargin =
                initialTitleMarginBottom.toFloat() -
                        (initialTitleMarginBottom.toFloat() /
                                totalScrollRange * abs(verticalOffset).toFloat())

            when (abs(verticalOffset)) {
                in (totalScrollRange * 0.7f).toInt()..totalScrollRange.toInt() -> {
                    characterDetailsCollapsingToolbarTitle.maxLines = 1
                }
                else -> characterDetailsCollapsingToolbarTitle.maxLines = 3
            }

            characterDetailsCollapsingToolbarTitle.setMargins(
                left = leftTitleMargin.toInt(),
                top = characterDetailsCollapsingToolbarTitle.marginTop,
                right = characterDetailsCollapsingToolbarTitle.marginRight,
                bottom = bottomTitleMargin.toInt() + marginBottomForCentering.toInt()
            )
        }
    }

    private fun TextView.getLargestCharacterHeight(): Int {
        val metrics = this.paint.fontMetrics
        return (metrics.bottom - metrics.top).toInt()
    }
}