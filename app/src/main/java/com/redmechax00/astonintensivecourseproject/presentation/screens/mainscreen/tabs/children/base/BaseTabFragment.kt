package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.FragmentBaseTabBinding
import com.redmechax00.astonintensivecourseproject.databinding.FragmentBaseTabSearchToolbarLayoutBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.base.BaseFragment
import com.redmechax00.astonintensivecourseproject.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseTabFragment<VM : ViewModel>(private val viewModelClass: Class<VM>) :
    BaseFragment<FragmentBaseTabBinding>(R.layout.fragment_base_tab) {

    private var _tabViewModel: VM? = null
    protected val tabViewModel
        get() = requireNotNull(_tabViewModel) { "$viewModelClass ViewModel isn't init" }

    override fun createBinding(view: View): FragmentBaseTabBinding =
        FragmentBaseTabBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()
        setListeners()
        observeViewModel()
    }

    override fun onDestroyFragmentView() {
        binding.baseTabRecyclerView.adapter = null
    }

    private fun setupFields() {
        _tabViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[viewModelClass]

        initBottomSheetFilter()

        with(binding) {
            baseTabRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            (baseTabRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
            setupAdapter(baseTabRecyclerView)

            with(baseTabSearchToolbarContainer) {
                baseTabSearchToolbarEditTextSearch.setNoSuggestionInputType()
            }
        }
    }

    abstract fun initBottomSheetFilter()

    abstract fun setupAdapter(recyclerView: RecyclerView)

    abstract fun observeViewModel()

    abstract fun showBottomSheetFilter()

    protected fun updateUILoadState(state: CombinedLoadStates) {
        with(binding) {
            baseTabRecyclerView.isVisible = state.refresh != LoadState.Loading
            baseTabSwipeRefresh.isRefreshing = state.refresh == LoadState.Loading
        }
    }

    protected fun updateSearchToolbar(searchText: String) {
        withToolbarBinding {
            with(baseTabSearchToolbarEditTextSearch) {
                if ((text?.toString() ?: "") != searchText) {
                    setText(searchText)
                }
            }
        }
    }

    protected fun smartScrollToTop() {
        with(binding.baseTabRecyclerView) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                delay(200)
                if (this@with.size > 1) {
                    this@with.layoutManager?.scrollToPosition(0)
                }
            }
        }
    }

    protected fun startDetailsFragment(fragmentTag: String, detailsId: Int) {
        mainScreenSharedViewModel.onStartDetailsFragment(fragmentTag, detailsId)
    }

    private fun setListeners() {
        withToolbarBinding {
            baseTabSearchToolbarTextSearch.setOnClickListener {
                startSearching()
            }

            baseTabSearchToolbarBtnSearch.setOnClickListener {
                startSearching()
            }

            baseTabSearchToolbarBtnCancel.setOnClickListener {
                cancelSearching()
            }

            baseTabSearchToolbarBtnClear.setOnClickListener {
                binding.baseTabSearchToolbarContainer.baseTabSearchToolbarEditTextSearch.clearText()
            }

            baseTabSearchToolbarBtnFilter.setOnClickListener {
                showBottomSheetFilter()
            }

            baseTabSearchToolbarEditTextSearch.addAfterTextChangedListener { text ->
                updateBtnClearVisibility(text)
                doAfterToolbarSearchTextChanged(text)
            }
        }

        with(binding) {
            baseTabSwipeRefresh.setOnRefreshListener {
                refreshSearching()
            }
        }
    }

    abstract fun refreshSearching()

    abstract fun doAfterToolbarSearchTextChanged(text: String)

    private fun startSearching() {
        withToolbarBinding {
            baseTabSearchToolbarTextSearch.gone()
            baseTabSearchToolbarBtnSearch.gone()
            updateBtnClearVisibility(baseTabSearchToolbarEditTextSearch.text.toString())
            baseTabSearchToolbarBtnCancel.show()
            baseTabSearchToolbarEditTextSearch.run {
                show()
                requestFocus()
                showKeyboard(requireContext())
            }
        }
    }

    private fun cancelSearching() {
        withToolbarBinding {
            baseTabSearchToolbarBtnCancel.gone()
            baseTabSearchToolbarBtnClear.gone()
            baseTabSearchToolbarTextSearch.show()
            baseTabSearchToolbarBtnSearch.show()
            baseTabSearchToolbarEditTextSearch.run {
                gone()
                clearText()
                hideKeyboard(requireContext())
            }
        }
    }

    private fun updateBtnClearVisibility(text: String) {
        withToolbarBinding {
            with(baseTabSearchToolbarBtnClear) {
                if (text.isEmpty()) hide()
                else show()
            }
        }
    }

    private fun View.hideKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }

    private fun View.showKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun EditText.clearText() {
        setText("")
    }

    private fun withToolbarBinding(
        run: FragmentBaseTabSearchToolbarLayoutBinding.() -> Unit
    ) {
        with(binding.baseTabSearchToolbarContainer) { run(this) }
    }
}