package com.redmechax00.astonintensivecourseproject.data.remote.models.tabs

import com.google.gson.annotations.SerializedName

data class EpisodesResponseDto(
    var info: InfoResponseDto,
    var results: List<EpisodeResponseDto>
) {

    data class InfoResponseDto(
        var count: Int,
        var pages: Int,
        var next: String?,
        var prev: String?
    )

    data class EpisodeResponseDto(
        var id: Int,
        var name: String,
        @SerializedName("air_date") var airDate: String,
        var episode: String,
        var characters: List<String>,
        var url: String,
        var created: String
    )
}
