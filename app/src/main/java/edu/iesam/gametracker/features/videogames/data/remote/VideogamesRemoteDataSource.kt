package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.app.data.remote.apiCall
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.core.annotation.Single

@Single
class VideogamesRemoteDataSource(private val videogamesService: VideogamesService) {

    suspend fun getVideogames(): Result<List<Videogame>> {
        return apiCall {
            videogamesService.requestVideogames()
        }.map { response ->
            response.results.map { it.toModel() }
        }
    }

    suspend fun getVideogameDetail(videogameId: Int): Result<Videogame> {
        return apiCall {
            videogamesService.requestVideogameDetail(videogameId)
        }.map { response ->
            response.results.firstOrNull()?.toModel()!!
        }
    }

}
