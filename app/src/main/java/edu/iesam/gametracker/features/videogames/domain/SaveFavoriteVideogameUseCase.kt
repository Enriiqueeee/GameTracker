package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class SaveFavoriteVideogameUseCase(
    private val repository: VideogameRepository
) {
    suspend operator fun invoke(videogame: Videogame): Result<Unit> {
        return repository.saveFavorite(videogame)
    }
}