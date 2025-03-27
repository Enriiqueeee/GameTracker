package edu.iesam.gametracker.features.videogames.domain

class RemoveFavoriteVideogameUseCase(private val repository: VideogameRepository) {
    suspend operator fun invoke(videogame: Videogame) = repository.removeFavorite(videogame)
}