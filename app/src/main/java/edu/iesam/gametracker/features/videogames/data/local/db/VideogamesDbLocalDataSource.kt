package edu.iesam.gametracker.features.videogames.data.local.db

import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.core.annotation.Single


@Single
class VideogamesDbLocalDataSource(private val videogamesDao: VideogamesDao) {

    suspend fun findAll(): Result<List<Videogame>> {
        val videogames = videogamesDao.findAll()
        return if (videogames.isEmpty()) {
            Result.success(emptyList())
        } else {
            Result.success(videogames.map { it.toDomain() })
        }
    }

    suspend fun findById(videogameId: Int): Result<Videogame?> {
        val videogame = videogamesDao.findById(videogameId)
        return if (videogame != null) {
            Result.success(videogame.toDomain())
        } else {
            Result.success(null)
        }
    }

    suspend fun saveAll(videogames: List<Videogame>) {
        val videogameList = videogames.map { it.toEntity() }
        videogamesDao.saveAll(*videogameList.toTypedArray())
    }

    suspend fun save(videogame: Videogame) {
        videogamesDao.save(videogame.toEntity())
    }
}