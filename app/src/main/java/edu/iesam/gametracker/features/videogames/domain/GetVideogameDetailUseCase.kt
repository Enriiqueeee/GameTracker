package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class GetVideogameDetailUseCase(private val videogameRepository: VideogameRepository) {
    suspend operator fun invoke(videogameId: Int): Result<Videogame>{
        return videogameRepository.getVideogameDetail(videogameId)
    }
}