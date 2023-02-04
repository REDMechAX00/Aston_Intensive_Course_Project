package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location

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
import com.redmechax00.astonintensivecourseproject.databinding.FragmentLocationDetailsBinding
import com.redmechax00.astonintensivecourseproject.di.modules.NetworkModule.Companion.BASE_URL
import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment.Companion.TAG_EXTRA_DETAILS_ID
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.RemoteDetailsResult
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsCharacterPreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.toBaseDetailsCharacterPreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.toLocationDetailsUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationDetailsFragment :
    BaseDetailsFragment<FragmentLocationDetailsBinding, LocationDetailsViewModel>(
        R.layout.fragment_location_details,
        LocationDetailsViewModel::class.java
    ) {

    private lateinit var residentsAdapter: LocationDetailsResidentsAdapter

    override fun createBinding(view: View): FragmentLocationDetailsBinding =
        FragmentLocationDetailsBinding.bind(view)

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication)
            .getAppComponent()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        val detailsId = arguments?.getInt(TAG_EXTRA_DETAILS_ID)
        detailsViewModel.initLocationId(detailsId)
    }


    private fun initFields() {
        with(binding.locationDetailsRecyclerViewResidents) {
            layoutManager = LinearLayoutManager(requireContext())
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            residentsAdapter = LocationDetailsResidentsAdapter { item -> onItemClicked(item) }
            adapter = residentsAdapter
        }
    }

    private fun onItemClicked(item: BaseDetailsCharacterPreviewUIModel) {
        startDetailsFragment(MainScreenFragment.TAG_FRAGMENT_CHARACTER_DETAILS, item.id)
    }

    override fun onDestroyFragmentView() {
        detailsViewModel.clearLocationId()
        detailsViewModel.clearResidents()
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
                    .collectLatest(::handleResidentsResult)
            }
        }
    }

    override fun setListeners() {
        binding.locationDetailsToolbarBtnBack
            .setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun handleResult(result: RemoteDetailsResult<LocationDetailsDomainModel>) {
        when (result.status) {
            RemoteDetailsResult.ResultStatus.SUCCESS -> {
                val domainData = result.data
                if (domainData != null) {
                    val uiData = domainData.toLocationDetailsUIModel()
                    val residentsIds = mutableListOf<Int?>()
                    val pathWithoutId = "$BASE_URL$PATH_SEGMENT_CHARACTER"

                    uiData.residents.forEach {
                        residentsIds.add(getIdFromDetailsUrl(pathWithoutId, pathWithId = it))
                    }
                    detailsViewModel.initResidents(residentsIds)
                    updateUIWithData(uiData)
                } /*else {

                }*/
            }
            RemoteDetailsResult.ResultStatus.ERROR -> {
                //val error = result.e
            }
        }
    }

    private fun handleResidentsResult(result: RemoteDetailsResult<List<CharacterDetailsDomainModel>>) {
        when (result.status) {
            RemoteDetailsResult.ResultStatus.SUCCESS -> {
                val domainData = result.data
                if (domainData != null) {
                    val uiData = domainData.map { it.toBaseDetailsCharacterPreviewUIModel() }
                    residentsAdapter.submitList(uiData)
                } /*else {

                }*/
            }
            RemoteDetailsResult.ResultStatus.ERROR -> {
                //val error = result.e
            }
        }
    }

    private fun updateUIWithData(uiData: LocationDetailsUIModel) {
        with(binding) {
            locationDetailsCollapsingToolbar.title = uiData.name
            locationDetailsCollapsingToolbarType.text = uiData.type
            locationDetailsCollapsingToolbarDimension.text = uiData.dimension
        }
    }
}