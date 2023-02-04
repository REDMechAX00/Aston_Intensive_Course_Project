package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.bottomsheetfilter

import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter.BaseBottomSheetFilterModel

data class CharactersFilterModel(
    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null
) : BaseBottomSheetFilterModel {
    override fun putAll(newData: BaseBottomSheetFilterModel) {
        newData as CharactersFilterModel
        name = newData.name
        status = newData.status
        species = newData.species
        type = newData.type
        gender = newData.gender
    }

    fun copy(): CharactersFilterModel = CharactersFilterModel(
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender
    )
}
