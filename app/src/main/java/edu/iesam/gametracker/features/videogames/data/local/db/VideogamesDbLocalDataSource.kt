package edu.iesam.gametracker.features.videogames.data.local.db

import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.core.annotation.Single

@Single
class VideogamesDbLocalDataSource(
    private val videogamesDao: VideogamesDao,
    private val favoriteDao: FavoriteDao
) {

    suspend fun findAll(): Result<List<Videogame>> {
        val entities = videogamesDao.findAll()
        return Result.success(entities.map { it.toDomain() })
    }

    suspend fun findById(videogameId: Int): Result<Videogame?> {
        val entity = videogamesDao.findById(videogameId)
        return Result.success(entity?.toDomain())
    }

    suspend fun saveAll(videogames: List<Videogame>) {
        val entities = videogames.mapIndexed { index, vg ->
            vg.toEntity(orderIndex = index)
        }
        videogamesDao.saveAll(*entities.toTypedArray())
    }

    suspend fun save(videogame: Videogame, orderIndex: Int = Int.MAX_VALUE) {
        videogamesDao.save(videogame.toEntity(orderIndex))
    }

    suspend fun getFavoriteVideogames(): Result<List<Videogame>> {
        val favoriteEntities = favoriteDao.getFavorites()
        val favoriteVideogames = favoriteEntities.mapNotNull { favorite ->
            videogamesDao.findById(favorite.id)?.toDomain()
        }
        return Result.success(favoriteVideogames)
    }

    suspend fun saveFavorite(videogame: Videogame) {
        val favoriteEntity = FavoriteEntity(videogame.id, favorite = true)
        favoriteDao.addFavorite(favoriteEntity)
    }

    suspend fun removeFavorite(videogame: Videogame) {
        val favoriteEntity = FavoriteEntity(videogame.id, favorite = true)
        favoriteDao.deleteFavorite(favoriteEntity)
    }

    suspend fun toggleFavorite(videogame: Videogame) {
        val favoriteEntity = favoriteDao.findById(videogame.id)
        if (favoriteEntity == null) {
            saveFavorite(videogame)
        } else {
            removeFavorite(videogame)
        }
    }
}