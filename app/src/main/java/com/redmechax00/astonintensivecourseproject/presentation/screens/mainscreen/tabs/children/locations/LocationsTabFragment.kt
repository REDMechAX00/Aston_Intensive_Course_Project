package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations

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
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterBottomSheetDialog
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter.LocationsFilterModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.model.LocationUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LocationsTabFragment : BaseTabFragment<LocationsTabViewModel>(
    LocationsTabViewModel::class.java
), BottomSheetFilterCallback<LocationsFilterModel> {

    private lateinit var adapter: LocationsTabAdapter
    private lateinit var bottomSheetFilter: LocationsFilterBottomSheetDialog

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication)
            .getAppComponent()
            .inject(this)
    }

    override fun initBottomSheetFilter() {
        bottomSheetFilter = LocationsFilterBottomSheetDialog()
            .also { it.setBottomSheetFilterCallback(this) }
    }

    override fun setupAdapter(recyclerView: RecyclerView) {
        adapter = LocationsTabAdapter { item -> onItemClicked(item) }
        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LocationsLoadingStateAdapter(adapter) {
                onGoOffline()
            }
        )
    }

    private fun onGoOffline() {
        startLocalLoading()
    }

    override fun onDestroyFragmentView() {
        bottomSheetFilter.setBottomSheetFilterCallback(null)
    }

    override fun observeViewModel() {
        tabViewModel.searchFilter
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateSearchFilter)
            .launchIn(lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tabViewModel.locations
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
                tabViewModel.localLocations
                    .collectLatest(adapter::submitData)
            }
        }
    }

    private fun updateSearchFilter(searchFilter: LocationsFilterModel) {
        bottomSheetFilter.updateFilterModel(searchFilter)
        updateSearchToolbar(searchFilter.name ?: "")
    }

    override fun showBottomSheetFilter() {
        bottomSheetFilter.show(parentFragmentManager, null)
    }

    override fun refreshSearching() {
        tabViewModel.refreshSearchFilter()
    }

    private fun onItemClicked(item: LocationUIModel) {
        startDetailsFragment(MainScreenFragment.TAG_FRAGMENT_LOCATION_DETAILS, item.id)
    }

    override fun doAfterToolbarSearchTextChanged(text: String) {
        tabViewModel.updateSearchFilterName(text)
    }

    override fun onBottomSheetResetClick() {
        tabViewModel.updateSearchFilterWithBottomSheet(LocationsFilterModel())
    }

    override fun onBottomSheetApplyClick(filter: LocationsFilterModel) {
        smartScrollToTop()
        tabViewModel.updateSearchFilterWithBottomSheet(filter)
        bottomSheetFilter.dismiss()
    }
}