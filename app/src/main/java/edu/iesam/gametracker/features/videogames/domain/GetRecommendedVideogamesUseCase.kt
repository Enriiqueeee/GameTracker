package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class GetRecommendedVideogamesUseCase(
    private val repository: VideogameRepository
) {
    suspend operator fun invoke(): Result<List<GetVideogamesUseCase.VideoGameFeed>> {
        val favorites = repository.getFavoriteVideogames().getOrNull() ?: return Result.success(emptyList())
        val genres = favorites.flatMap { it.genres.map { genre -> genre.name } }.distinct()

        val allGames = repository.getVideogames().getOrNull() ?: return Result.success(emptyList())
        val recommended = allGames.filter { game ->
            game.genres.any { it.name in genres } && favorites.none { it.id == game.id }
        }

        return Result.success(
            recommended.map { game ->
                GetVideogamesUseCase.VideoGameFeed(game, false)
            }
        )
    }
}
