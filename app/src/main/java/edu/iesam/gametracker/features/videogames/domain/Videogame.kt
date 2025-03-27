package edu.iesam.gametracker.features.videogames.domain

data class Videogame (
    val id: Int,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val playtime: Int,
    val description: String
)

