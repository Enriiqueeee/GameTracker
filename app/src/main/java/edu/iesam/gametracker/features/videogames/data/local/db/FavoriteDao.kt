package edu.iesam.gametracker.features.videogames.data.local.db

import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM $FAVORITE_TABLE WHERE videogameFavorite = 1")
    suspend fun getFavorites(): List<FavoriteEntity>

    @Query("SELECT * FROM $FAVORITE_TABLE WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(vararg favorite: FavoriteEntity)

    @Update
    suspend fun updateFavorite(vararg favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(vararg favorite: FavoriteEntity)
}

