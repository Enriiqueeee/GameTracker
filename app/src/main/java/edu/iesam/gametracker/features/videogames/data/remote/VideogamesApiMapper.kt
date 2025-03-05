package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.features.videogames.domain.Videogame

fun VideogamesApiModel.toModel(): Videogame {
    return Videogame(
        this.id,
        this.name,
        this.released,
        this.backgroundImage,
        this.rating,
        this.playtime,
    )
}
