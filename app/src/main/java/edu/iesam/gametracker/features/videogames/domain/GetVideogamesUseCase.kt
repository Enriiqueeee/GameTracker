package edu.iesam.gametracker.features.videogames.domain

class GetVideogamesUseCase(private val videogameRepository: VideogameRepository) {

    suspend operator fun invoke(): Result<List<Videogame>>{
        return videogameRepository.getVideogames()
    }
}