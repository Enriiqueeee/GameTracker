package edu.iesam.gametracker.features.videogames.domain

data class Videogame (
    val id: Int,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val playtime: Int,
    val platforms: List<Platforms>,
    val genres: List<Genres>
)


data class Platforms(
    val id: Int,
    val name: String
)

data class Genres(
    val id: Int,
    val name: String

)