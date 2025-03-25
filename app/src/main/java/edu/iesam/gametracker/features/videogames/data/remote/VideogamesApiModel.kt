package edu.iesam.gametracker.features.videogames.data.remote

import com.google.gson.annotations.SerializedName

data class VideogamesApiModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("released") val released: String,
    @SerializedName("background_image") val backgroundImage: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("playtime") val playtime: Int,
    @SerializedName("description_raw") val description: String?
)
