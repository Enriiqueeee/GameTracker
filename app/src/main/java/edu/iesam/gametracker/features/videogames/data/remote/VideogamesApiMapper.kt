package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.features.videogames.domain.Genre
import edu.iesam.gametracker.features.videogames.domain.Videogame

fun VideogamesApiModel.toModel(): Videogame =
    Videogame(
        id              = id,
        name            = name.orEmpty(),
        released        = released.orEmpty(),
        backgroundImage = backgroundImage.orEmpty(),
        rating          = rating,
        playtime        = playtime,
        description     = description.orEmpty(),
        genres          = genres.map {
            Genre(it.id, it.name.orEmpty())
        }
    )

