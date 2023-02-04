package com.redmechax00.astonintensivecourseproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase.Companion.TAG_EPISODE_ENTITY

@Entity(tableName = TAG_EPISODE_ENTITY)
data class EpisodeEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "air_date") var airDate: String,
    @ColumnInfo(name = "episode") var episode: String,
    @ColumnInfo(name = "characters") var characters: List<String>,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "created") var created: String
)