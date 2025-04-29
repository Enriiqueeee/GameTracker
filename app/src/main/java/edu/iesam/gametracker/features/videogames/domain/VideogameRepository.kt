package edu.iesam.gametracker.features.videogames.domain

interface VideogameRepository {
    suspend fun getVideogames(): Result<List<Videogame>>
    suspend fun getVideogameDetail(videogameId: Int): Result<Videogame>
    suspend fun getFavoriteVideogames(): Result<List<Videogame>>
    suspend fun saveFavorite(videogame: Videogame): Result<Unit>
    suspend fun removeFavorite(videogame: Videogame): Result<Unit>
    suspend fun toggleFavorite(videogame: Videogame): Result<Unit>
    suspend fun getRecommendedVideogames(): Result<List<Videogame>>
}