package edu.iesam.gametracker.features.videogames.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideogamesService {

    @GET("games")
    suspend fun requestVideogames(
        @Query("key") apiKey: String = "c2c4ac233f0e46eca79e7ab82b1bdbef",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 50
    ): Response<RawgResponse>

    @GET("games/{id}")
    suspend fun requestVideogameDetail(
        @Path("id") videogameId: Int,
        @Query("key") apiKey: String = "c2c4ac233f0e46eca79e7ab82b1bdbef"
    ): Response<VideogamesApiModel>
}

