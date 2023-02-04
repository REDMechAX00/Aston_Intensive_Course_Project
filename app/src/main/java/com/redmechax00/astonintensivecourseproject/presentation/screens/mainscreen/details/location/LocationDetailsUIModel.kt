package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location

data class LocationDetailsUIModel(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>,
    var url: String,
    var created: String
)
