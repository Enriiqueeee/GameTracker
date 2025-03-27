package edu.iesam.gametracker.features.videogames.domain

class GetFavoriteVideogamesUseCase(private val repository: VideogameRepository) {
    suspend operator fun invoke(): List<Videogame> = repository.getFavorites()
}