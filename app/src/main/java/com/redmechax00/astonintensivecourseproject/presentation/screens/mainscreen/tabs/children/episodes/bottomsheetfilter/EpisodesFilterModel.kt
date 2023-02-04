package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.bottomsheetfilter

import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BaseBottomSheetFilterModel

data class EpisodesFilterModel(
    var name: String? = null,
    var season: String? = null,
    var series: String? = null
) : BaseBottomSheetFilterModel {

    override fun putAll(newData: BaseBottomSheetFilterModel) {
        newData as EpisodesFilterModel
        name = newData.name
        season = newData.season
        series = newData.series
    }
}
