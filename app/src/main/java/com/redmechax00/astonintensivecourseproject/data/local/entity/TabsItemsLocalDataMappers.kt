package com.redmechax00.astonintensivecourseproject.data.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redmechax00.astonintensivecourseproject.domain.models.tab.CharacterDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.EpisodeDomainModel
import com.redmechax00.astonintensivecourseproject.domain.models.tab.LocationDomainModel

class EntityConverter {

    @TypeConverter
    fun fromCharacterOriginEntity(value: CharacterEntity.OriginEntity): String {
        val gson = Gson()
        val type = object : TypeToken<CharacterEntity.OriginEntity>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCharacterOriginEntity(value: String): CharacterEntity.OriginEntity {
        val gson = Gson()
        val type = object : TypeToken<CharacterEntity.OriginEntity>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCharacterLocationEntity(value: CharacterEntity.LocationEntity): String {
        val gson = Gson()
        val type = object : TypeToken<CharacterEntity.LocationEntity>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCharacterLocationEntity(value: String): CharacterEntity.LocationEntity {
        val gson = Gson()
        val type = object : TypeToken<CharacterEntity.LocationEntity>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCharacterEpisodeEntity(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCharacterEpisodeEntity(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}

internal fun CharacterEntity.toCharacterDomainModel(): CharacterDomainModel {
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

internal fun LocationEntity.toLocationDomainModel(): LocationDomainModel {
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

internal fun EpisodeEntity.toEpisodeDomainModel(): EpisodeDomainModel {
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

