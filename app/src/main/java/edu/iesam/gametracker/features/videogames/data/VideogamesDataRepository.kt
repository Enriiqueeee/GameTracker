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
        val local = db.findAll()

        return local.onSuccess { videogames ->
            if (videogames.isNotEmpty()) {
                return Result.success(videogames)
            } else {
                val remote = remote.getVideogames()
                remote.onSuccess {
                    db.saveAll(it)
                }
                return remote
            }
        }
    }

    override suspend fun getVideogameDetail(videogameId: Int): Result<Videogame> {
        val local = db.findById(videogameId)
        return if (local.isSuccess && local.getOrNull() != null) {
            Result.success(local.getOrNull()!!)
        } else {
            val remote = remote.getVideogameDetail(videogameId)
            remote.onSuccess {
                db.save(it)
            }
            return remote
        }
    }
}
