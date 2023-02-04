package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.model

import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabItemModel

data class EpisodeUIModel(
    var id: Int,
    var name: String,
    var episode: String,
    var airDate: String
) : BaseTabItemModel
