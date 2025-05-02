package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class SearchVideogamesUseCase(
    private val repository: VideogameRepository
) {
    suspend operator fun invoke(query: String): Result<List<GetVideogamesUseCase.VideoGameFeed>> {
        return repository.searchVideogames(query).map { games ->
            val favs = repository.getFavoriteVideogames().getOrNull().orEmpty()
            games.map { vg ->
                GetVideogamesUseCase.VideoGameFeed(
                    videogame  = vg,
                    isFavorite = favs.any { it.id == vg.id }
                )
            }
        }
    }
}
