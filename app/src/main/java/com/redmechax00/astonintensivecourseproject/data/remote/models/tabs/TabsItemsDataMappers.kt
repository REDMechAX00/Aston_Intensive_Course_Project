package com.redmechax00.astonintensivecourseproject.data.remote.models.tabs

import com.redmechax00.astonintensivecourseproject.data.local.entity.CharacterEntity
import com.redmechax00.astonintensivecourseproject.data.local.entity.EpisodeEntity
import com.redmechax00.astonintensivecourseproject.data.local.entity.LocationEntity
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel

internal fun CharactersResponseDto.CharacterResponseDto.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = CharacterEntity.OriginEntity(
            name = this.origin.name,
            url = this.origin.url
        ),
        location = CharacterEntity.LocationEntity(
            name = this.location.name,
            url = this.location.url
        ),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}

internal fun LocationsResponseDto.LocationResponseDto.toLocationEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents,
        url = this.url,
        created = this.created
    )
}

internal fun EpisodesResponseDto.EpisodeResponseDto.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episode = this.episode,
        characters = this.characters,
        url = this.url,
        created = this.created
    )
}

internal fun CharactersResponseDto.CharacterResponseDto.toCharacterDomainModel(): CharacterDomainModel {
    return CharacterDomainModel(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = CharacterDomainModel.Origin(
            name = this.origin.name,
            url = this.origin.url
        ),
        location = CharacterDomainModel.Location(
            name = this.location.name,
            url = this.location.url
        ),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}

internal fun LocationsResponseDto.LocationResponseDto.toLocationDomainModel(): LocationDomainModel {
    return LocationDomainModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents,
        url = this.url,
        created = this.created
    )
}

internal fun EpisodesResponseDto.EpisodeResponseDto.toEpisodeDomainModel(): EpisodeDomainModel {
    return EpisodeDomainModel(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episode = this.episode,
        characters = this.characters,
        url = this.url,
        created = this.created
    )
}