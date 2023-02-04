package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter

import android.os.Bundle
import android.view.View
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.FragmentEpisodesTabBottomSheetBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BaseBottomSheetDialogFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BottomSheetFilterCallback
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.ISupportBottomSheetFilterCallback

class EpisodesFilterBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentEpisodesTabBottomSheetBinding, EpisodesFilterModel>(
        R.layout.fragment_episodes_tab_bottom_sheet
    ), ISupportBottomSheetFilterCallback<EpisodesFilterModel> {

    companion object {
        const val FILTER_SEASON_S01 = "S01"
        const val FILTER_SEASON_S02 = "S02"
        const val FILTER_SEASON_S03 = "S03"
        const val FILTER_SEASON_S04 = "S04"
        const val FILTER_SEASON_S05 = "S05"
        const val FILTER_SERIES_E01 = "E01"
        const val FILTER_SERIES_E02 = "E02"
        const val FILTER_SERIES_E03 = "E03"
        const val FILTER_SERIES_E04 = "E04"
        const val FILTER_SERIES_E05 = "E05"
        const val FILTER_SERIES_E06 = "E06"
        const val FILTER_SERIES_E07 = "E07"
        const val FILTER_SERIES_E08 = "E08"
        const val FILTER_SERIES_E09 = "E09"
        const val FILTER_SERIES_E10 = "E10"
        const val FILTER_SERIES_E11 = "E11"
    }

    private var filterCallback: BottomSheetFilterCallback<EpisodesFilterModel>? = null

    override fun setBottomSheetFilterCallback(
        bottomSheetFilterCallback: BottomSheetFilterCallback<EpisodesFilterModel>?
    ) {
        filterCallback = bottomSheetFilterCallback
    }

    private val filterModel: EpisodesFilterModel = EpisodesFilterModel()

    override fun createBinding(view: View): FragmentEpisodesTabBottomSheetBinding =
        FragmentEpisodesTabBottomSheetBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        updateDialogUI()
    }

    override fun updateFilterModel(updatedFilterModel: EpisodesFilterModel) {
        filterModel.putAll(updatedFilterModel)
        try {
            with(binding) {
                fragmentEpisodesTabBottomSheetSeasonText.text =
                    requireActivity().resources.getString(R.string.filter_episode_item_none)
                fragmentEpisodesTabBottomSheetSeriesText.text =
                    requireActivity().resources.getString(R.string.filter_episode_item_none)
            }
        } catch (_: Exception) {
        }
    }

    private fun updateDialogUI() {
        with(binding) {
            fragmentEpisodesTabBottomSheetSeasonText.text = filterModel.season
                ?: requireActivity().resources.getString(R.string.filter_episode_item_none)
            fragmentEpisodesTabBottomSheetSeriesText.text = filterModel.series
                ?: requireActivity().resources.getString(R.string.filter_episode_item_none)
        }
    }

    private fun setListeners() {
        with(binding) {
            fragmentEpisodesTabBottomSheetSeasonText.setOnClickListener { seasonItem ->
                showSeasonPopupMenu(anchor = seasonItem, R.menu.popup_menu_filter_season) {
                    fragmentEpisodesTabBottomSheetSeasonText.text =
                        if (filterModel.season.isNullOrEmpty()) {
                            requireActivity().resources.getString(R.string.filter_status_item_none)
                        } else {
                            filterModel.season
                        }
                }
            }

            fragmentEpisodesTabBottomSheetSeriesText.setOnClickListener { seriesItem ->
                showSeriesPopupMenu(anchor = seriesItem, R.menu.popup_menu_filter_episode) {
                    fragmentEpisodesTabBottomSheetSeriesText.text =
                        if (filterModel.series.isNullOrEmpty()) {
                            requireActivity().resources.getString(R.string.filter_gender_item_none)
                        } else {
                            filterModel.series
                        }
                }
            }

            fragmentEpisodesTabBottomSheetBtnReset.setOnClickListener {
                resetFilter()
            }

            fragmentEpisodesTabBottomSheetBtnApply.setOnClickListener {
                applyFilter(filterModel)
            }
        }
    }

    private fun showSeasonPopupMenu(anchor: View, menuRes: Int, onItemChosen: () -> Unit) {
        popupMenuWithItemCallback(anchor, menuRes) { menuItem ->
            filterModel.season =
                when (menuItem.itemId) {
                    R.id.popup_menu_filter_season_item_s01 -> FILTER_SEASON_S01
                    R.id.popup_menu_filter_season_item_s02 -> FILTER_SEASON_S02
                    R.id.popup_menu_filter_season_item_s03 -> FILTER_SEASON_S03
                    R.id.popup_menu_filter_season_item_s04 -> FILTER_SEASON_S04
                    R.id.popup_menu_filter_season_item_s05 -> FILTER_SEASON_S05
                    else -> null
                }
            onItemChosen()
        }
    }

    private fun showSeriesPopupMenu(anchor: View, menuRes: Int, onItemChosen: () -> Unit) {
        popupMenuWithItemCallback(anchor, menuRes) { menuItem ->
            filterModel.series =
                when (menuItem.itemId) {
                    R.id.popup_menu_filter_episode_item_e01 -> FILTER_SERIES_E01
                    R.id.popup_menu_filter_episode_item_e02 -> FILTER_SERIES_E02
                    R.id.popup_menu_filter_episode_item_e03 -> FILTER_SERIES_E03
                    R.id.popup_menu_filter_episode_item_e04 -> FILTER_SERIES_E04
                    R.id.popup_menu_filter_episode_item_e05 -> FILTER_SERIES_E05
                    R.id.popup_menu_filter_episode_item_e06 -> FILTER_SERIES_E06
                    R.id.popup_menu_filter_episode_item_e07 -> FILTER_SERIES_E07
                    R.id.popup_menu_filter_episode_item_e08 -> FILTER_SERIES_E08
                    R.id.popup_menu_filter_episode_item_e09 -> FILTER_SERIES_E09
                    R.id.popup_menu_filter_episode_item_e10 -> FILTER_SERIES_E10
                    R.id.popup_menu_filter_episode_item_e11 -> FILTER_SERIES_E11
                    else -> null
                }
            onItemChosen()
        }
    }

    private fun resetFilter() {
        filterCallback?.onBottomSheetResetClick()
    }

    private fun applyFilter(filter: EpisodesFilterModel) {
        filterCallback?.onBottomSheetApplyClick(filter)
    }
}