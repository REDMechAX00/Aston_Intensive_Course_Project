package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter

import android.os.Bundle
import android.view.View
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.FragmentLocationsTabBottomSheetBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BaseBottomSheetDialogFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.ISupportBottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.util.addAfterTextChangedListener
import com.redmechax00.astonintensivecourseproject.util.setNoSuggestionInputType

class LocationsFilterBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentLocationsTabBottomSheetBinding, LocationsFilterModel>(
        R.layout.fragment_locations_tab_bottom_sheet
    ), ISupportBottomSheetFilterCallback<LocationsFilterModel> {

    private var filterCallback: BottomSheetFilterCallback<LocationsFilterModel>? = null

    override fun setBottomSheetFilterCallback(
        bottomSheetFilterCallback: BottomSheetFilterCallback<LocationsFilterModel>?
    ) {
        filterCallback = bottomSheetFilterCallback
    }

    private val filterModel: LocationsFilterModel = LocationsFilterModel()

    override fun createBinding(view: View): FragmentLocationsTabBottomSheetBinding =
        FragmentLocationsTabBottomSheetBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()
        setListeners()
        updateDialogUI()
    }

    private fun setupFields() {
        with(binding) {
            fragmentLocationsTabBottomSheetTypeEditText.setNoSuggestionInputType()
            fragmentLocationsTabBottomSheetDimensionEditText.setNoSuggestionInputType()
        }
    }

    override fun updateFilterModel(updatedFilterModel: LocationsFilterModel) {
        filterModel.putAll(updatedFilterModel)
    }

    private fun updateDialogUI() {
        with(binding) {
            fragmentLocationsTabBottomSheetTypeEditText.setText(filterModel.type)
            fragmentLocationsTabBottomSheetDimensionEditText.setText(filterModel.dimension)
        }
    }

    private fun setListeners() {
        with(binding) {
            fragmentLocationsTabBottomSheetTypeEditText.addAfterTextChangedListener { text ->
                if (text.isEmpty()) filterModel.type = null
                else filterModel.type = text
            }

            fragmentLocationsTabBottomSheetDimensionEditText.addAfterTextChangedListener { text ->
                if (text.isEmpty()) filterModel.dimension = null
                else filterModel.dimension = text
            }

            fragmentLocationsTabBottomSheetBtnReset.setOnClickListener {
                resetFilter()
            }

            fragmentLocationsTabBottomSheetBtnApply.setOnClickListener {
                applyFilter(filterModel)
            }
        }
    }

    private fun resetFilter() {
        filterCallback?.onBottomSheetResetClick()
    }

    private fun applyFilter(filter: LocationsFilterModel) {
        filterCallback?.onBottomSheetApplyClick(filter)
    }
}