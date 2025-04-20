package edu.iesam.gametracker.features.setting.data.developer.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeveloperDao {

    @Query("SELECT * FROM $DEVELOPER_TABLE")
    suspend fun findAllDevelopers(): List<DeveloperEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDevelopers(vararg developers: DeveloperEntity)

}