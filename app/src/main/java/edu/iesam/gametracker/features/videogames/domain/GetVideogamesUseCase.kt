package edu.iesam.gametracker.features.videogames.domain

import org.koin.core.annotation.Single

@Single
class GetVideogamesUseCase(private val videogameRepository: VideogameRepository) {

    suspend operator fun invoke(): Result<List<Videogame>>{
        return videogameRepository.getVideogames()
    }
}