package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode

data class EpisodeDetailsUIModel(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String,
    var characters: List<String>,
    var url: String,
    var created: String
)
