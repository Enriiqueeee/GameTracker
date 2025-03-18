package edu.iesam.gametracker.features.videogames.domain

interface VideogameRepository {
    suspend fun getVideogames(): Result<List<Videogame>>
    suspend fun getVideogameDetail(videogameId: Int): Result<Videogame>
}