package edu.iesam.gametracker.features.videogames.data.local.db

import edu.iesam.gametracker.features.videogames.domain.Videogame

fun Videogame.toEntity(orderIndex: Int): VideogamesEntity {
    return VideogamesEntity(
        this.id,
        this.name,
        this.released,
        this.backgroundImage,
        this.rating,
        this.playtime,
        this.description,
        this.genres,
        orderIndex = orderIndex
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
        this.description,
        this.genres
    )
}