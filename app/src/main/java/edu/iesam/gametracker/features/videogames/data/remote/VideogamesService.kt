package edu.iesam.gametracker.features.videogames.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface VideogamesService {
    @GET("games?key=55146091e47e424ba20d13f842a7bc5a")
    suspend fun requestVideogames(): Response<RawgResponse>
}
