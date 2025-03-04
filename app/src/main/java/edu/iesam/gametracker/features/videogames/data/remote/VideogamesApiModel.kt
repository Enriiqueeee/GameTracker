package edu.iesam.gametracker.features.videogames.data.remote

import com.google.gson.annotations.SerializedName

data class VideogamesApiModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("released") val released: String,
    @SerializedName("background_image") val backgroundImage: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("playtime") val playtime: Int,
    @SerializedName("platforms") val platforms: List<PlatformsApiModel>,
    @SerializedName("genres") val genres: List<GenresApiModel>
)

data class PlatformsApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class GenresApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String

)