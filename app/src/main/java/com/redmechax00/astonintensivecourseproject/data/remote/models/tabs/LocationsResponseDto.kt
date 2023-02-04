package com.redmechax00.astonintensivecourseproject.data.remote.models.tabs

data class LocationsResponseDto(
    var info: InfoResponseDto,
    var results: List<LocationResponseDto>
) {

    data class InfoResponseDto(
        var count: Int,
        var pages: Int,
        var next: String?,
        var prev: String?
    )

    data class LocationResponseDto(
        var id: Int,
        var name: String,
        var type: String,
        var dimension: String,
        var residents: List<String>,
        var url: String,
        var created: String
    )
}
