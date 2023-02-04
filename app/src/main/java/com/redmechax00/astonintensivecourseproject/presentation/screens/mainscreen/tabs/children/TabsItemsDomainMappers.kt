package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children

import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.model.CharacterUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.model.EpisodeUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.model.LocationUIModel

internal fun CharacterDomainModel.toCharacterUiModel(): CharacterUIModel {
    return CharacterUIModel(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

internal fun LocationDomainModel.toLocationUiModel(): LocationUIModel {
    return LocationUIModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension
    )
}

internal fun EpisodeDomainModel.toEpisodeUiModel(): EpisodeUIModel {
    return EpisodeUIModel(
        id = this.id,
        name = this.name,
        episode = this.episode,
        airDate = this.airDate
    )
}
