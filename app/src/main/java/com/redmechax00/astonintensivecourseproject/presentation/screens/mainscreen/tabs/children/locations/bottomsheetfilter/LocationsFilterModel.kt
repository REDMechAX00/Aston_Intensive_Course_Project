package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.bottomsheetfilter

import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BaseBottomSheetFilterModel

data class LocationsFilterModel(
    var name: String? = null,
    var type: String? = null,
    var dimension: String? = null
) : BaseBottomSheetFilterModel {

    override fun putAll(newData: BaseBottomSheetFilterModel) {
        newData as LocationsFilterModel
        name = newData.name
        type = newData.type
        dimension = newData.dimension
    }
}