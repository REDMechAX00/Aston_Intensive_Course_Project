package com.redmechax00.astonintensivecourseproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase.Companion.TAG_CHARACTER_ENTITY
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase.Companion.TAG_EPISODE_ENTITY
import com.redmechax00.astonintensivecourseproject.data.local.database.AppDatabase.Companion.TAG_LOCATION_ENTITY
import com.redmechax00.astonintensivecourseproject.data.local.entity.CharacterEntity
import com.redmechax00.astonintensivecourseproject.data.local.entity.EpisodeEntity
import com.redmechax00.astonintensivecourseproject.data.local.entity.LocationEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query(
        "SELECT * FROM $TAG_CHARACTER_ENTITY WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
                " AND (:status IS NULL OR status LIKE '%' || :status || '%')" +
                " AND (:species IS NULL OR species LIKE '%' || :species || '%')" +
                " AND (:type IS NULL OR type LIKE '%' || :type || '%')" +
                " AND (:gender IS NULL OR gender LIKE '%' || :gender || '%')" +
                " ORDER BY id ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getCharacters(
        offset: Int,
        limit: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Query(
        "SELECT * FROM $TAG_LOCATION_ENTITY WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
                " AND (:type IS NULL OR type LIKE '%' || :type || '%')" +
                " AND (:dimension IS NULL OR dimension LIKE '%' || :dimension || '%')" +
                " ORDER BY id ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getLocations(
        offset: Int,
        limit: Int,
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Query(
        "SELECT * FROM $TAG_EPISODE_ENTITY WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
                " AND (:episode IS NULL OR episode LIKE '%' || :episode || '%')" +
                " ORDER BY id ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getEpisodes(
        offset: Int,
        limit: Int,
        name: String? = null,
        episode: String? = null
    ): List<EpisodeEntity>
}