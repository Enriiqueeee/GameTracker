package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class ToggleFavoriteVideogameUseCase(
    private val repository: VideogameRepository
) {
    suspend operator fun invoke(videogame: Videogame): Result<Unit> {
        return repository.toggleFavorite(videogame)
    }
}