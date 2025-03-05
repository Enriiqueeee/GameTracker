package edu.iesam.gametracker.app.data.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.iesam.gametracker.features.videogames.domain.Genres
import edu.iesam.gametracker.features.videogames.domain.Platforms

class ClassConverter {

    @TypeConverter
    fun fromStringToPlatformsList(platforms: String): List<Platforms> {
        val listType = object : TypeToken<List<Platforms>>() {}.type
        return Gson().fromJson(platforms, listType)
    }

    @TypeConverter
    fun fromPlatformsListToString(platforms: List<Platforms>): String {
        return Gson().toJson(platforms)
    }

    @TypeConverter
    fun fromStringToGenresList(genres: String): List<Genres> {
        val listType = object : TypeToken<List<Genres>>() {}.type
        return Gson().fromJson(genres, listType)
    }

    @TypeConverter
    fun fromGenresListToString(genres: List<Genres>): String {
        return Gson().toJson(genres)
    }
}
