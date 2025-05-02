package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.BuildConfig
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
            val response = videogamesService.requestVideogameDetail(videogameId)
            response
        }.map { response ->
            response.toModel()
        }
    }

    suspend fun searchVideogames(query: String): Result<List<Videogame>> {
        return apiCall {
            videogamesService.searchVideogames(
                query    = query,
                apiKey   = BuildConfig.API_KEY,
                page     = 1,
                pageSize = 50
            )
        }.map { response ->
            response.results.map { it.toModel() }
        }
    }


}
