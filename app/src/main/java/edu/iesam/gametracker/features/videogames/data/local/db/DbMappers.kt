package edu.iesam.gametracker.features.videogames.data.local.db

import edu.iesam.gametracker.features.videogames.domain.Genres
import edu.iesam.gametracker.features.videogames.domain.Platforms
import edu.iesam.gametracker.features.videogames.domain.Videogame

fun Videogame.toEntity(): VideogamesEntity {
    return VideogamesEntity(
        this.id,
        this.name,
        this.released,
        this.backgroundImage,
        this.rating,
        this.playtime,
        this.platforms.map { it.toEntity() },
        this.genres.map { it.toEntity() },
    )
}

fun Platforms.toEntity(): PlatformsEntity {
    return PlatformsEntity(
        this.id,
        this.name
    )
}

fun Genres.toEntity(): GenresEntity {
    return GenresEntity(
        this.id,
        this.name
    )
}

fun VideogamesEntity.toDomain(): Videogame{
    return Videogame(
        this.id,
        this.name,
        this.released,
        this.backgroundImage,
        this.rating,
        this.playtime,
        this.platforms.map { it.toDomain() },
        this.genres.map { it.toDomain() },
    )
}

fun PlatformsEntity.toDomain(): Platforms {
    return Platforms(
        this.id,
        this.name
    )
}

fun GenresEntity.toDomain(): Genres {
    return Genres(
        this.id,
        this.name
    )
}
