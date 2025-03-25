package edu.iesam.gametracker.features.videogames.data.remote

import edu.iesam.gametracker.features.videogames.domain.Videogame
import org.jsoup.Jsoup

fun VideogamesApiModel.toModel(): Videogame {
    val cleanedDescription = this.description?.takeIf { it.isNotEmpty() }
        ?.let { Jsoup.parse(it).text() } ?: "Descripción no disponible"

    return Videogame(
        this.id,
        this.name,
        this.released,
        this.backgroundImage,
        this.rating,
        this.playtime,
        description = cleanedDescription
    )
}
