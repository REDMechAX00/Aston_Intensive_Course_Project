package com.redmechax00.astonintensivecourseproject.data.remote.models.details

import com.google.gson.annotations.SerializedName

data class AllEpisodesDetailsResponseDto(
    var id: Int,
    var name: String,
    @SerializedName("air_date") var airDate: String,
    var episode: String,
    var characters: List<String>,
    var url: String,
    var created: String
)