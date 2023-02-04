package com.redmechax00.astonintensivecourseproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase.Companion.TAG_CHARACTER_ENTITY

@Entity(tableName = TAG_CHARACTER_ENTITY)
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "species") var species: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "origin") var origin: OriginEntity,
    @ColumnInfo(name = "location") var location: LocationEntity,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "episode") var episode: List<String>,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "created") var created: String
) {

    data class OriginEntity(
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "url") var url: String
    )

    data class LocationEntity(
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "url") var url: String
    )
}