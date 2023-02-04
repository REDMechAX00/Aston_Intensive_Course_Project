package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter

import android.os.Bundle
import android.view.View
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.FragmentCharactersTabBottomSheetBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BaseBottomSheetDialogFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.ISupportBottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.util.addAfterTextChangedListener
import com.redmechax00.astonintensivecourseproject.util.setNoSuggestionInputType

class CharactersFilterBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentCharactersTabBottomSheetBinding, CharactersFilterModel>(
        R.layout.fragment_characters_tab_bottom_sheet
    ), ISupportBottomSheetFilterCallback<CharactersFilterModel> {

    companion object {
        const val FILTER_STATUS_ALIVE = "Alive"
        const val FILTER_STATUS_DEAD = "Dead"
        const val FILTER_STATUS_UNKNOWN = "Unknown"
        const val FILTER_GENDER_FEMALE = "Female"
        const val FILTER_GENDER_MALE = "Male"
        const val FILTER_GENDER_GENDERLESS = "Genderless"
        const val FILTER_GENDER_UNKNOWN = "Unknown"
    }

    private var filterCallback: BottomSheetFilterCallback<CharactersFilterModel>? = null

    override fun setBottomSheetFilterCallback(
        bottomSheetFilterCallback: BottomSheetFilterCallback<CharactersFilterModel>?
    ) {
        filterCallback = bottomSheetFilterCallback
    }

    private val filterModel: CharactersFilterModel = CharactersFilterModel()

    override fun createBinding(view: View): FragmentCharactersTabBottomSheetBinding =
        FragmentCharactersTabBottomSheetBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFields()
        setListeners()
        updateDialogUI()
    }

    private fun setupFields() {
        with(binding) {
            fragmentCharactersTabBottomSheetSpeciesEditText.setNoSuggestionInputType()
            fragmentCharactersTabBottomSheetTypeEditText.setNoSuggestionInputType()
        }
    }

    override fun updateFilterModel(updatedFilterModel: CharactersFilterModel) {
        filterModel.putAll(updatedFilterModel)
        try {
            with(binding) {
                fragmentCharactersTabBottomSheetStatusText.text =
                    requireActivity().resources.getString(R.string.filter_status_item_none)
                fragmentCharactersTabBottomSheetSpeciesEditText.setText("")
                fragmentCharactersTabBottomSheetTypeEditText.setText("")
                fragmentCharactersTabBottomSheetGenderText.text =
                    requireActivity().resources.getString(R.string.filter_status_item_none)
            }
        } catch (_: Exception) {
        }
    }

    private fun updateDialogUI() {
        with(binding) {
            fragmentCharactersTabBottomSheetStatusText.text = filterModel.status
                ?: requireActivity().resources.getString(R.string.filter_status_item_none)
            fragmentCharactersTabBottomSheetSpeciesEditText.setText(filterModel.species)
            fragmentCharactersTabBottomSheetTypeEditText.setText(filterModel.type)
            fragmentCharactersTabBottomSheetGenderText.text = filterModel.gender
                ?: requireActivity().resources.getString(R.string.filter_gender_item_none)
        }
    }

    private fun setListeners() {
        with(binding) {
            fragmentCharactersTabBottomSheetStatusText.setOnClickListener { statusItem ->
                showStatusPopupMenu(anchor = statusItem, R.menu.popup_menu_filter_status) {
                    fragmentCharactersTabBottomSheetStatusText.text =
                        if (filterModel.status.isNullOrEmpty()) {
                            requireActivity().resources.getString(R.string.filter_status_item_none)
                        } else {
                            filterModel.status
                        }
                }
            }

            fragmentCharactersTabBottomSheetGenderText.setOnClickListener { genderItem ->
                showGenderPopupMenu(anchor = genderItem, R.menu.popup_menu_filter_gender) {
                    fragmentCharactersTabBottomSheetGenderText.text =
                        if (filterModel.gender.isNullOrEmpty()) {
                            requireActivity().resources.getString(R.string.filter_gender_item_none)
                        } else {
                            filterModel.gender
                        }
                }
            }

            fragmentCharactersTabBottomSheetSpeciesEditText
                .addAfterTextChangedListener { text ->
                    if (text.isEmpty()) filterModel.species = null
                    else filterModel.species = text
                }

            fragmentCharactersTabBottomSheetTypeEditText
                .addAfterTextChangedListener { text ->
                    if (text.isEmpty()) filterModel.type = null
                    else filterModel.type = text
                }


            fragmentCharactersTabBottomSheetBtnReset.setOnClickListener {
                resetFilter()
            }

            fragmentCharactersTabBottomSheetBtnApply.setOnClickListener {
                applyFilter(filterModel)
            }
        }
    }

    private fun showStatusPopupMenu(anchor: View, menuRes: Int, onItemChosen: () -> Unit) {
        popupMenuWithItemCallback(anchor, menuRes) { menuItem ->
            filterModel.status =
                when (menuItem.itemId) {
                    R.id.popup_menu_filter_status_item_alive -> FILTER_STATUS_ALIVE
                    R.id.popup_menu_filter_status_item_dead -> FILTER_STATUS_DEAD
                    R.id.popup_menu_filter_status_item_unknown -> FILTER_STATUS_UNKNOWN
                    else -> null
                }
            onItemChosen()
        }
    }

    private fun showGenderPopupMenu(anchor: View, menuRes: Int, onItemChosen: () -> Unit) {
        popupMenuWithItemCallback(anchor, menuRes) { menuItem ->
            filterModel.gender =
                when (menuItem.itemId) {
                    R.id.popup_menu_filter_gender_item_female -> FILTER_GENDER_FEMALE
                    R.id.popup_menu_filter_gender_item_male -> FILTER_GENDER_MALE
                    R.id.popup_menu_filter_gender_item_genderless -> FILTER_GENDER_GENDERLESS
                    R.id.popup_menu_filter_gender_item_unknown -> FILTER_GENDER_UNKNOWN
                    else -> null
                }
            onItemChosen()
        }
    }

    private fun resetFilter() {
        filterCallback?.onBottomSheetResetClick()
    }

    private fun applyFilter(filter: CharactersFilterModel) {
        filterCallback?.onBottomSheetApplyClick(filter)
    }
}