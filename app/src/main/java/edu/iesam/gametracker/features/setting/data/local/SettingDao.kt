package edu.iesam.gametracker.features.setting.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SettingDao {

    @Query("SELECT * FROM $RESOURCE_TABLE")
    suspend fun findAllResources(): List<ResourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveResources(vararg resources: ResourceEntity)

    @Query("SELECT * FROM $DEVELOPER_TABLE")
    suspend fun findAllDevelopers(): List<DeveloperEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDevelopers(vararg developers: DeveloperEntity)

}