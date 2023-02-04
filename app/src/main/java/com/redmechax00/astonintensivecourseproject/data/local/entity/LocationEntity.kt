package com.redmechax00.astonintensivecourseproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase.Companion.TAG_LOCATION_ENTITY

@Entity(tableName = TAG_LOCATION_ENTITY)
data class LocationEntity(

    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "dimension") var dimension: String,
    @ColumnInfo(name = "residents") var residents: List<String>,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "created") var created: String
)