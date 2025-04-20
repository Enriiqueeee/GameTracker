package edu.iesam.gametracker.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.iesam.gametracker.app.data.local.db.converters.Converters
import edu.iesam.gametracker.features.setting.data.local.ResourceEntity
import edu.iesam.gametracker.features.setting.data.local.SettingDao
import edu.iesam.gametracker.features.videogames.data.local.db.FavoriteDao
import edu.iesam.gametracker.features.videogames.data.local.db.FavoriteEntity
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesDao
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesEntity

@Database(
    entities = [
        VideogamesEntity::class,
        FavoriteEntity::class,
        ResourceEntity::class
    ],
    version = 9,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class GameTrackerDataBase : RoomDatabase() {
    abstract fun videogamesDao(): VideogamesDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun settingDao(): SettingDao
}