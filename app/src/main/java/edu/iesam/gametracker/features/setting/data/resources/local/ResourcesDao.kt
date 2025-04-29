package edu.iesam.gametracker.features.setting.data.resources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResourcesDao {

    @Query("SELECT * FROM $RESOURCE_TABLE")
    suspend fun getAll(): List<ResourcesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveResources(vararg resources: ResourcesEntity)
}
