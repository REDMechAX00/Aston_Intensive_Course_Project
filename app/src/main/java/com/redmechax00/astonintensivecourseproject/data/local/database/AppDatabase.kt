package com.redmechax00.astonintensivecourseproject.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.redmechax00.astonintensivecourseproject.data.local.dao.AppDao
import com.redmechax00.astonintensivecourseproject.data.local.entity.CharacterEntity
import com.redmechax00.astonintensivecourseproject.data.local.entity.EntityConverter
import com.redmechax00.astonintensivecourseproject.data.local.entity.EpisodeEntity
import com.redmechax00.astonintensivecourseproject.data.local.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class],
    version = 1
)
@TypeConverters(EntityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao

    companion object {
        const val TAG_CHARACTER_ENTITY = "CharacterEntity"
        const val TAG_LOCATION_ENTITY = "LocationEntity"
        const val TAG_EPISODE_ENTITY = "EpisodeEntity"

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) = Room.databaseBuilder(
            appContext, AppDatabase::class.java, "AppDatabase.db"
        ).fallbackToDestructiveMigration().build()
    }
}