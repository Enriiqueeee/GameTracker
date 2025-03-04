package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.features.videogames.domain.Genres
import edu.iesam.gametracker.features.videogames.domain.Platforms
import edu.iesam.gametracker.features.videogames.domain.Videogame

fun VideogamesApiModel.toModel(): Videogame {
    return Videogame(
        this.id,
        this.name,
        this.released,
        this.backgroundImage,
        this.rating,
        this.playtime,
        this.platforms.map { it.toModel() },
        this.genres.map { it.toModel() }
    )
}

fun PlatformsApiModel.toModel(): Platforms {
    return Platforms(
        this.id,
        this.name
    )
}

fun GenresApiModel.toModel(): Genres {
    return Genres(
        this.id,
        this.name
    )
}
