package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.redmechax00.astonintensivecourseproject.MyApplication
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment.Companion.TAG_FRAGMENT_CHARACTER_DETAILS
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterBottomSheetDialog
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter.CharactersFilterModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.model.CharacterUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CharactersTabFragment : BaseTabFragment<CharactersTabViewModel>(
    CharactersTabViewModel::class.java
), BottomSheetFilterCallback<CharactersFilterModel> {

    private lateinit var adapter: CharactersTabAdapter
    private lateinit var bottomSheetFilter: CharactersFilterBottomSheetDialog

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication)
            .getAppComponent()
            .inject(this)
    }

    override fun initBottomSheetFilter() {
        bottomSheetFilter = CharactersFilterBottomSheetDialog()
            .also { it.setBottomSheetFilterCallback(this) }
    }

    override fun setupAdapter(recyclerView: RecyclerView) {
        adapter = CharactersTabAdapter { item -> onItemClicked(item) }
        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = CharactersLoadingStateAdapter(adapter) {
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
                tabViewModel.characters
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
                tabViewModel.localCharacters
                    .collectLatest(adapter::submitData)
            }
        }
    }

    private fun updateSearchFilter(searchParams: CharactersFilterModel) {
        bottomSheetFilter.updateFilterModel(searchParams)
        updateSearchToolbar(searchParams.name ?: "")
    }

    override fun showBottomSheetFilter() {
        bottomSheetFilter.show(parentFragmentManager, null)
    }

    override fun refreshSearching() {
        tabViewModel.refreshSearchFilter()
    }

    private fun onItemClicked(item: CharacterUIModel) {
        startDetailsFragment(TAG_FRAGMENT_CHARACTER_DETAILS, item.id)
    }

    override fun doAfterToolbarSearchTextChanged(text: String) {
        tabViewModel.updateSearchFilterName(text)
    }

    override fun onBottomSheetResetClick() {
        tabViewModel.updateSearchParamsWithBottomSheetFilter(CharactersFilterModel())
    }

    override fun onBottomSheetApplyClick(filter: CharactersFilterModel) {
        smartScrollToTop()
        tabViewModel.updateSearchParamsWithBottomSheetFilter(filter)
        bottomSheetFilter.dismiss()
    }
}