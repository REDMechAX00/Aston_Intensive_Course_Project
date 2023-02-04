package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.redmechax00.astonintensivecourseproject.MyApplication
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterBottomSheetDialog
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter.EpisodesFilterModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.model.EpisodeUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EpisodesTabFragment : BaseTabFragment<EpisodesTabViewModel>(
    EpisodesTabViewModel::class.java
), BottomSheetFilterCallback<EpisodesFilterModel> {

    private lateinit var adapter: EpisodesTabAdapter
    private lateinit var bottomSheetFilter: EpisodesFilterBottomSheetDialog

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication)
            .getAppComponent()
            .inject(this)
    }

    override fun initBottomSheetFilter() {
        bottomSheetFilter = EpisodesFilterBottomSheetDialog()
            .also { it.setBottomSheetFilterCallback(this) }
    }

    override fun setupAdapter(recyclerView: RecyclerView) {
        adapter = EpisodesTabAdapter { item -> onItemClicked(item) }
        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = EpisodesLoadingStateAdapter(adapter) {
                onGoOffline()
            }
        )
    }

    private fun onGoOffline() {
        startLocalLoading()
    }

    override fun observeViewModel() {
        tabViewModel.searchFilter
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchFilter)
            .launchIn(lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tabViewModel.episodes
                    .collectLatest(adapter::submitData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect { state ->
                updateUILoadState(state)
                if (state.refresh is LoadState.Error && adapter.itemCount == 0) {
                    onGoOffline()
                }
            }
        }
    }

    private fun startLocalLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tabViewModel.localEpisodes
                    .collectLatest(adapter::submitData)
            }
        }
    }

    private fun updateSearchFilter(searchFilter: EpisodesFilterModel) {
        bottomSheetFilter.updateFilterModel(searchFilter)
        updateSearchToolbar(searchFilter.name ?: "")
    }

    override fun showBottomSheetFilter() {
        bottomSheetFilter.show(parentFragmentManager, null)
    }

    override fun refreshSearching() {
        tabViewModel.refreshSearchFilter()
    }

    private fun onItemClicked(item: EpisodeUIModel) {
        startDetailsFragment(MainScreenFragment.TAG_FRAGMENT_EPISODE_DETAILS, item.id)
    }

    override fun doAfterToolbarSearchTextChanged(text: String) {
        tabViewModel.updateSearchFilterName(text)
    }

    override fun onBottomSheetResetClick() {
        tabViewModel.updateSearchFilterWithBottomSheet(EpisodesFilterModel())
    }

    override fun onBottomSheetApplyClick(filter: EpisodesFilterModel) {
        smartScrollToTop()
        tabViewModel.updateSearchFilterWithBottomSheet(filter)
        bottomSheetFilter.dismiss()
    }
}