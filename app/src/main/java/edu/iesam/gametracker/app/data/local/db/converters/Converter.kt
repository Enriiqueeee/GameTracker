package edu.iesam.gametracker.app.data.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.iesam.gametracker.features.videogames.domain.Genre

object Converters {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromGenres(genres: List<Genre>): String =
        gson.toJson(genres)

    @TypeConverter
    @JvmStatic
    fun toGenres(json: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(json, type)
    }
}