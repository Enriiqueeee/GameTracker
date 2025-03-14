package edu.iesam.gametracker.features.videogames.domain

interface VideogameRepository {
    suspend fun getVideogames(): Result<List<Videogame>>
}