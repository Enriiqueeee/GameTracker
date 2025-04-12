package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class GetFavoriteVideogamesUseCase(private val videogameRepository: VideogameRepository) {

    suspend operator fun invoke(): Result<List<GetVideogamesUseCase.VideoGameFeed>> {
        return videogameRepository.getFavoriteVideogames().map { favorites ->
            favorites.map {
                GetVideogamesUseCase.VideoGameFeed(
                    it, true
                )
            }
        }
    }
}