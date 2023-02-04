package com.redmechax00.astonintensivecourseproject.data.remote.models.details

import com.redmechax00.astonintensivecourseproject.domain.models.details.CharacterDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.EpisodeDetailsDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.details.LocationDetailsDomainModel

internal fun CharacterDetailsResponseDto.toCharacterDetailsDomainModel(): CharacterDetailsDomainModel {
    return CharacterDetailsDomainModel(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = CharacterDetailsDomainModel.Origin(
            name = this.origin.name,
            url = this.origin.url
        ),
        location = CharacterDetailsDomainModel.Location(
            name = this.location.name,
            url = this.location.url
        ),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}

internal fun LocationDetailsResponseDto.toLocationDetailsDomainModel(): LocationDetailsDomainModel {
    return LocationDetailsDomainModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents,
        url = this.url,
        created = this.created
    )
}

internal fun EpisodeDetailsResponseDto.toEpisodeDetailsDomainModel(): EpisodeDetailsDomainModel {
    return EpisodeDetailsDomainModel(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episode = this.episode,
        characters = this.characters,
        url = this.url,
        created = this.created
    )
}

internal fun AllCharactersDetailsResponseDto.toCharacterDetailsDomainModel(): CharacterDetailsDomainModel {
    return CharacterDetailsDomainModel(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = CharacterDetailsDomainModel.Origin(
            name = this.origin.name,
            url = this.origin.url
        ),
        location = CharacterDetailsDomainModel.Location(
            name = this.location.name,
            url = this.location.url
        ),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}

internal fun AllEpisodesDetailsResponseDto.toEpisodeDetailsDomainModel(): EpisodeDetailsDomainModel {
    return EpisodeDetailsDomainModel(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episode = this.episode,
        characters = this.characters,
        url = this.url,
        created = this.created
    )
}