package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface VideogamesService {

    @GET("games")
    suspend fun requestVideogames(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 50
    ): Response<RawgResponse>

    @GET("games/{id}")
    suspend fun requestVideogameDetail(
        @Path("id") videogameId: Int,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<VideogamesApiModel>
}

