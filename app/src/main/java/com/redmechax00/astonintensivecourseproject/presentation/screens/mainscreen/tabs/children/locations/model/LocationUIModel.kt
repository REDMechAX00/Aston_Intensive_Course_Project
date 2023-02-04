package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.model

import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabItemModel

data class LocationUIModel(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String
) : BaseTabItemModel
