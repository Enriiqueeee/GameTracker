package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.app.data.remote.apiCall
import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.koin.core.annotation.Single

@Single
class VideogamesRemoteDataSource(private val videogamesService: VideogamesService) {

    suspend fun getVideogames(): Result<List<Videogame>> {
        return apiCall {
            videogamesService.requestVideogames()
        }.map { videgomaes ->
            videgomaes.map {
                it.toModel()
            }
        }
    }
}