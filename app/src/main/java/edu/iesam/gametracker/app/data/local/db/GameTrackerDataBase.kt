package edu.iesam.gametracker.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesDao
import edu.iesam.gametracker.features.videogames.data.local.db.VideogamesEntity

@Database(
    entities = [
        VideogamesEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters()
abstract class GameTrackerDataBase : RoomDatabase() {
    abstract fun videogamesDao(): VideogamesDao
}