package edu.iesam.gametracker.features.videogames.data.local.db

import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.core.annotation.Single


@Single
class VideogamesDbLocalDataSource(private val videogamesDao: VideogamesDao) {

    suspend fun findAll(): Result<List<Videogame>> {
        val videogames = videogamesDao.findAll()
        return if (videogames.isEmpty()){
            Result.success(emptyList())
        }else{
            Result.success(videogames.map { it.toDomain() })
        }
    }

    suspend fun saveAll(videogames: List<Videogame>) {
        val videogameList = videogames.map { it.toEntity() }
        videogamesDao.saveAll(*videogameList.toTypedArray())
    }

}