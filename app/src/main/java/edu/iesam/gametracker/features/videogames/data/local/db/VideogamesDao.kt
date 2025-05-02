package edu.iesam.gametracker.features.videogames.data.local.db

import androidx.room.*

@Dao
interface VideogamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(vararg videogames: VideogamesEntity)

    @Query("SELECT * FROM $VIDEOGAME_TABLE ORDER BY order_index ASC")
    suspend fun findAll(): List<VideogamesEntity>

    @Query("SELECT * FROM $VIDEOGAME_TABLE WHERE id = :videogameId LIMIT 1")
    suspend fun findById(videogameId: Int): VideogamesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(videogame: VideogamesEntity)
}
