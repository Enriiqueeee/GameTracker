package edu.iesam.gametracker.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.iesam.gametracker.app.data.local.db.converters.Converters
import edu.iesam.gametracker.features.setting.data.developer.local.DeveloperEntity
import edu.iesam.gametracker.features.setting.data.developer.local.DeveloperDao
import edu.iesam.gametracker.features.setting.data.resources.local.ResourcesDao
import edu.iesam.gametracker.features.setting.data.resources.local.ResourcesEntity
import edu.iesam.gametracker.features.videogames.data.local.db.FavoriteDao
import edu.iesam.gametracker.features.videogames.data.local.db.FavoriteEntity
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesDao
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesEntity

@Database(
    entities = [
        VideogamesEntity::class,
        FavoriteEntity::class,
        DeveloperEntity::class,
        ResourcesEntity::class
    ],
    version = 15,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class GameTrackerDataBase : RoomDatabase() {
    abstract fun videogamesDao(): VideogamesDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun developerDao(): DeveloperDao
    abstract fun resourcesDao(): ResourcesDao
}