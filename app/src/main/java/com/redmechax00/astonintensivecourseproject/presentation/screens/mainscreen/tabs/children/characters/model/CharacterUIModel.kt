package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.model

import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabItemModel

data class CharacterUIModel(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var gender: String,
    var image: String
) : BaseTabItemModel
