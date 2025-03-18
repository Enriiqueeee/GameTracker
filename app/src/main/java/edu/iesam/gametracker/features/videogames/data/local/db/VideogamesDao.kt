package edu.iesam.gametracker.features.videogames.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideogamesDao {

    @Query("SELECT * FROM $VIDEOGAME_TABLE")
    suspend fun findAll(): List<VideogamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(vararg videogames: VideogamesEntity)

    @Query("SELECT * FROM $VIDEOGAME_TABLE WHERE id = :videogameId LIMIT 1")
    suspend fun findById(videogameId: Int): VideogamesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(videogame: VideogamesEntity)

}