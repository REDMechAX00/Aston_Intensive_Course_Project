package com.redmechax00.astonintensivecourseproject.domain.models.tab

data class EpisodeDomainModel(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String,
    var characters: List<String>,
    var url: String,
    var created: String
)
