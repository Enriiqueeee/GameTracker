package edu.iesam.gametracker.features.videogames.domain

class SaveFavoriteVideogameUseCase(private val repository: VideogameRepository) {
    suspend operator fun invoke(videogame: Videogame) = repository.saveFavorite(videogame)
}