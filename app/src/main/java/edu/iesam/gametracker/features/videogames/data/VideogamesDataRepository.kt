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

    override suspend fun getFavoriteVideogames(): Result<List<Videogame>> {
            return db.getFavoriteVideogames()
    }

    override suspend fun saveFavorite(videogame: Videogame): Result<Unit> {
            db.saveFavorite(videogame)
            return Result.success(Unit)

    }

    override suspend fun removeFavorite(videogame: Videogame): Result<Unit> {

            db.removeFavorite(videogame)
           return Result.success(Unit)
    }

    override suspend fun toggleFavorite(videogame: Videogame): Result<Unit> {
            db.toggleFavorite(videogame)
            return Result.success(Unit)
    }

    override suspend fun getRecommendedVideogames(): Result<List<Videogame>> {
        val favorites = db.getFavoriteVideogames().getOrNull() ?: return Result.success(emptyList())
        val genres = favorites.flatMap { it.genres.map { genre -> genre.name } }.distinct()

        val allGames = db.findAll().getOrNull() ?: return Result.success(emptyList())
        val recommended = allGames.filter { game ->
            game.genres.any { it.name in genres } && favorites.none { it.id == game.id }
        }

        return Result.success(recommended)
    }

    override suspend fun searchVideogames(query: String): Result<List<Videogame>> {
        val local = db.findAll().getOrNull().orEmpty()
        val filtered = local.filter {
            it.name.contains(query.trim(), ignoreCase = true)
        }
        if (filtered.isNotEmpty()) {
            return Result.success(filtered)
        }

        return remote.searchVideogames(query).onSuccess { remoteList ->
            db.saveAll(remoteList)
        }
    }

}
