package com.redmechax00.astonintensivecourseproject.data.remote.models.details

data class LocationDetailsResponseDto(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>,
    var url: String,
    var created: String
)