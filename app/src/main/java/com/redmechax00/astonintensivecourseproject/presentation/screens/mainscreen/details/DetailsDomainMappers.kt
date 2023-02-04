package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details

import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsCharacterPreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsEpisodePreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character.CharacterDetailsUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode.EpisodeDetailsUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location.LocationDetailsUIModel

internal fun CharacterDetailsDomainModel.toCharacterDetailsUIModel(): CharacterDetailsUIModel {
    return CharacterDetailsUIModel(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = CharacterDetailsUIModel.Origin(
            name = this.origin.name,
            url = this.origin.url
        ),
        location = CharacterDetailsUIModel.Location(
            name = this.location.name,
            url = this.location.url
        ),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}

internal fun LocationDetailsDomainModel.toLocationDetailsUIModel(): LocationDetailsUIModel {
    return LocationDetailsUIModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents,
        url = this.url,
        created = this.created
    )
}

internal fun EpisodeDetailsDomainModel.toEpisodeDetailsUIModel(): EpisodeDetailsUIModel {
    return EpisodeDetailsUIModel(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episode = this.episode,
        characters = this.characters,
        url = this.url,
        created = this.created
    )
}

internal fun CharacterDetailsDomainModel.toBaseDetailsCharacterPreviewUIModel():
        BaseDetailsCharacterPreviewUIModel {
    return BaseDetailsCharacterPreviewUIModel(
        id = this.id,
        name = this.name,
        image = this.image
    )
}

internal fun EpisodeDetailsDomainModel.toBaseDetailsEpisodePreviewUIModel():
        BaseDetailsEpisodePreviewUIModel {
    return BaseDetailsEpisodePreviewUIModel(
        id = this.id,
        name = this.name,
        episode = this.episode
    )
}
