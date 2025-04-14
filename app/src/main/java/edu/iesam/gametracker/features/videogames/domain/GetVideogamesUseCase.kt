package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class GetVideogamesUseCase(private val videogameRepository: VideogameRepository) {

    suspend operator fun invoke(): Result<List<VideoGameFeed>> {
        val resultVideogames = videogameRepository.getVideogames()
        val favoriteVideogames = videogameRepository.getFavoriteVideogames().getOrNull()
        var videoGamesFeed = mutableListOf<VideoGameFeed>()
        resultVideogames.getOrNull()?.let { videoGames ->
            //videoGames
            videoGames.forEach { videGame ->
                videoGamesFeed.add(
                    VideoGameFeed(
                        videGame,
                        favoriteVideogames != null &&
                                favoriteVideogames.find { favoriteGames ->
                                    favoriteGames.id == videGame.id
                                } != null)
                )
            }
        }
        return Result.success(videoGamesFeed)
    }

    data class VideoGameFeed(
        val videogame: Videogame,
        val isFavorite: Boolean
    )
}