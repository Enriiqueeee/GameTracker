package edu.iesam.gametracker.features.videogames.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideogamesService {
    @GET("games")
    suspend fun requestVideogames(
        @Query("key") apiKey: String = "55146091e47e424ba20d13f842a7bc5a",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 50
    ): Response<RawgResponse>
}
