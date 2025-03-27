package edu.iesam.gametracker.features.videogames.data

import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesDbLocalDataSource
import edu.iesam.gametracker.features.videogames.data.remote.VideogamesRemoteDataSource
import edu.iesam.gametracker.features.videogames.domain.Videogame
import edu.iesam.gametracker.features.videogames.domain.VideogameRepository
import org.koin.core.annotation.Single

@Single
class VideogamesDataRepository(
    private val db: VideogamesDbLocalDataSource,
    private val remote: VideogamesRemoteDataSource
) : VideogameRepository {

    override suspend fun getVideogames(): Result<List<Videogame>> {
        return db.findAll().onSuccess { videogames ->
            if (videogames.isNotEmpty()) {
                return Result.success(videogames)
            } else {
                return remote.getVideogames().onSuccess { remoteList ->
                    val updatedList = remoteList.map { videogame ->
                        if (videogame.description.isEmpty() || videogame.description == "null") {
                            remote.getVideogameDetail(videogame.id).getOrNull() ?: videogame
                        } else {
                            videogame
                        }
                    }
                    db.saveAll(updatedList)
                    return Result.success(updatedList)
                }
            }
        }
    }


    override suspend fun getVideogameDetail(videogameId: Int): Result<Videogame> {
        val localVideogame = db.findById(videogameId)
        if (localVideogame.isSuccess) {
            val videogame = localVideogame.getOrNull()
            if (videogame?.id == videogameId) {
                return Result.success(videogame)
            }
        }
        val remoteVideogame = remote.getVideogameDetail(videogameId)
        remoteVideogame.onSuccess {
            db.save(it)
        }
        return remoteVideogame
    }
}
