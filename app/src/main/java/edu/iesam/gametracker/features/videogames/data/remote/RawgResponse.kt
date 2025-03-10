package edu.iesam.gametracker.features.videogames.data.remote

import com.google.gson.annotations.SerializedName

data class RawgResponse(
    @SerializedName("results") val results: List<VideogamesApiModel>
)
